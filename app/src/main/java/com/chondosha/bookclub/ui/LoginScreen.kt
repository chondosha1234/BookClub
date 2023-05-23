package com.chondosha.bookclub.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chondosha.bookclub.LocalLoginRepository
import com.chondosha.bookclub.LocalUserRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.viewmodels.LoginViewModel
import com.chondosha.bookclub.viewmodels.LoginViewModelFactory

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToCreateAccount: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(LocalLoginRepository.current)
    )
) {

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginAttempted = remember { mutableStateOf(false) }

    val loginResult = loginViewModel.loginResult.collectAsState()

    val context = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.login))

        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
                loginAttempted.value = false
            },
            label = { Text(text = stringResource(R.string.username_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
                loginAttempted.value = false
            },
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
                onClick = {
                    loginViewModel.login(context, username.value, password.value)
                    loginAttempted.value = true
                },
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Text(text = stringResource(R.string.login))
            }
        }

        LaunchedEffect(loginResult.value.isSuccess) {
            Log.d("Test", "inside launched effect on login")
            if (loginResult.value.isSuccess) {
                onNavigateToHome()
            } else {
                if (loginAttempted.value) {
                    username.value = ""
                    password.value = ""
                    Toast.makeText(context, R.string.login_failed, Toast.LENGTH_LONG).show()
                }
            }
        }

        /*
        when (loginResult.value.isSuccess) {
            true -> {
                Log.d("Test", "loginResult: true")
                onNavigateToHome()
            }
            false -> {
                Log.d("Test", "loginResult: false")
                if (loginAttempted.value) {
                    username.value = ""
                    password.value = ""
                    Toast.makeText(context, R.string.login_failed, Toast.LENGTH_LONG).show()
                }
            }
        }
        */
    }
}