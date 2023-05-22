package com.chondosha.bookclub.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.chondosha.bookclub.LocalConversationRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Message
import com.chondosha.bookclub.viewmodels.ConversationViewModel
import com.chondosha.bookclub.viewmodels.ConversationViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun ConversationScreen(
    conversationId: String?,
    modifier: Modifier = Modifier,
    conversationViewModel : ConversationViewModel = viewModel(
        factory = ConversationViewModelFactory(LocalConversationRepository.current, conversationId)
    )
) {

    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    val conversation by conversationViewModel.conversation.collectAsState()
    val messages by conversationViewModel.messages.collectAsState()

    val message = remember { mutableStateOf("") }

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
                    //OptionsMenu()
                }
            )
        },
        content = { padding ->
            Column(
                modifier = modifier.padding(padding)
            ) {
                MessageList(
                    messages = messages,
                    modifier = modifier
                )

                Row(
                    modifier = modifier
                ) {
                    TextField(
                        value = message.value,
                        onValueChange = { message.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                conversationViewModel.sendMessage(message.value)
                            }
                            message.value = ""
                        }
                    ) {
                        // add image for send
                    }
                }
            }
        }
    )
}

@Composable
fun MessageList(
    messages: List<Message>?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true,
    ) {
        if (messages != null) {
            if (messages.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.empty_group_list_text),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(messages) { message ->
                    MessageCell(
                        message = message,
                    )
                }
            }
        }
    }
}