package com.chondosha.bookclub.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.chondosha.bookclub.LocalUserRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import com.chondosha.bookclub.viewmodels.UserHomeViewModelFactory

@Composable
fun AddFriendScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    userHomeViewModel : UserHomeViewModel = viewModel(
        factory = UserHomeViewModelFactory(LocalUserRepository.current)
    )
) {
    val navController = rememberNavController()

    val searchQuery = remember { mutableStateOf("") }
    val userList by userHomeViewModel.userSearch.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(
                            onClick = { navController.navigateUp() }
                        ) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                } else {
                    null
                },
                actions = {
                    OptionsMenu(
                        userHomeViewModel = userHomeViewModel,
                        onNavigateToLogin = onNavigateToLogin
                    )
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier.padding(innerPadding)
            ) {
                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    label = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Button(
                    onClick = {
                        userHomeViewModel.searchUserList(searchQuery.value)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(end = 16.dp)
                ) {
                    Text(text = stringResource(R.string.search))
                }

                if (userList != null) {
                    if (userList!!.isEmpty()) {
                        Text("No Users found")
                    } else {
                        LazyColumn {
                            items(userList!!) { user ->
                                AddFriendCell(
                                    user = user,
                                    userHomeViewModel = userHomeViewModel,
                                    onNavigateToHome = onNavigateToHome
                                )
                            }
                        }

                    }
                }
            }
        }
    )

}