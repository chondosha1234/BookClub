package com.chondosha.bookclub.api

import java.util.*

data class Message(
    val id: UUID,
    val sender: String,
    val conversation: String,
    val text: String,
    val created_at: String
)
