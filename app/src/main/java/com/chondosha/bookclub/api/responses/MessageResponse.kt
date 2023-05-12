package com.chondosha.bookclub.api.responses


import com.chondosha.bookclub.api.models.Message
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter= true)
class MessageResponse {
    var messages: List<Message> = emptyList()

    @FromJson
    fun fromJson(response: JsonElement): MessageResponse {
        val messageResponse = MessageResponse()

        if (response.isJsonObject) {
            val message = Gson().fromJson(response, Message::class.java)
            messageResponse.messages = listOf(message)
        } else if (response.isJsonArray) {
            val messageList = Gson().fromJson<List<Message>>(response, object : TypeToken<List<Message>>() {}.type)
            messageResponse.messages = messageList
        }

        return messageResponse
    }
}