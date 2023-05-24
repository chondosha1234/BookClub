package com.chondosha.bookclub.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.chondosha.bookclub.LocalGroupRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.viewmodels.GroupViewModel
import com.chondosha.bookclub.viewmodels.GroupViewModelFactory
import java.util.*

@Composable
fun CreateConversationScreen(
    groupIdString: String?,
    onNavigateToGroup: (groupId: UUID) -> Unit,
    modifier: Modifier = Modifier,
    groupViewModel : GroupViewModel = viewModel(
        factory = GroupViewModelFactory(LocalGroupRepository.current, groupIdString)
    )
) {
    val navController = rememberNavController()
    val groupId = UUID.fromString(groupIdString)

    val bookTitle = remember { mutableStateOf("") }

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
                    //OptionsMenu(userHomeViewModel)
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier.padding(innerPadding)
            ) {
                TextField(
                    value = bookTitle.value,
                    onValueChange = {
                        bookTitle.value = it
                        //createAttempted.value = false
                    },
                    label = { Text(text = stringResource(R.string.book_title_label)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Button(
                    onClick = {
                        groupViewModel.createConversation(bookTitle.value, groupId)
                        onNavigateToGroup(groupId)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(end = 16.dp)
                ) {
                    Text(text = stringResource(R.string.create_conversation))
                }
            }
        }
    )
}
