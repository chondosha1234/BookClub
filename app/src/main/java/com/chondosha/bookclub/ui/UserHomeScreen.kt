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
import com.chondosha.bookclub.LocalUserRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import com.chondosha.bookclub.viewmodels.UserHomeViewModelFactory
import java.util.*

@Composable
fun UserHomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToGroup: (groupId: UUID) -> Unit
){
    val userHomeViewModel : UserHomeViewModel = viewModel(
        factory = UserHomeViewModelFactory(LocalUserRepository.current)
    )

    val user by userHomeViewModel.user.collectAsState()
    val groups = user?.groups

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
        },
        bottomBar = {}
    )
}

