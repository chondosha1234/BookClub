package com.chondosha.bookclub.api

data class Message(
    val id: String,
    val sender: String,
    val conversation: String,
    val text: String,
    val created_at: String
)
