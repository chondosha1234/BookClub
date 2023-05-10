package com.chondosha.bookclub.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ConversationResponse (
    @Json(name = "conversation") val conversations: List<Conversation>
    )