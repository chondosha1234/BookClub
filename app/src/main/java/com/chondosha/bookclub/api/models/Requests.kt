package com.chondosha.bookclub.api.models

import com.google.gson.annotations.SerializedName
import java.util.*


data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class CreateAccountRequest(
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class FcmTokenRequest(
    @SerializedName("fcm_token") val fcm_token: String
)

data class SendMessageRequest(
    @SerializedName("sender") val sender: UUID?,
    @SerializedName("sender_username") val sender_username: String?,
    @SerializedName("conversation") val conversation: UUID?,
    @SerializedName("text") val text: String
)

data class CreateGroupRequest(
    @SerializedName("name") val name: String
)

data class CreateConversationRequest(
    @SerializedName("book_title") val book_title: String,
    @SerializedName("group") val group: UUID
)

data class UserProfilePictureRequest(
    @SerializedName("picture") val picture: String
)

data class GroupPictureRequest(
    @SerializedName("groupId") val groupId: UUID,
    @SerializedName("picture") val picture: String
)

data class ConversationPictureRequest(
    @SerializedName("conversationId") val conversationId: UUID,
    @SerializedName("picture") val picture: String
)
