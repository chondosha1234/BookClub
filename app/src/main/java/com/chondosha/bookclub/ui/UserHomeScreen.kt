package com.chondosha.bookclub.ui

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.chondosha.bookclub.LocalUserRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Group
import com.chondosha.bookclub.api.models.User
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import com.chondosha.bookclub.viewmodels.UserHomeViewModelFactory
import java.util.*
import com.chondosha.bookclub.BitmapUtils.toBase64


@Composable
fun UserHomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToGroup: (groupId: UUID) -> Unit,
    onNavigateToCreateGroup: () -> Unit,
    onNavigateToAddFriend: () -> Unit,
    onNavigateToLogin: () -> Unit,
    userHomeViewModel : UserHomeViewModel = viewModel(
        factory = UserHomeViewModelFactory(LocalUserRepository.current)
    )
){

    //val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    val user by userHomeViewModel.user.collectAsState()
    val groups by userHomeViewModel.groups.collectAsState()
    val friends by userHomeViewModel.friends.collectAsState()

    val imagePainter = if (user?.picture != null) {
        rememberAsyncImagePainter(
            model = user?.picture,
            placeholder = painterResource(R.drawable.no_picture)
        )
    } else {
        painterResource(R.drawable.no_picture)
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        val base64Image = bitmap.toBase64()
        Log.d("picture", "value of base64Image: $base64Image")
        userHomeViewModel.setProfilePicture(base64Image)
    }

    val selectedTab = remember { mutableStateOf(0)}
    val topPictureBeingViewed = remember { mutableStateOf(false) }

    if (topPictureBeingViewed.value) {
        PictureDialog(
            imagePainter = imagePainter,
            cameraLauncher = cameraLauncher,
            canChangePicture = true
        ) {
            topPictureBeingViewed.value = false
        }
    }

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
                    Image(
                        painter = imagePainter,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(24.dp)
                            .clip(CircleShape)
                            .clickable {
                                topPictureBeingViewed.value = true
                                //cameraLauncher.launch(null)
                            }
                    )
                    OptionsMenu(
                        userHomeViewModel = userHomeViewModel,
                        onNavigateToLogin = onNavigateToLogin
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    selected = selectedTab.value == 0,
                    onClick = { selectedTab.value = 0 },
                    icon = { },  //todo find icon
                    label = { Text(text = "Groups") }
                )
                BottomNavigationItem(
                    selected = selectedTab.value == 1,
                    onClick = { selectedTab.value = 1 },
                    icon = {},
                    label = { Text(text = "Friends") }
                )
            }
        }
    ) { innerPadding ->
        when (selectedTab.value) {
            0 -> Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Log.d("Test", "Inside composable when block 0 selectedTab")
                GroupList(
                    groups = groups,
                    userHomeViewModel = userHomeViewModel,
                    onNavigateToGroup = onNavigateToGroup,
                    onNavigateToCreateGroup = onNavigateToCreateGroup,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .weight(.9f, fill = true)
                )
            }
            1 -> Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Log.d("Test", "Inside composable when block 1 selectedTab")
                FriendList(
                    friends = friends,
                    userHomeViewModel = userHomeViewModel,
                    onNavigateToAddFriend = onNavigateToAddFriend,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .weight(.9f, fill = true)
                )
            }
        }
    }
}

@Composable
fun GroupList(
    groups: List<Group>?,
    userHomeViewModel: UserHomeViewModel,
    modifier: Modifier = Modifier,
    onNavigateToGroup: (groupId: UUID) -> Unit,
    onNavigateToCreateGroup: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
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
                        userHomeViewModel = userHomeViewModel,
                        onClickCell = { onNavigateToGroup(group.id) },
                    )
                }
            }
        }
    }
    Button(
        onClick = { onNavigateToCreateGroup() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ){
        Text(text = stringResource(R.string.add_group))
    }
}

@Composable
fun FriendList(
    friends: List<User>?,
    modifier: Modifier = Modifier,
    userHomeViewModel: UserHomeViewModel,
    onNavigateToAddFriend: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
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
    Button(
        onClick = { onNavigateToAddFriend() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ){
        Text(text = stringResource(R.string.add_friend))
    }
}

