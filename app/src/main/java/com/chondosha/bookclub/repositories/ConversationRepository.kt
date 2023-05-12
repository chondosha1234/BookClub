package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.models.Conversation
import com.chondosha.bookclub.api.MessageServerApi
import java.util.*

class ConversationRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun createConversation(groupId: UUID): List<Conversation> = messageServerApi.createConversation(groupId).conversations

    suspend fun getConversationList(groupId: UUID): List<Conversation> = messageServerApi.getConversationList(groupId).conversations
}