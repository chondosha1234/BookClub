package com.chondosha.bookclub.api

import java.util.*

data class Conversation(
    val id: UUID,
    val bookTitle: String,
    val group: String,
    val messageList: List<Message>
)