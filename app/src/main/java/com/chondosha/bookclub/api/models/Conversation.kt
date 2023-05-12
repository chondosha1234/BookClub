package com.chondosha.bookclub.api.models

import com.squareup.moshi.Json
import java.util.*

data class Conversation(
    val id: UUID,
    @Json(name = "book_title") val bookTitle: String,
    val group: String,
    val messageList: List<Message>,
    val picture: String? = null
)