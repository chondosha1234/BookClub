package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.Message
import com.chondosha.bookclub.api.MessageServerApi
import java.util.UUID

class MessageRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun sendMessage(): List<Message> = messageServerApi.sendMessage().messages

    suspend fun getMessages(conversationId: UUID): List<Message> = messageServerApi.getMessages(conversationId).messages
}