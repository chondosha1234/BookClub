package com.chondosha.bookclub.api.responses

import com.chondosha.bookclub.api.models.Token
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResponse(
    val token: Token
)
