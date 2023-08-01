package com.chondosha.bookclub.api.models

import com.squareup.moshi.Json
import java.util.*

data class Conversation(
    val id: UUID,
    @Json(name = "book_title") val bookTitle: String,
    val group: UUID,
    //val messageList: List<Message>,
    @Json(name = "picture_url") val picture: String? = null
)