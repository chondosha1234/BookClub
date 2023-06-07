package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.models.Conversation
import com.chondosha.bookclub.api.MessageServerApi
import com.chondosha.bookclub.api.models.Message
import com.chondosha.bookclub.api.models.SendMessageRequest
import com.chondosha.bookclub.api.models.User
import com.chondosha.bookclub.api.responses.MessageResponse
import java.util.*

class ConversationRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun getConversation(conversationId: UUID): List<Conversation> = messageServerApi.getConversation(conversationId).conversations

    suspend fun getCurrentUser(): User = messageServerApi.getCurrentUser().users[0]

    suspend fun getMessages(conversationId: UUID): List<Message> = messageServerApi.getMessages(conversationId).messages

    suspend fun sendMessage(userId: UUID?, username: String?, conversationId: UUID?, text: String): List<Message> =
        messageServerApi.sendMessage(SendMessageRequest(userId, username, conversationId, text)).messages

    suspend fun setConversationPicture(conversationId: UUID): Conversation = messageServerApi.setConversationPicture(conversationId).conversations[0]
}