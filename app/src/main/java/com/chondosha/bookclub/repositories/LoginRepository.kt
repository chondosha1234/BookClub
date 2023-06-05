package com.chondosha.bookclub.repositories

import android.content.Context
import android.util.Log
import com.chondosha.bookclub.SharedPreferencesManager
import com.chondosha.bookclub.api.MessageServerApi
import com.chondosha.bookclub.api.models.CreateAccountRequest
import com.chondosha.bookclub.api.models.FcmTokenRequest
import com.chondosha.bookclub.api.models.LoginRequest
import com.chondosha.bookclub.api.responses.TokenResponse
import com.chondosha.bookclub.api.responses.UserResponse
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

class LoginRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun login(context: Context, username: String, password: String): Result<TokenResponse> {
        Log.d("Test", "inside repository login function")
        return try {
            val request = LoginRequest(username, password)
            val gson = Gson()
            val requestJson = gson.toJson(request)
            Log.d("Test", "request in repo: $requestJson")
            val response = messageServerApi.login(request)
            Log.d("Test", "response in repo: ${response.isSuccessful}")
            if (response.isSuccessful) {
                val tokenResponse = response.body()
                if (tokenResponse != null) {
                    val authToken = tokenResponse.token.key
                    SharedPreferencesManager.saveAuthToken(context, authToken)
                    setFcmToken()
                    Result.success(tokenResponse)
                } else {
                    Result.failure(Exception("Login response body is null"))
                }
            } else {
                Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Log.d("Test", "in repo catch block: $e")
            Result.failure(e)
        }
    }

    private suspend fun setFcmToken() {
        val token = FirebaseMessaging.getInstance().token.await()
        Log.d("fcm", "FCM token generated: $token")
        messageServerApi.setFcmToken(FcmTokenRequest(token))
    }

    suspend fun createUser(email: String, username: String, password: String): Result<UserResponse> {
        Log.d("Test", "inside repository create function")
        return try {
            val request = CreateAccountRequest(email, username, password)
            val response = messageServerApi.createUser(request)
            Log.d("Test", "response in repository from api: $response")
            if (response.isSuccessful) {
                val userResponse = response.body()
                if (userResponse != null) {
                    Result.success(userResponse)
                } else {
                    Result.failure(Exception("Create user response body is null"))
                }
            } else {
                Result.failure(Exception("Create user failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}