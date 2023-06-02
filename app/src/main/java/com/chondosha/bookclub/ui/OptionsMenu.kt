package com.chondosha.bookclub.ui

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chondosha.bookclub.LocalUserRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import com.chondosha.bookclub.viewmodels.UserHomeViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun OptionsMenu(
    userHomeViewModel: UserHomeViewModel,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val coroutineScope = rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(R.drawable.overflow_menu),
            contentDescription = "Menu"
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {

        DropdownMenuItem(onClick = {

        }) {
            Text(stringResource(R.string.settings))
        }

        DropdownMenuItem(onClick = {
            coroutineScope.launch {
                userHomeViewModel.logout()
                onNavigateToLogin()
            }
        }) {
            Text(stringResource(R.string.logout))
        }
    }
}