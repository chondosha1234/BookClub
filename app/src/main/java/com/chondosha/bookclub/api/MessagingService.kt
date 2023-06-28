package com.chondosha.bookclub.api


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.*
import com.chondosha.bookclub.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*
import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.chondosha.bookclub.NotificationClickHandler
import com.chondosha.bookclub.SharedPreferencesManager

class MessagingService : FirebaseMessagingService(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val context = applicationContext

        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification?.title
            val body = remoteMessage.notification?.body

            val hasNotificationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED

            if (hasNotificationPermission) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channelId = getString(R.string.message_notification_channel_id)
                    val channelName = getString(R.string.message_notification_channel_name)
                    val importance = NotificationManager.IMPORTANCE_HIGH
                    val channel = NotificationChannel(channelId, channelName, importance)
                    val notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }

                val notificationCount = SharedPreferencesManager.getNotificationCount(context)
                SharedPreferencesManager.changeNotificationCount(context, notificationCount + 1)

                /*
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                 */
                Log.d("context", "context in messaging service: $context")
                val intent = Intent(context, NotificationClickHandler::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                val notificationBuilder = NotificationCompat.Builder(
                    this,
                    getString(R.string.message_notification_channel_id)
                )
                    .setSmallIcon(R.drawable.message_notification)
                    .setContentTitle(title)
                    .setContentText(
                        if (notificationCount < 1){
                            getString(R.string.message_notification_body)
                        } else {
                            getString(R.string.messages_notification_body, (notificationCount + 1).toString())
                        }
                    )
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)

                with(NotificationManagerCompat.from(this)) {
                    notify(0, notificationBuilder.build())
                }
            }
        }
    }

    private fun generateNotificationId(): Int {
        val timestamp = Calendar.getInstance().timeInMillis
        return timestamp.toInt()
    }

}