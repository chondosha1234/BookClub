package com.chondosha.bookclub.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
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
import com.chondosha.bookclub.LocalUserRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Group
import com.chondosha.bookclub.api.models.User
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import com.chondosha.bookclub.viewmodels.UserHomeViewModelFactory
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun UserHomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToGroup: (groupId: UUID) -> Unit,
    userHomeViewModel : UserHomeViewModel = viewModel(
        factory = UserHomeViewModelFactory(LocalUserRepository.current)
    )
){

    //val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    //val user by userHomeViewModel.user.collectAsState()
    val groups by userHomeViewModel.groups.collectAsState()
    val friends by userHomeViewModel.friends.collectAsState()

    val selectedItem = remember { mutableStateOf(0)}

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
                    OptionsMenu(userHomeViewModel)
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    selected = selectedItem.value == 0,
                    onClick = { selectedItem.value = 0 },
                    icon = { },  //todo find icon
                    label = { Text(text = "Groups") }
                )
                BottomNavigationItem(
                    selected = selectedItem.value == 1,
                    onClick = { selectedItem.value = 1 },
                    icon = {},
                    label = { Text(text = "Friends") }
                )
            }
        }
    ) { innerPadding ->
        val currentItem = selectedItem.value
        when (currentItem) {
            0 -> Column {
                Log.d("Test", "Inside composable when block 0 selectedItem")
                GroupList(
                    groups = groups,
                    onNavigateToGroup = onNavigateToGroup,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                )

                Button(
                    onClick = {}
                ){
                    Text(text = stringResource(R.string.add_group))
                }
            }
            1 -> Column {
                Log.d("Test", "Inside composable when block 1 selectedItem")
                FriendList(
                    friends = friends,
                    userHomeViewModel = userHomeViewModel,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                )
                Button(
                    onClick = {
                        // launch dialog or screen?
                    }
                ){
                    Text(text = stringResource(R.string.add_friend))
                }
            }
        }
    }
}

@Composable
fun GroupList(
    groups: List<Group>?,
    modifier: Modifier = Modifier,
    onNavigateToGroup: (groupId: UUID) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        if (groups != null) {
            if (groups.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.empty_group_list_text),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(groups) { group ->
                    GroupCell(
                        group = group,
                        onClickCell = { onNavigateToGroup(group.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun FriendList(
    friends: List<User>?,
    modifier: Modifier = Modifier,
    userHomeViewModel: UserHomeViewModel
) {

    LazyColumn(
        modifier = modifier
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
                    FriendCell(
                        user = friend,
                        userHomeViewModel = userHomeViewModel
                    )
                }
            }
        }
    }
}

