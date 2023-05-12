package com.chondosha.bookclub.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chondosha.bookclub.api.UserResponse
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

    fun login(username: String, password: String) {

        viewModelScope.launch {
            _loginResult.value = Result.failure(Exception("Loading"))

            val result = repository.login(username, password)
            _loginResult.value = result
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