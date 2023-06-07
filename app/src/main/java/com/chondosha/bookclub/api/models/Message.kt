package com.chondosha.bookclub.api.models

import java.util.*

data class Message(
    val id: UUID,
    val sender: String,
    val sender_username: String,
    val conversation: String,
    val text: String,
    val created_at: String
)
