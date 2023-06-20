package com.chondosha.bookclub

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SharedPreferencesManager {
    private const val PREF_NAME = "Preferences"
    private const val AUTH_TOKEN = "AuthToken"
    private const val NOTIFICATION_COUNT = "NotificationCount"

    private fun getSharedPreferences(context: Context):  SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveAuthToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    fun getAuthToken(context: Context): String? {
        return getSharedPreferences(context).getString(AUTH_TOKEN, null)
    }

    fun clearAuthToken(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(AUTH_TOKEN)
        editor.apply()
    }

    fun changeNotificationCount(context: Context, count: Int) {
        Log.d("notification", "inside shared preferences change")
        Log.d("notification", "count: $count")
        val editor = getSharedPreferences(context).edit()
        editor.putInt(NOTIFICATION_COUNT, count)
        editor.apply()
    }

    fun getNotificationCount(context: Context) : Int {
        val editor = getSharedPreferences(context)
        Log.d("notification", "inside shared preferences get")
        Log.d("notification", "getInt count: ${editor.getInt(NOTIFICATION_COUNT, 0)}")
        return editor.getInt(NOTIFICATION_COUNT, 0)
    }
}