package com.chondosha.bookclub.api


import android.util.Log
import androidx.lifecycle.*
import com.chondosha.bookclub.viewmodels.ConversationViewModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

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