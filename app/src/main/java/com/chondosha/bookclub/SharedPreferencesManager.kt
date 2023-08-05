package com.chondosha.bookclub

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SharedPreferencesManager {
    private const val PREF_NAME = "Preferences"
    private const val AUTH_TOKEN = "AuthToken"
    private const val LOGGED_IN = "LoggedIn"
    private const val NOTIFICATION_COUNT = "NotificationCount"

    private val _isLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean>
        get() = _isLoggedIn

    fun initLoggedIn(context: Context) {
        val editor = getSharedPreferences(context)
        val isLoggedIn = editor.getBoolean(LOGGED_IN, false)
        _isLoggedIn.value = isLoggedIn
    }

    private fun getSharedPreferences(context: Context):  SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isLoggedIn(context: Context) : Boolean {
        val editor = getSharedPreferences(context)
        Log.d("login", "value of Logged in pref: ${editor.getBoolean(LOGGED_IN, false)}")
        return editor.getBoolean(LOGGED_IN, false)
    }

    fun logout(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(LOGGED_IN, false)
        editor.apply()
        _isLoggedIn.value = false
    }

    fun saveAuthToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(AUTH_TOKEN, token)
        editor.putBoolean(LOGGED_IN, true)
        editor.apply()
        _isLoggedIn.value = true
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
        val editor = getSharedPreferences(context).edit()
        editor.putInt(NOTIFICATION_COUNT, count)
        editor.apply()
    }

    fun getNotificationCount(context: Context) : Int {
        val editor = getSharedPreferences(context)
        return editor.getInt(NOTIFICATION_COUNT, 0)
    }
}