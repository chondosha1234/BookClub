package com.chondosha.bookclub.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GroupResponse (
    @Json(name = "group") val groups: List<Group>
    )