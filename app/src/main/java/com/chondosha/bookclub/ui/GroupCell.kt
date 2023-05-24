package com.chondosha.bookclub.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.chondosha.bookclub.R
import com.chondosha.bookclub.api.models.Group
import java.io.File


@Composable
fun GroupCell(
    group: Group,
    modifier: Modifier = Modifier,
    onClickCell: () -> Unit
) {

    val imagePainter = rememberAsyncImagePainter(
        model = group.picture?.let {
            File(LocalContext.current.filesDir, it).toUri()
        },
        placeholder = painterResource(R.drawable.ic_launcher_background)  // todo find image
    )

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
            modifier = Modifier.size(64.dp)
        )
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.group_name, group.name),
            )
        }

    }
}