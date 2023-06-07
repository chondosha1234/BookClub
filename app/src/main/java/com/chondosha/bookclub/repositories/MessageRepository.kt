package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.models.Message
import com.chondosha.bookclub.api.MessageServerApi
import com.chondosha.bookclub.api.models.SendMessageRequest
import java.util.UUID

class MessageRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    /*
    suspend fun sendMessage(userId: UUID, conversationId: UUID, text: String): List<Message> =
        messageServerApi.sendMessage(SendMessageRequest(userId, conversationId, text)).messages
     */
    suspend fun getMessages(conversationId: UUID): List<Message> = messageServerApi.getMessages(conversationId).messages
}