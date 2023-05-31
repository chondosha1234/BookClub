package com.chondosha.bookclub.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chondosha.bookclub.api.models.Group
import com.chondosha.bookclub.api.models.User
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

    private val _groups: MutableStateFlow<List<Group>?> = MutableStateFlow(null)
    val groups: StateFlow<List<Group>?> = _groups.asStateFlow()

    private val _friends: MutableStateFlow<List<User>?> = MutableStateFlow(null)
    val friends: StateFlow<List<User>?> = _friends.asStateFlow()

    private val _userSearch: MutableStateFlow<List<User>?> = MutableStateFlow(null)
    val userSearch: StateFlow<List<User>?> = _userSearch.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d("Test", "inside user home view model init")
            _user.value = repository.getCurrentUser()
            Log.d("Test", "user view model after get current user")
            _groups.value = repository.getGroupList()
            Log.d("Test", "user view model after get group list")
            _friends.value = repository.getFriendsList()
        }
    }

    fun createGroup(name: String) {
        viewModelScope.launch {
            _groups.value = repository.createGroup(name)
        }
    }

    suspend fun addFriend(userId: UUID) {
        _friends.value = repository.addFriend(userId)
    }

    fun searchUserList(query: String) {
        viewModelScope.launch {
            _userSearch.value = repository.searchUserList(query)
        }
    }

    suspend fun removeFriend(userId: UUID) {
        _friends.value = repository.removeFriend(userId)
    }

    suspend fun setProfilePicture() {
        _user.value = repository.setProfilePicture().users[0]
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