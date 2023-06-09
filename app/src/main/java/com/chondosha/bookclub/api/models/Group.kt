package com.chondosha.bookclub.api.models

import com.squareup.moshi.Json
import java.util.UUID

data class Group(
    val id: UUID,
    val name: String,
    @Json(name = "picture_url") val picture: String? = null
)