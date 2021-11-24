package ru.fefu.activitytracker.newactivity.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.newactivity.NewActivityActivity

class ActivityForegroundService : Service() {

    companion object {
        private const val CHANNEL_ID = "activity_foreground_service_id"
        private const val EXTRA_ID = "id"
        private const val ACTION_CANCEL = "cancel"

        fun startForeground(context: Context, id: Int) {
            val intent = Intent(context, ActivityForegroundService::class.java)
            intent.putExtra(EXTRA_ID, id)
            ContextCompat.startForegroundService(context, intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_CANCEL) {
            stopForeground(true)
            stopSelf()
            return START_NOT_STICKY
        }

        showNotification()
        super.onStartCommand(intent, flags, startId)
        return START_REDELIVER_INTENT
    }

    private fun showNotification() {
        createChannel()

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, NewActivityActivity::class.java),
            0
        )

        val cancelIntent = Intent(this, ActivityForegroundService::class.java).apply {
            action = ACTION_CANCEL
        }

        val cancelPendingIntent = PendingIntent.getService(
            this,
            1,
            cancelIntent,
            0
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Здравствуйте")
            .setContentText("Отслеживание Вашей активности")
            .setSmallIcon(R.drawable.ic_baseline_add_24)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_baseline_stop_24, "Остановить", cancelPendingIntent)
            .build()

        startForeground(1, notification)
    }

    private fun createChannel() {
        val channel =
            NotificationChannel(CHANNEL_ID, "Default channel", NotificationManager.IMPORTANCE_LOW)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}