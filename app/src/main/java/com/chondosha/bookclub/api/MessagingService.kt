package com.chondosha.bookclub.api


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.*
import com.chondosha.bookclub.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*
import android.Manifest

class MessagingService : FirebaseMessagingService(), LifecycleObserver {

    private var isAppInForeground = false
    private var onConversationScreen = true

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }

    /*
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForegroundStart() {
        isAppInForeground = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onForegroundStop() {
        isAppInForeground = false
    }

     */

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification?.title
            val body = remoteMessage.notification?.body
            Log.d("fcm", "Value of notification title: $title")
            Log.d("fcm", "Value of notification body: $body")

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

                val notificationBuilder = NotificationCompat.Builder(
                    this,
                    getString(R.string.message_notification_channel_id)
                )
                    .setSmallIcon(R.drawable.message_notification)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)


                with(NotificationManagerCompat.from(this)) {
                    notify(generateNotificationId(), notificationBuilder.build())
                }
            }
        }
        /*
        if (isAppInForeground && onConversationScreen) {
            val conversationModel: ConversationViewModel? = null
            //conversationModel.getMessages()
        } else {
            // send notification to tray
        }
         */
    }

    private fun generateNotificationId(): Int {
        val timestamp = Calendar.getInstance().timeInMillis
        return timestamp.toInt()
    }

    private inner class AppLifecycleObserver : DefaultLifecycleObserver {

        override fun onStart(owner: LifecycleOwner) {
            isAppInForeground = true
        }

        override fun onStop(owner: LifecycleOwner) {
            isAppInForeground = false
        }
    }

    fun setConversationScreenFlag(onScreen: Boolean) {
        onConversationScreen = onScreen
    }
}