package com.chondosha.bookclub.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.User
import com.chondosha.bookclub.viewmodels.UserHomeViewModel
import kotlinx.coroutines.launch

@Composable
fun AddFriendCell(
    user: User,
    userHomeViewModel: UserHomeViewModel,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {

    var expanded by remember { mutableStateOf(false) }
    var addPressed by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val imagePainter = if (user.picture != null) {
        rememberAsyncImagePainter(
            model = user.picture,
            placeholder = painterResource(R.drawable.no_picture)
        )
    } else {
        painterResource(R.drawable.no_picture)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier
                .padding(4.dp)
                .size(24.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.group_name, user.username),
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                addPressed = true
                expanded = false
            }) {
                Text(stringResource(R.string.add_friend))
            }
        }
    }

    if (addPressed) {
        AlertDialog(
            onDismissRequest = { addPressed = false },
            title = {
                Text(text = stringResource(R.string.add_friend_confirmation))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            userHomeViewModel.addFriend(user.id)
                            onNavigateToHome()
                        }
                        addPressed = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.confirm)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { addPressed = false }
                ) {
                    Text(
                        text = stringResource(R.string.deny)
                    )
                }
            }
        )
    }
}