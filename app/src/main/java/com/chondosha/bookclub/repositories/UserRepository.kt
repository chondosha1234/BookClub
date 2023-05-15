package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.*
import com.chondosha.bookclub.api.models.*
import com.chondosha.bookclub.api.responses.UserResponse
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import java.util.UUID

class UserRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun login(username: String, password: String): Result<UserResponse> {
        return try {
            val response = messageServerApi.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                val userResponse = response.body()
                if (userResponse != null) {
                    setFcmToken()
                    Result.success(userResponse)
                } else {
                    Result.failure(Exception("Login response body is null"))
                }
            } else {
                Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun setFcmToken() {
        val token = FirebaseMessaging.getInstance().token.await()
        messageServerApi.setFcmToken(FcmTokenRequest(token))
    }

    suspend fun createUser(email: String, username: String, password: String): Result<UserResponse> {
        return try {
            val response = messageServerApi.createUser(CreateAccountRequest(email, username, password))
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
    // maybe unnecessary?

    suspend fun getFriendsList(): List<User> = messageServerApi.getFriendList().users

    suspend fun createGroup(): List<Group> = messageServerApi.createGroup().groups

    suspend fun addFriend(userId: UUID): List<User> = messageServerApi.addFriend(userId).users

    suspend fun removeFriend(userId:UUID): List<User> = messageServerApi.removeFriend(userId).users

    suspend fun setProfilePicture() = messageServerApi.setProfilePicture()

}