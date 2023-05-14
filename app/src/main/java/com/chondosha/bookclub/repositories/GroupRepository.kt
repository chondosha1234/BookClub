package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.models.Group
import com.chondosha.bookclub.api.MessageServerApi
import com.chondosha.bookclub.api.models.Conversation
import com.chondosha.bookclub.api.models.User
import java.util.*

class GroupRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun getGroup(groupId: UUID): Group = messageServerApi.getGroup(groupId).groups[0]

    suspend fun getMemberList(groupId: UUID): List<User> = messageServerApi.getMemberList(groupId).users

    suspend fun getConversationList(groupId: UUID): List<Conversation> = messageServerApi.getConversationList(groupId).conversations

    suspend fun addMember(groupId: UUID, userId: UUID): List<Group> = messageServerApi.addMember(groupId, userId).groups

    suspend fun removeMember(groupId: UUID, userId: UUID): List<Group> = messageServerApi.removeMember(groupId, userId).groups
}