package com.chondosha.bookclub.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chondosha.bookclub.api.responses.TokenResponse
import com.chondosha.bookclub.api.responses.UserResponse
import com.chondosha.bookclub.repositories.LoginRepository
import com.chondosha.bookclub.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
    ) : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<TokenResponse>>(Result.failure(Exception("Initial state")))
    val loginResult: StateFlow<Result<TokenResponse>> = _loginResult.asStateFlow()

    private val _createResult = MutableStateFlow<Result<UserResponse>>(Result.failure(Exception("Initial State")))
    val createResult: StateFlow<Result<UserResponse>> = _createResult.asStateFlow()

    fun login(context: Context, username: String, password: String) {

        viewModelScope.launch {
            Log.d("Test", "inside view model login function")
            _loginResult.value = Result.failure(Exception("Loading"))

            val result = repository.login(context, username, password)
            Log.d("Test", "result in viewmodel: $result")
            _loginResult.value = result
        }
    }

    fun createAccount(email: String, username: String, password: String) {
        viewModelScope.launch {
            Log.d("Test", "inside view model create function")
            _createResult.value = Result.failure(Exception("Loading"))

            val result = repository.createUser(email, username, password)
            Log.d("Test", "result in viewmodel: $result")
            _createResult.value = result
        }
    }
}

class LoginViewModelFactory(
    private val repository: LoginRepository
) : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}