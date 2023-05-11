package com.chondosha.bookclub.api

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserResponse {
    var users: List<User> = emptyList()

    @FromJson
    fun fromJson(response: JsonElement): UserResponse {
        val userResponse = UserResponse()

        if (response.isJsonObject) {
            val user = Gson().fromJson(response, User::class.java)
            userResponse.users = listOf(user)
        } else if (response.isJsonArray) {
            val userList = Gson().fromJson<List<User>>(response, object : TypeToken<List<User>>() {}.type)
            userResponse.users = userList
        }

        return userResponse
    }
}