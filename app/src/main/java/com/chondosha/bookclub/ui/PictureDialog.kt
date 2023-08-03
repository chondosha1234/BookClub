package com.chondosha.bookclub.ui

import android.graphics.Bitmap
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chondosha.bookclub.R

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
                        .align(Alignment.CenterHorizontally)
                )

                if (canChangePicture) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            cameraLauncher?.launch(null)
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.camera_button),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(24.dp)
                                .fillMaxWidth()
                        )
                    }
                }

            }
        }
    }
}