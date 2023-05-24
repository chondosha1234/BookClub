package com.chondosha.bookclub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chondosha.bookclub.api.models.Conversation
import com.chondosha.bookclub.api.models.Group
import com.chondosha.bookclub.api.models.User
import com.chondosha.bookclub.repositories.GroupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class GroupViewModel(
    private val repository: GroupRepository,
    groupIdString: String?
) : ViewModel() {

    private val _group: MutableStateFlow<Group?> = MutableStateFlow(null)
    val group: StateFlow<Group?> = _group.asStateFlow()

    private val _conversations: MutableStateFlow<List<Conversation>?> = MutableStateFlow(null)
    val conversations: StateFlow<List<Conversation>?> = _conversations.asStateFlow()

    private val _members: MutableStateFlow<List<User>?> = MutableStateFlow(null)
    val members: StateFlow<List<User>?> = _members.asStateFlow()

    init {
        val groupId = UUID.fromString(groupIdString)
        viewModelScope.launch {
            _group.value = repository.getGroup(groupId)
            _conversations.value = repository.getConversationList(groupId)
            _members.value = repository.getMemberList(groupId)
        }
    }

    fun createConversation(bookTitle: String, groupId: UUID) {
        viewModelScope.launch {
            _conversations.value = repository.createConversation(bookTitle, groupId)
        }
    }

    suspend fun addMember(groupId: UUID, userId: UUID) {
        _group.value = repository.addMember(groupId, userId)
    }

    suspend fun removeMember(groupId: UUID, userId: UUID) {
        _group.value = repository.removeMember(groupId, userId)
    }

    suspend fun setGroupPicture(groupId: UUID) {
        _group.value = repository.setGroupPicture(groupId)
    }
}

class GroupViewModelFactory(
    private val repository: GroupRepository,
    private val groupId: String?
) : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return GroupViewModel(repository, groupId) as T
    }
}