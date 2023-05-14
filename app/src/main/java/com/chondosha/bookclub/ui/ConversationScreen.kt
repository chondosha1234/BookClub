package com.chondosha.bookclub.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chondosha.bookclub.LocalConversationRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Message
import com.chondosha.bookclub.viewmodels.ConversationViewModel
import com.chondosha.bookclub.viewmodels.ConversationViewModelFactory

@Composable
fun ConversationScreen(
    conversationId: String?,
    modifier: Modifier = Modifier,
) {

    val conversationViewModel : ConversationViewModel = viewModel(
        factory = ConversationViewModelFactory(LocalConversationRepository.current, conversationId)
    )

    val conversation by conversationViewModel.conversation.collectAsState()
    val messages by conversationViewModel.messages.collectAsState()
    
}

@Composable
fun MessageList(
    messages: List<Message>?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
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