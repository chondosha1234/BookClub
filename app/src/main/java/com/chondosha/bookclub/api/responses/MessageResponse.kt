package com.chondosha.bookclub.api.responses


import com.chondosha.bookclub.api.models.Message
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter= true)
data class MessageResponse(
    val messages: List<Message>
)