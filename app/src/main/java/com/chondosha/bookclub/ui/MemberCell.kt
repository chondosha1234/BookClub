package com.chondosha.bookclub.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.chondosha.bookclub.viewmodels.GroupViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun MemberCell(
    user: User,
    groupId: UUID,
    groupViewModel: GroupViewModel,
    modifier: Modifier = Modifier,
) {

    var expanded by remember { mutableStateOf(false) }
    var deletePressed by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val imagePainter = if (user.picture != null) {
        rememberAsyncImagePainter(
            model = user.picture,
            placeholder = painterResource(R.drawable.no_picture)  // todo find image
        )
    } else {
        painterResource(R.drawable.no_picture)
    }

    val memberPictureBeingViewed = remember { mutableStateOf(false) }

    if (memberPictureBeingViewed.value) {
        PictureDialog(
            imagePainter = imagePainter,
            onDismissRequest = {
                memberPictureBeingViewed.value = false
            }
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
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
                .clickable { memberPictureBeingViewed.value = true }
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
                deletePressed = true
                expanded = false
            }) {
                Text(stringResource(R.string.remove_user))
            }
        }
    }

    if (deletePressed) {
        AlertDialog(
            onDismissRequest = { deletePressed = false },
            title = {
                Text(text = stringResource(R.string.remove_confirmation))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            groupViewModel.removeMember(groupId, user.id)
                        }
                        deletePressed = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.confirm)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { deletePressed = false }
                ) {
                    Text(
                        text = stringResource(R.string.deny)
                    )
                }
            }
        )
    }
}