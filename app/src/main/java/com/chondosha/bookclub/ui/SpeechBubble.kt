package com.chondosha.bookclub.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun SpeechBubble(
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.padding(4.dp)
    ) {
        Box(
            modifier = modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            content()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(10.dp)
                .clip(TriangleShape(direction = TriangleDirection.BottomEnd))
                .background(backgroundColor)
        )
    }
}

@Composable
fun TriangleShape(direction: TriangleDirection): Shape = GenericShape { size, _ ->
    moveTo(size.width, size.height)
    when (direction) {
        TriangleDirection.BottomEnd -> {
            lineTo(0f, size.height)
            lineTo(size.width, 0f)
        }
        TriangleDirection.BottomStart -> {
            lineTo(size.width, size.height)
            lineTo(0f, 0f)
        }
    }
}

enum class TriangleDirection {
    BottomEnd,
    BottomStart
}