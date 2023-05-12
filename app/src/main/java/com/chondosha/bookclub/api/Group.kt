package com.chondosha.bookclub.api

import java.util.UUID

data class Group(
    val id: UUID,
    val name: String,
    val conversations: List<Conversation>,
    val members: List<User>,
    val photoFileName: String? = null
)