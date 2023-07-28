package com.chondosha.bookclub.repositories

import android.util.Log
import com.chondosha.bookclub.api.MessageServerApi
import com.chondosha.bookclub.api.models.*
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

    suspend fun setGroupPicture(groupId: UUID, base64Image: String): Group {
        return messageServerApi.setGroupPicture(GroupPictureRequest(groupId, base64Image)).groups[0]
    }
}