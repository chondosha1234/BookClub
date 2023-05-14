package com.chondosha.bookclub.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
           text = "sender name"
       )

       Text(
           text = "message content"
       )
   }
}