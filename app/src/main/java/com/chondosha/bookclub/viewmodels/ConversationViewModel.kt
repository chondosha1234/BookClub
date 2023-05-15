package com.chondosha.bookclub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chondosha.bookclub.api.models.Conversation
import com.chondosha.bookclub.api.models.Message
import com.chondosha.bookclub.api.models.User
import com.chondosha.bookclub.repositories.ConversationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*


class ConversationViewModel(
    private val repository: ConversationRepository,
    conversationIdString: String?
): ViewModel() {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _conversation: MutableStateFlow<Conversation?> = MutableStateFlow(null)
    val conversation = _conversation.asStateFlow()

    private val _messages: MutableStateFlow<List<Message>?> = MutableStateFlow(null)
    val messages = _messages.asStateFlow()

    init{
        val conversationId = UUID.fromString(conversationIdString)
        viewModelScope.launch {
            _user.value = repository.getCurrentUser()
            _conversation.value = repository.getConversation(conversationId)[0]
            _messages.value = repository.getMessages(conversationId)
        }
    }

    suspend fun sendMessage(text: String) {
        _messages.value = repository.sendMessage(user.value?.id, conversation.value?.id, text)
    }

    suspend fun setConversationPicture(conversationId: UUID) {
        _conversation.value = repository.setConversationPicture(conversationId)
    }
}

class ConversationViewModelFactory(
    private val repository: ConversationRepository,
    private val conversationId: String?
) : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return ConversationViewModel(repository, conversationId) as T
    }
}