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

    suspend fun logout(context: Context): Unit {
        messageServerApi.logout()
        SharedPreferencesManager.logout(context)
    }

    suspend fun getCurrentUser(): User {
        Log.d("Test", "Inside get current user repo func")
        val response = messageServerApi.getCurrentUser()
        Log.d("Test", "Inside repo func response: $response")
        return messageServerApi.getCurrentUser().users[0]
    }

    suspend fun getGroupList(): List<Group> {
        Log.d("Test", "Inside get group list repo func")
        val response = messageServerApi.getGroupList()
        Log.d("Test", "Inside repo func group response: $response")
        return messageServerApi.getGroupList().groups
    }

    suspend fun getFriendsList(): List<User> = messageServerApi.getFriendList().users

    suspend fun createGroup(name: String): List<Group> {
        return messageServerApi.createGroup(CreateGroupRequest(name)).groups
    }

    suspend fun addFriend(userId: UUID): List<User> = messageServerApi.addFriend(userId).users

    suspend fun searchUserList(query: String): List<User> = messageServerApi.searchUserList(query).users

    suspend fun removeFriend(userId:UUID): List<User> = messageServerApi.removeFriend(userId).users

    suspend fun setProfilePicture() = messageServerApi.setProfilePicture()

}