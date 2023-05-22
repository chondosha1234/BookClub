package com.chondosha.bookclub.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.chondosha.bookclub.LocalGroupRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Conversation
import com.chondosha.bookclub.api.models.User
import com.chondosha.bookclub.viewmodels.GroupViewModel
import com.chondosha.bookclub.viewmodels.GroupViewModelFactory
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import java.io.File
import java.util.*


@Composable
fun GroupScreen(
    groupId: String?,
    modifier: Modifier = Modifier,
    onNavigateToConversation: (conversationId: UUID) -> Unit,
    groupViewModel : GroupViewModel = viewModel(
        factory = GroupViewModelFactory(LocalGroupRepository.current, groupId)
    )
) {

    val navController = rememberNavController()

    val group by groupViewModel.group.collectAsState()
    val conversations by groupViewModel.conversations.collectAsState()
    val members by groupViewModel.members.collectAsState()

    val selectedItem = remember { mutableStateOf(0) }

    val imagePainter = rememberAsyncImagePainter(
        model = group?.picture,
        placeholder = painterResource(R.drawable.ic_launcher_background)  // todo find image
    )

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = imagePainter,
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                        //OptionsMenu()
                    }

                }
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    selected = selectedItem.value == 0,
                    onClick = { selectedItem.value = 0 },
                    icon = { },  //todo find icon
                    label = { Text(text = "Conversations") }
                )
                BottomNavigationItem(
                    selected = selectedItem.value == 1,
                    onClick = { selectedItem.value = 1 },
                    icon = {},
                    label = { Text(text = "Members") }
                )
            }
        }
    ) { innerPadding ->
        when (selectedItem.value) {
            0 -> ConversationList(
                conversations = conversations,
                modifier = Modifier.padding(innerPadding),
                onNavigateToConversation = onNavigateToConversation
            )
            1 -> MemberList(
                groupId = group?.id,
                members = members,
                modifier = Modifier.padding(innerPadding),
                groupViewModel = groupViewModel
            )
        }
    }
}

@Composable
fun ConversationList(
    conversations: List<Conversation>?,
    modifier: Modifier = Modifier,
    onNavigateToConversation: (conversationId: UUID) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        if (conversations != null) {
            if (conversations.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.empty_group_list_text),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(conversations) { conversation ->
                    ConversationCell(
                        conversation = conversation,
                        onClickCell = { onNavigateToConversation(conversation.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun MemberList(
    groupId: UUID?,
    members: List<User>?,
    modifier: Modifier = Modifier,
    groupViewModel: GroupViewModel
) {
    LazyColumn(
        modifier = modifier
    ) {
        if (members != null) {
            if (members.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.empty_group_list_text),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(members) { member ->
                    if (groupId != null) {
                        MemberCell(
                            groupId = groupId,
                            user = member,
                            groupViewModel = groupViewModel
                        )
                    }
                }
            }
        }
    }
}