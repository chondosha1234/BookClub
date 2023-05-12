package com.chondosha.bookclub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chondosha.bookclub.api.User
import com.chondosha.bookclub.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class UserHomeViewModel(
    private val repository: UserRepository
): ViewModel() {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            _user.value = repository.getCurrentUser()[0]
        }
    }

    suspend fun getGroupList() {
        repository.getGroupList()
    }

    suspend fun getFriendsList() {
        repository.getFriendsList()
    }

    suspend fun addFriend(userId: UUID) {
        repository.addFriend(userId)
    }

    suspend fun removeFriend(userId: UUID) {
        repository.removeFriend(userId)
    }

    suspend fun logout() {
        repository.logout()
    }
}

class UserHomeViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return UserHomeViewModel(repository) as T
    }
}