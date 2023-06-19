package com.chondosha.bookclub

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationListener: NotificationListenerService() {

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d("notification", "Inside onNotificationRemoved")
        val context = applicationContext
        val packageName = "com.chondosha.bookclub"
        if (sbn.packageName == packageName) {
            SharedPreferencesManager.changeNotificationCount(context, 0)
        }
    }
}