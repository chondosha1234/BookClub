package com.chondosha.bookclub.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.chondosha.bookclub.BitmapUtils.toBase64
import com.chondosha.bookclub.LocalConversationRepository
import com.chondosha.bookclub.LocalGroupRepository
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Conversation
import com.chondosha.bookclub.viewmodels.ConversationViewModel
import com.chondosha.bookclub.viewmodels.ConversationViewModelFactory
import com.chondosha.bookclub.viewmodels.GroupViewModel
import com.chondosha.bookclub.viewmodels.GroupViewModelFactory

@Composable
fun ConversationCell(
    conversation: Conversation,
    modifier: Modifier = Modifier,
    onClickCell: () -> Unit
) {

    val conversationId = conversation.id.toString()
    val conversationViewModel : ConversationViewModel = viewModel(
        factory = ConversationViewModelFactory(LocalConversationRepository.current, conversationId)
    )

    val imagePainter = if (conversation.picture != null) {
        rememberAsyncImagePainter(
            model = conversation.picture,
            placeholder = painterResource(R.drawable.no_picture)  // todo find image
        )
    } else {
        painterResource(R.drawable.no_picture)
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        val base64Image = bitmap.toBase64()
        conversationViewModel.setConversationPicture(conversation.id, base64Image)
    }

    val conversationPictureBeingViewed = remember { mutableStateOf(false) }

    if (conversationPictureBeingViewed.value) {
        PictureDialog(
            imagePainter = imagePainter,
            cameraLauncher = cameraLauncher,
            canChangePicture = true
        ) {
            conversationPictureBeingViewed.value = false
        }
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
                .clickable { conversationPictureBeingViewed.value = true }
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