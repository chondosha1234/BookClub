package com.chondosha.bookclub.api


import com.chondosha.bookclub.api.models.*
import com.chondosha.bookclub.api.responses.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

interface MessageServerApi {

    @POST("messages/send")
    suspend fun sendMessage(@Body request: SendMessageRequest): MessageResponse

    @GET("messages/{conversationId}")
    suspend fun getMessages(@Path("conversationId") conversationId: UUID): MessageResponse


    @POST("groups/create")
    suspend fun createGroup(@Body request: CreateGroupRequest): GroupResponse

    @GET("groups/{groupId}/get_group")
    suspend fun getGroup(@Path("groupId") groupId: UUID): GroupResponse

    @GET("groups/{groupId}/get_member_list")
    suspend fun getMemberList(@Path("groupId") groupId: UUID): UserResponse

    @POST("groups/set_picture")
    suspend fun setGroupPicture(@Body groupPictureRequest: GroupPictureRequest): GroupResponse

    @POST("groups/{groupId}/{userId}/add_member")
    suspend fun addMember(@Path("groupId") groupId: UUID, @Path("userId") userId: UUID): GroupResponse

    @POST("groups/{groupId}/{userId}/remove_member")
    suspend fun removeMember(@Path("groupId") groupId: UUID, @Path("userId") userId: UUID): GroupResponse


    @POST("groups/create_conversation")
    suspend fun createConversation(@Body request: CreateConversationRequest): ConversationResponse

    @GET("groups/conversation/{conversationId}")
    suspend fun getConversation(@Path("conversationId") conversationId: UUID): ConversationResponse

    @GET("groups/{groupId}/conversations")
    suspend fun getConversationList(@Path("groupId") groupId: UUID): ConversationResponse

    @POST("groups/conversation/set_picture")
    suspend fun setConversationPicture(@Body conversationPictureRequest: ConversationPictureRequest): ConversationResponse


    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<TokenResponse>

    @POST("users/set_fcm_token")
    suspend fun setFcmToken(@Body request: FcmTokenRequest): UserResponse

    @POST("logout")
    suspend fun logout()

    @POST("users/create_user")
    suspend fun createUser(@Body createAccountRequest: CreateAccountRequest): Response<UserResponse>

    @GET("users/get_current_user")
    suspend fun getCurrentUser(): UserResponse

    @POST("users/set_picture")
    suspend fun setProfilePicture(@Body userProfilePictureRequest: UserProfilePictureRequest): UserResponse

    @GET("users/groups")
    suspend fun getGroupList(): GroupResponse

    @POST("users/{userId}/add_friend")
    suspend fun addFriend(@Path("userId") userId: UUID): UserResponse

    @GET("users/search/{query}")
    suspend fun searchUserList(@Path("query") query: String): UserResponse

    @POST("users/{userId}/remove_friend")
    suspend fun removeFriend(@Path("userId") userId: UUID): UserResponse

    @GET("users/get_friends_list")
    suspend fun getFriendList(): UserResponse

}
