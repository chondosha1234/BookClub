package com.chondosha.bookclub.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Message

@Composable
fun MessageCell(
    message: Message,
    modifier: Modifier = Modifier,
) {
   Column(
       modifier = modifier,
   ) {
       Text(
           text = stringResource(R.string.sender_name, message.sender)
       )

       Text(
           text = stringResource(R.string.message_text, message.text)
       )
   }
}