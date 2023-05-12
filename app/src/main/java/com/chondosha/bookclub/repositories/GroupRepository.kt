package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.Conversation
import com.chondosha.bookclub.api.Group
import com.chondosha.bookclub.api.MessageServerApi
import com.chondosha.bookclub.api.User
import java.util.*

class GroupRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun createGroup(): List<Group> = messageServerApi.createGroup().groups

    suspend fun getGroupList(): List<Group> = messageServerApi.getGroupList().groups

    suspend fun getMemberList(groupId: UUID): List<User> = messageServerApi.getMemberList(groupId).users

    suspend fun addMember(groupId: UUID, userId: UUID): List<Group> = messageServerApi.addMember(groupId, userId).groups

    suspend fun removeMember(groupId: UUID, userId: UUID): List<Group> = messageServerApi.removeMember(groupId, userId).groups
}