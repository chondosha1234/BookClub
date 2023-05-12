package com.chondosha.bookclub.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

interface MessageServerApi {

    @POST("messages/send")
    suspend fun sendMessage(): MessageResponse

    @GET("messages/{conversationId}")
    suspend fun getMessages(@Path("conversationId") conversationId: UUID): MessageResponse


    @POST("groups/create")
    suspend fun createGroup(): GroupResponse

    @GET("groups/{groupId}/get_member_list")
    suspend fun getMemberList(@Path("groupId") groupId: UUID): UserResponse

    @POST("groups/{groupId}/{userId}/add_member")
    suspend fun addMember(@Path("groupId") groupId: UUID, @Path("userId") userId: UUID): GroupResponse

    @POST("groups/{groupId}/{userId}/remove_member")
    suspend fun removeMember(@Path("groupId") groupId: UUID, @Path("userId") userId: UUID): GroupResponse


    @POST("groups/{groupId}/create_conversation")
    suspend fun createConversation(@Path("groupId") groupId: UUID): ConversationResponse

    @GET("groups/{groupId}/conversations")
    suspend fun getConversationList(@Path("groupId") groupId: UUID): ConversationResponse


    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<UserResponse>

    @POST("logout")
    suspend fun logout(): Unit

    @GET("users/groups")
    suspend fun getGroupList(): GroupResponse

    @POST("users/{userId}/add_friend")
    suspend fun addFriend(@Path("userId") userId: UUID): UserResponse

    @POST("users/{userId}/remove_friend")
    suspend fun removeFriend(@Path("userId") userId: UUID): UserResponse

    @GET("users/get_friends_list")
    suspend fun getFriendList(): UserResponse

    @POST("users/create_user")
    suspend fun createUser(): UserResponse

    @GET("users/get_current_user")
    suspend fun getCurrentUser(): UserResponse
}

data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)