package com.example.bettersleepdemo.features.play_music.presentation.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.example.bettersleepdemo.common.getMediaIcon
import com.example.bettersleepdemo.features.play_music.presentation.ui_event.MediaUIEvent

/// media grid item
@Composable
fun MediaItem(
    modifier: Modifier = Modifier,
    media: Pair<Pair<Int, String>, Boolean>,
    onUIEvent: (MediaUIEvent) -> Unit,
    rotation: Animatable<Float, AnimationVector1D>
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                getMediaIcon(
                    media.first.first,
                    media.second
                )
            ),
            contentDescription = "",
            modifier = Modifier
                .clickable {
                    if (!media.second) {
                        onUIEvent(MediaUIEvent.PlayMusic(media.first.first))
                    } else {
                        onUIEvent(MediaUIEvent.PauseMusic(media.first.first))
                    }
                }
                .graphicsLayer {
                    rotationZ = if (media.second) {
                        rotation.value
                    } else {
                        0f
                    }
                })
        Text(
            text = media.first.second,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}