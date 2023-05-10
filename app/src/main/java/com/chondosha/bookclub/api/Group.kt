package com.chondosha.bookclub.api

data class Group(
    val id: String,
    val name: String,
    val conversationList: List<Conversation>,
    val memberList: List<User>
)