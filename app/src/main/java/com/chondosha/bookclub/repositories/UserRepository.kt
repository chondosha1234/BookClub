package com.chondosha.bookclub.repositories

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.chondosha.bookclub.SharedPreferencesManager
import com.chondosha.bookclub.api.*
import com.chondosha.bookclub.api.models.*
import com.chondosha.bookclub.api.responses.TokenResponse
import com.chondosha.bookclub.api.responses.UserResponse
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.util.UUID

class UserRepository {

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
                    //setFcmToken()
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
        messageServerApi.setFcmToken(FcmTokenRequest(token))
    }

    suspend fun createUser(email: String, username: String, password: String): Result<UserResponse> {
        return try {
            val request = CreateAccountRequest(email, username, password)
            val response = messageServerApi.createUser(request)
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

    suspend fun logout(): Unit = messageServerApi.logout()

    suspend fun getCurrentUser(): User = messageServerApi.getCurrentUser().users[0]

    suspend fun getGroupList(): List<Group> = messageServerApi.getGroupList().groups

    suspend fun getFriendsList(): List<User> = messageServerApi.getFriendList().users

    suspend fun createGroup(): List<Group> = messageServerApi.createGroup().groups

    suspend fun addFriend(userId: UUID): List<User> = messageServerApi.addFriend(userId).users

    suspend fun removeFriend(userId:UUID): List<User> = messageServerApi.removeFriend(userId).users

    suspend fun setProfilePicture() = messageServerApi.setProfilePicture()

}