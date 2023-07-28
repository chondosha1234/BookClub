package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.MessageServerApi
import com.chondosha.bookclub.api.models.*
import com.chondosha.bookclub.api.responses.MessageResponse
import java.util.*

class ConversationRepository {

    private var messageServerApi: MessageServerApi = ApiServiceFactory.createMessageServerApi()

    suspend fun getConversation(conversationId: UUID): List<Conversation> = messageServerApi.getConversation(conversationId).conversations

    suspend fun getCurrentUser(): User = messageServerApi.getCurrentUser().users[0]

    suspend fun getMessages(conversationId: UUID): List<Message> = messageServerApi.getMessages(conversationId).messages

    suspend fun sendMessage(userId: UUID?, username: String?, conversationId: UUID?, text: String): List<Message> =
        messageServerApi.sendMessage(SendMessageRequest(userId, username, conversationId, text)).messages

    suspend fun setConversationPicture(conversationId: UUID, base64Image: String): Conversation {
        return messageServerApi.setConversationPicture(ConversationPictureRequest(conversationId, base64Image)).conversations[0]
    }

}