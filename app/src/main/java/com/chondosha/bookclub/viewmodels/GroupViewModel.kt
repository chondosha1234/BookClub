package com.chondosha.bookclub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chondosha.bookclub.api.models.Group
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

    init {
        val groupId = UUID.fromString(groupIdString)
        viewModelScope.launch {
            _group.value = repository.getGroup(groupId)
        }
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