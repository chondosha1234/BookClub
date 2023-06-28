package com.chondosha.bookclub

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class NotificationClickHandler : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = applicationContext
        SharedPreferencesManager.changeNotificationCount(context, 0)

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

}