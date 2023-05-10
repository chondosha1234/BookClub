package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.MessageServerApi
import com.chondosha.bookclub.api.User
import com.chondosha.bookclub.api.UserResponse

class UserRepository {
    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun createUser(): List<User> = messageServerApi.createUser().users

    suspend fun getFriendsList(): List<User> = messageServerApi.getFriendList().users

    suspend fun addFriend(userId: Int): List<User> = messageServerApi.addFriend(userId).users

    suspend fun removeFriend(userId:Int): List<User> = messageServerApi.removeFriend(userId).users

}