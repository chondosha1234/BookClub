package com.chondosha.bookclub.api.models

import java.util.UUID

data class Group(
    val id: UUID,
    val name: String,
    val conversations: List<Conversation>,
    val members: List<User>,
    val picture: String? = null
)