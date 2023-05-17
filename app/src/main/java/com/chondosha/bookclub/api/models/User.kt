package com.chondosha.bookclub.api.models

import com.squareup.moshi.Json
import java.util.UUID

data class User(
    val id: UUID,
    val email: String,
    val username: String,
    val groups: List<Group>,
    val friends: List<User>,
    @Json(name = "picture_url") val picture: String? = null
)