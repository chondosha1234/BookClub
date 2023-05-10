package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.Conversation
import com.chondosha.bookclub.api.MessageServerApi

class ConversationRepository {
    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun createConversation(groupId: Int): List<Conversation> = messageServerApi.createConversation(groupId).conversations

    suspend fun getConversationList(groupId: Int): List<Conversation> = messageServerApi.getConversationList(groupId).conversations
}