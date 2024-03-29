package com.chondosha.bookclub.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.chondosha.bookclub.LocalConversationRepository
import com.chondosha.bookclub.LocalUserRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Message
import com.chondosha.bookclub.api.models.User
import com.chondosha.bookclub.viewmodels.ConversationViewModel
import com.chondosha.bookclub.viewmodels.ConversationViewModelFactory
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import com.chondosha.bookclub.viewmodels.UserHomeViewModelFactory

@Composable
fun ConversationScreen(
    conversationId: String?,
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    userHomeViewModel : UserHomeViewModel = viewModel(
        factory = UserHomeViewModelFactory(LocalUserRepository.current)
    ),
    conversationViewModel : ConversationViewModel = viewModel(
        factory = ConversationViewModelFactory(LocalConversationRepository.current, conversationId)
    )
) {
    val navController = rememberNavController()

    val user by conversationViewModel.user.collectAsState()
    val conversation by conversationViewModel.conversation.collectAsState()
    val messages by conversationViewModel.messages.collectAsState()

    val message = remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.header, conversation?.bookTitle ?: R.string.app_name)) },
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
        content = { padding ->
            Column(
                modifier = modifier.padding(padding)
            ) {
                MessageList(
                    user = user,
                    messages = messages,
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                )

                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = message.value,
                        onValueChange = { message.value = it },
                        modifier = Modifier
                            //.fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                    Button(
                        shape = CircleShape,
                        onClick = {
                            conversationViewModel.sendMessage(message.value)
                            message.value = ""
                        },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.message_send_button),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun MessageList(
    user: User?,
    messages: List<Message>?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        reverseLayout = true,
    ) {
        if (messages != null) {
            if (messages.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.empty_message_list_text),
                        textAlign = TextAlign.Center,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            } else {
                items(messages) { message ->
                    MessageCell(
                        user = user,
                        message = message,
                    )
                }
            }
        }
    }
}