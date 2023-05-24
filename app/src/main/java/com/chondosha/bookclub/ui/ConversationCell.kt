package com.chondosha.bookclub.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Conversation
import java.io.File

@Composable
fun ConversationCell(
    conversation: Conversation,
    modifier: Modifier = Modifier,
    onClickCell: () -> Unit
) {

    val imagePainter = if (conversation.picture != null) {
        rememberAsyncImagePainter(
            model = conversation.picture,
            placeholder = painterResource(R.drawable.no_picture)  // todo find image
        )
    } else {
        painterResource(R.drawable.no_picture)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickCell() }
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
                text = stringResource(R.string.conversation_title, conversation.bookTitle),
            )
        }
    }

}