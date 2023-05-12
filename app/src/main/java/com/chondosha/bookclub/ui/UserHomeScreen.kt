package com.chondosha.bookclub.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chondosha.bookclub.LocalUserRepository
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import com.chondosha.bookclub.viewmodels.UserHomeViewModelFactory

@Composable
fun UserHomeScreen(
    modifier: Modifier = Modifier,
    userId: String?
){
    val userHomeViewModel : UserHomeViewModel = viewModel(
        factory = UserHomeViewModelFactory(LocalUserRepository.current, userId)
    )

    val user by userHomeViewModel.user.collectAsState()
    val groups = user?.groups

    Scaffold(
        modifier = modifier,
        topBar = {

        },
        content = { padding ->
            LazyColumn(
                modifier = modifier.padding(padding)
            ) {
                if (groups != null) {
                    if (groups.isEmpty()) {
                        item {
                            Text(
                                text = "Not part of any groups yet",
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        items(groups) { group ->
                            GroupCell(
                                group = group,
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

