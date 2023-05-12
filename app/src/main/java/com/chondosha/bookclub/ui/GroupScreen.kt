package com.chondosha.bookclub.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chondosha.bookclub.LocalGroupRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.viewmodels.GroupViewModel
import com.chondosha.bookclub.viewmodels.GroupViewModelFactory


@Composable
fun GroupScreen(
    modifier: Modifier = Modifier,
    groupId: String?
) {

    val groupViewModel : GroupViewModel = viewModel(
        factory = GroupViewModelFactory(LocalGroupRepository.current, groupId)
    )

    val group by groupViewModel.group.collectAsState()
    val conversations = group?.conversations

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = modifier.padding(padding)
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
                                onClickCell = {}
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {}
    )
}