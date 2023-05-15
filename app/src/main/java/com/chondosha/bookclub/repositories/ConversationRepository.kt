package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.models.Conversation
import com.chondosha.bookclub.api.MessageServerApi
import com.chondosha.bookclub.api.models.Message
import com.chondosha.bookclub.api.responses.MessageResponse
import java.util.*

class ConversationRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun getConversation(conversationId: UUID): List<Conversation> = messageServerApi.getConversation(conversationId).conversations

    suspend fun getMessages(conversationId: UUID): List<Message> = messageServerApi.getMessages(conversationId).messages

    suspend fun sendMessage(): List<Message> = messageServerApi.sendMessage().messages

    suspend fun setConversationPicture(conversationId: UUID): Conversation = messageServerApi.setConversationPicture(conversationId).conversations[0]
}