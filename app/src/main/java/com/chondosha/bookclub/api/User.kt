package com.chondosha.bookclub.api

import java.util.UUID

data class User(
    val id: UUID,
    val email: String,
    val username: String,
    val groups: List<Group>,
    val friends: List<User>
)