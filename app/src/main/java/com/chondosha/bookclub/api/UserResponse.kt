package com.chondosha.bookclub.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserResponse (
    @Json(name = "friend") val friends: List<User>
    )