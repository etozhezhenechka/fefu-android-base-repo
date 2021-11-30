package ru.fefu.activitytracker.newactivity.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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

class ActivityLocationService : Service() {

    companion object {
        private const val CHANNEL_ID = "activity_location_service_id"
        private const val EXTRA_ID = "id"

        private const val ACTION_START = "start"
        private const val ACTION_CANCEL = "cancel"

        var activityId = -1
        val coordinates = mutableListOf<Pair<Double, Double>>()

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

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    private var locationCallback: LocationCallback? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        if (intent?.action == ACTION_CANCEL) {
            stopLocationUpdates()
            stopForeground(true)
            stopSelf()
            return START_NOT_STICKY
        } else if (intent?.action == ACTION_START) {
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

            coordinates.add(Pair(lastLocation.latitude, lastLocation.longitude))

            App.INSTANCE.db.activityDao().updateCoordinatesById(activityId, coordinates)
        }
    }
}