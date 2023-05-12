package com.chondosha.bookclub.api.responses

import com.chondosha.bookclub.api.models.Conversation
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ConversationResponse {
    var conversations: List<Conversation> = emptyList()

    @FromJson
    fun fromJson(response: JsonElement): ConversationResponse {
        val conversationResponse = ConversationResponse()

        if (response.isJsonObject) {
            val conversation = Gson().fromJson(response, Conversation::class.java)
            conversationResponse.conversations = listOf(conversation)
        } else if (response.isJsonArray) {
            val conversationList = Gson().fromJson<List<Conversation>>(response, object : TypeToken<List<Conversation>>() {}.type)
            conversationResponse.conversations = conversationList
        }

        return conversationResponse
    }
}
