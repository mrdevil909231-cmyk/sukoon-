package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun DoubleTapLikeArea(
    onDoubleTap: () -> Unit,
    onSingleTap: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var showHeart by remember { mutableStateOf(false) }
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(showHeart) {
        if (showHeart) {
            scale.snapTo(0f)
            alpha.snapTo(1f)
            scale.animateTo(
                targetValue = 1.2f,
                animationSpec = keyframes {
                    durationMillis = 600
                    0f at 0 using FastOutSlowInEasing
                    1.3f at 250 using FastOutSlowInEasing
                    1.0f at 400 using FastOutSlowInEasing
                    0.9f at 600
                }
            )
            alpha.animateTo(0f)
            showHeart = false
        }
    }

    Box(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = {
                    showHeart = true
                    onDoubleTap()
                },
                onTap = {
                    onSingleTap()
                }
            )
        },
        contentAlignment = Alignment.Center
    ) {
        content()

        if (showHeart || alpha.value > 0f) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Liked",
                tint = Color.White.copy(alpha = alpha.value),
                modifier = Modifier
                    .size(110.dp)
                    .scale(scale.value)
            )
        }
    }
}
