package com.chondosha.bookclub.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageServerApi {

    @POST("messages/send")
    suspend fun sendMessage()

    @GET("messages/{conversationId}")
    suspend fun getMessages(@Path("conversationId") conversationId: Int): MessageResponse

    @POST("groups/create")
    suspend fun createGroup(): GroupResponse

    @GET("groups/{groupId}/get_member_list")
    suspend fun getMemberList(@Path("groupId") groupId: Int): UserResponse

    @POST("groups/{groupId}/{userId}/add_member")
    suspend fun addMember(@Path("groupId") groupId: Int, @Path("userId") userId: Int): GroupResponse

    @POST("groups/{groupId}/{userId}/remove_member")
    suspend fun removeMember(@Path("groupId") groupId: Int, @Path("userId") userId: Int): GroupResponse

    @POST("groups/conversations/create")
    suspend fun createConversation(): ConversationResponse

    @GET("users/groups")
    suspend fun getGroupList(): GroupResponse

    @GET("users/{groupId}/conversations")
    suspend fun getConversationList(@Path("groupId") groupId: Int): ConversationResponse

    @POST("users/{userId}/add_friend")
    suspend fun addFriend(@Path("userId") userId: Int): UserResponse

    @POST("users/{userId}/remove_friend")
    suspend fun removeFriend(@Path("userId") userId: Int): UserResponse

    @GET("users/get_friends_list")
    suspend fun getFriendList(): UserResponse

    @POST("users/create_user")
    suspend fun createUser(): UserResponse
}