package com.chondosha.bookclub.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.chondosha.bookclub.LocalGroupRepository
import com.chondosha.bookclub.LocalUserRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.viewmodels.GroupViewModel
import com.chondosha.bookclub.viewmodels.GroupViewModelFactory
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import com.chondosha.bookclub.viewmodels.UserHomeViewModelFactory
import java.util.*

@Composable
fun AddMemberScreen(
    modifier: Modifier = Modifier,
    groupId: String?,
    //onNavigateToHome: () -> Unit,
    onNavigateToGroup: (groupId: UUID) -> Unit,
    onNavigateToLogin: () -> Unit,
    groupViewModel : GroupViewModel = viewModel(
        factory = GroupViewModelFactory(LocalGroupRepository.current, groupId)
    ),
    userHomeViewModel : UserHomeViewModel = viewModel(
        factory = UserHomeViewModelFactory(LocalUserRepository.current)
    )
) {
    val navController = rememberNavController()

    val friends = userHomeViewModel.friends.collectAsState().value

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
            LazyColumn(
                modifier = modifier.fillMaxWidth().padding(innerPadding)
            ) {
                if (friends != null) {
                    if (friends.isEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.empty_friends_list_text),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        items(friends) { friend ->
                            AddMemberCell(
                                user = friend,
                                groupViewModel = groupViewModel,
                                onNavigateToGroup = onNavigateToGroup
                            )
                        }
                    }
                }
            }
        }
    )

}