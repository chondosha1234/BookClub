package com.chondosha.bookclub.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun CreateAccountScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit
) {

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(LocalLoginRepository.current)
    )

    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val createAttempted = remember { mutableStateOf(false) }

    val createResult = loginViewModel.createResult.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Text(text = stringResource(R.string.login))

        TextField(
            value = email.value,
            onValueChange = {
                email.value = it
                createAttempted.value = false
                            },
            label = { Text(text = stringResource(R.string.email_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
                createAttempted.value = false
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
                createAttempted.value = false
                            },
            label = { Text(text = stringResource(R.string.password_label)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Button(
            onClick = {
                loginViewModel.createAccount(email.value, username.value, password.value)
                createAttempted.value = true
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(end = 16.dp)
        ) {
            Text(text = stringResource(R.string.create_account))
        }
    }

    when (createResult.value.isSuccess) {
        true -> {
            onNavigateToLogin()
        }
        false -> {
            if (createAttempted.value) {
                email.value = ""
                username.value = ""
                password.value = ""
                Toast.makeText(context, R.string.create_account_failed, Toast.LENGTH_LONG).show()
            }
        }
    }
}