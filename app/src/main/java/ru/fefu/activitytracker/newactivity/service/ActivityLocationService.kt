package ru.fefu.activitytracker.newactivity.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.newactivity.NewActivityActivity
import java.time.LocalDateTime

class ActivityLocationService : Service() {

    companion object {
        private const val CHANNEL_ID = "activity_location_service_id"
        private const val EXTRA_ID = "id"

        const val ACTION_START = "start"
        const val ACTION_CANCEL = "cancel"

        var distance = 0.0
        var activityId = -1

        val locationRequest: LocationRequest
            get() = LocationRequest.create()
                .setInterval(10000L)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(20f)

        fun startForeground(context: Context, id: Int) {
            val intent = Intent(context, ActivityLocationService::class.java)
            activityId = id
            intent.putExtra(EXTRA_ID, activityId)
            intent.action = ACTION_START
            ContextCompat.startForegroundService(context, intent)
        }
    }

    private val coordinates = mutableListOf<Pair<Double, Double>>()

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    private var locationCallback: LocationCallback? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        if (intent?.action == ACTION_CANCEL) {
            activityId = intent.getLongExtra("activityId", activityId.toLong()).toInt()

            App.INSTANCE.db.activityDao().updateIsFinishedById(activityId, true)
            App.INSTANCE.db.activityDao().updateDistanceById(activityId, distance)
            App.INSTANCE.db.activityDao().updateEndTimeById(activityId, LocalDateTime.now())

            distance = 0.0
            activityId = -1
            coordinates.clear()

            stopLocationUpdates()
            stopForeground(true)
            stopSelf()
            return START_NOT_STICKY
        } else if (intent?.action == ACTION_START) {
            App.INSTANCE.db.activityDao().updateStartTimeById(startId, LocalDateTime.now())
            startLocationUpdates(intent.getIntExtra(EXTRA_ID, -1))
            return START_REDELIVER_INTENT
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
        super.onDestroy()
    }

    private fun startLocationUpdates(id: Int) {
        if (id == -1) stopSelf()

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) stopSelf()

        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
        ActivityLocationCallback(id).apply {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                this,
                Looper.getMainLooper()
            )
            locationCallback = this
        }
        showNotification()
    }

    private fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
    }

    private fun showNotification() {
        createChannel()

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, NewActivityActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val cancelIntent = Intent(this, ActivityLocationService::class.java).apply {
            action = ACTION_CANCEL
        }

        val cancelPendingIntent = PendingIntent.getService(
            this,
            1,
            cancelIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Здравствуйте")
            .setContentText("Отслеживание Вашей активности")
            .setSmallIcon(R.drawable.ic_user_location)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_user_location, "Остановить", cancelPendingIntent)
            .build()

        startForeground(1, notification)
    }

    private fun createChannel() {
        val channel =
            NotificationChannel(CHANNEL_ID, "Default channel", NotificationManager.IMPORTANCE_LOW)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    inner class ActivityLocationCallback(private val activityId: Int) : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            val lastLocation = result?.lastLocation ?: return

            val newCoordinate = Pair(lastLocation.latitude, lastLocation.longitude)
            coordinates.add(newCoordinate)

            if (coordinates.size > 1) {
                val newLocation = Location("NewLocation")
                newLocation.latitude = newCoordinate.first
                newLocation.longitude = newCoordinate.second

                val oldPair = coordinates[coordinates.lastIndex - 1]
                val oldCoordinate = Pair(oldPair.first, oldPair.second)

                val oldLocation = Location("OldLocation")
                oldLocation.latitude = oldCoordinate.first
                oldLocation.longitude = oldCoordinate.second

                distance += newLocation.distanceTo(oldLocation)
            }

            App.INSTANCE.db.activityDao().updateCoordinatesById(activityId, coordinates)
        }
    }
}