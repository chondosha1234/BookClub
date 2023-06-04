package com.chondosha.bookclub.repositories

import android.util.Log
import com.chondosha.bookclub.api.models.Group
import com.chondosha.bookclub.api.MessageServerApi
import com.chondosha.bookclub.api.models.Conversation
import com.chondosha.bookclub.api.models.CreateConversationRequest
import com.chondosha.bookclub.api.models.User
import java.util.*

class GroupRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun getGroup(groupId: UUID): Group = messageServerApi.getGroup(groupId).groups[0]

    suspend fun getMemberList(groupId: UUID): List<User> = messageServerApi.getMemberList(groupId).users

    suspend fun createConversation(bookTitle: String, groupId: UUID): List<Conversation> {
        return messageServerApi.createConversation(CreateConversationRequest(bookTitle, groupId)).conversations
    }

    suspend fun getConversationList(groupId: UUID): List<Conversation> = messageServerApi.getConversationList(groupId).conversations

    suspend fun addMember(groupId: UUID, userId: UUID): Group = messageServerApi.addMember(groupId, userId).groups[0]

    suspend fun removeMember(groupId: UUID, userId: UUID): Group = messageServerApi.removeMember(groupId, userId).groups[0]

    suspend fun setGroupPicture(groupId: UUID): Group = messageServerApi.setGroupPicture(groupId).groups[0]
}