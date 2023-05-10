package com.chondosha.bookclub.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter= true)
class MessageResponse (
    @Json(name = "message") val messages: List<Message>
    )