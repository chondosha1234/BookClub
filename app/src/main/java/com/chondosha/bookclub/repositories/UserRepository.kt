package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.*
import java.util.UUID

class UserRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun login(username: String, password: String): Result<UserResponse> {
        return try {
            val response = messageServerApi.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                val userResponse = response.body()
                if (userResponse != null) {
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

    suspend fun logout(): Unit = messageServerApi.logout()

    suspend fun getCurrentUser(): List<User> = messageServerApi.getCurrentUser().users

    suspend fun getGroupList(): List<Group> = messageServerApi.getGroupList().groups
    // maybe unnecessary?

    suspend fun createUser(): List<User> = messageServerApi.createUser().users

    suspend fun getFriendsList(): List<User> = messageServerApi.getFriendList().users

    suspend fun addFriend(userId: UUID): List<User> = messageServerApi.addFriend(userId).users

    suspend fun removeFriend(userId:UUID): List<User> = messageServerApi.removeFriend(userId).users

}