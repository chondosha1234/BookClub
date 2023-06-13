package com.chondosha.bookclub.ui


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Message
import com.chondosha.bookclub.api.models.User

@Composable
fun MessageCell(
    user: User?,
    message: Message,
    modifier: Modifier = Modifier,
) {
    val isCurrentUser = user?.username == message.sender_username
    Log.d("arrangement", "value of isCurrentUser: $isCurrentUser")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        SpeechBubble(
            backgroundColor = Color.DarkGray,
            contentColor = Color.Black,
            modifier = Modifier.padding(4.dp),
            side = if (isCurrentUser) Alignment.BottomEnd else Alignment.BottomStart,
            direction = if (isCurrentUser) TriangleDirection.BottomEnd else TriangleDirection.BottomStart
        ) {
            Column(
                modifier = Modifier,
            ) {
                if (!isCurrentUser) {
                    Text(
                        text = stringResource(R.string.sender_name, message.sender_username)
                    )
                }
                Text(
                    text = stringResource(R.string.message_text, message.text)
                )
            }
        }
    }
}