package com.chondosha.bookclub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chondosha.bookclub.LocalUserRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.viewmodels.LoginViewModel
import com.chondosha.bookclub.viewmodels.LoginViewModelFactory

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToCreateAccount: () -> Unit
) {
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(LocalUserRepository.current)
    )

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("")}

    val loginResult = loginViewModel.loginResult.collectAsState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.login))

        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = stringResource(R.string.username_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = stringResource(R.string.password_label)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onNavigateToCreateAccount() },
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(text = stringResource(R.string.signup))
            }

            Button(
                onClick = { loginViewModel.login(username.value, password.value) },
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Text(text = stringResource(R.string.login))
            }
        }

        when (loginResult.value.isSuccess) {
            true -> {
                val userResponse = loginResult.value.getOrNull()  // maybe not needed if authenticated user collects info on home screen
                onNavigateToHome()
            }
            false -> {
                Text(text= "Login Failed")
            }
        }
    }
}