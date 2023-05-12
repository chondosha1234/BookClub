package com.chondosha.bookclub.api.responses

import com.chondosha.bookclub.api.models.Group
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GroupResponse {
    var groups: List<Group> = emptyList()

    @FromJson
    fun fromJson(response: JsonElement): GroupResponse {
        val groupResponse = GroupResponse()

        if (response.isJsonObject) {
            val group = Gson().fromJson(response, Group::class.java)
            groupResponse.groups = listOf(group)
        } else if (response.isJsonArray) {
            val groupList = Gson().fromJson<List<Group>>(response, object : TypeToken<List<Group>>() {}.type)
            groupResponse.groups = groupList
        }

        return groupResponse
    }
}