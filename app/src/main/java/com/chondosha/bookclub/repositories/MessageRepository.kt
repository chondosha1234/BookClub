package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.Message
import com.chondosha.bookclub.api.MessageServerApi

class MessageRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun sendMessage(): List<Message> = messageServerApi.sendMessage().messages

    suspend fun getMessages(conversationId: Int): List<Message> = messageServerApi.getMessages(conversationId).messages
}