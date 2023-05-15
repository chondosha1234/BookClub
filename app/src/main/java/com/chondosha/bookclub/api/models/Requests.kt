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
    @SerializedName("fcm_token") val token: String
)

data class SendMessageRequest(
    @SerializedName("sender") val sender: UUID?,
    @SerializedName("conversation") val conversation: UUID?,
    @SerializedName("text") val text: String
)