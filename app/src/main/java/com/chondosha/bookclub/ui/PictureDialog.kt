package com.chondosha.bookclub.ui

import android.graphics.Bitmap
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PictureDialog(
    imagePainter: Painter,
    cameraLauncher: ManagedActivityResultLauncher<Void?, Bitmap?>?,
    canChangePicture: Boolean,
    onDismissRequest: () -> Unit,

    ) {

    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column {
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(256.dp)
                )

                if (canChangePicture) {
                    Button(
                        onClick = {
                            cameraLauncher?.launch(null)
                        }
                    ) {
                        Text(text = "Change")
                    }
                }

            }
        }
    }
}