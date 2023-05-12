package com.chondosha.bookclub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chondosha.bookclub.api.responses.UserResponse
import com.chondosha.bookclub.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
    ) : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<UserResponse>>(Result.failure(Exception("Initial state")))
    val loginResult: StateFlow<Result<UserResponse>> = _loginResult.asStateFlow()

    private val _createResult = MutableStateFlow<Result<UserResponse>>(Result.failure(Exception("Initial State")))
    val createResult: StateFlow<Result<UserResponse>> = _createResult.asStateFlow()

    fun login(username: String, password: String) {

        viewModelScope.launch {
            _loginResult.value = Result.failure(Exception("Loading"))

            val result = repository.login(username, password)
            _loginResult.value = result
        }
    }

    fun createAccount(email: String, username: String, password: String) {
        viewModelScope.launch {
            _createResult.value = Result.failure(Exception("Loading"))

            val result = repository.createUser(email, username, password)
            _createResult.value = result
        }
    }
}

class LoginViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}