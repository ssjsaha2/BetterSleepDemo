package com.example.bettersleepdemo.features.play_music.presentation.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bettersleepdemo.features.play_music.presentation.ui_event.MediaUIEvent
import com.example.bettersleepdemo.features.play_music.presentation.ui_state.MediaPlaybackUiState


@Composable
fun SoundListComposable(
    modifier: Modifier = Modifier,
    uiState: MediaPlaybackUiState,
    onUIEvent: (MediaUIEvent) -> Unit
) {

    val mediaList = uiState.mediaButtonStateList
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        rotation.animateTo(
            targetValue = 10f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 2_000
                    0f at 0 using LinearEasing
                    10f at 0 using LinearEasing
                    -10f at 0 using LinearEasing
                },
                repeatMode = RepeatMode.Reverse
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.statusBars
            )
            .windowInsetsPadding(
                WindowInsets.navigationBars
            )
    ) {
        Background()
        Column(modifier = modifier.fillMaxSize()) {
            LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                itemsIndexed(mediaList) { index, media ->
                    val paddingTop = if (index % 2 == 0) {
                        16.dp
                    } else {
                        0.dp
                    }
                    MediaItem(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, paddingTop, 0.dp, 0.dp),
                        media = media,
                        rotation = rotation,
                        onUIEvent = onUIEvent
                    )
                }
            }
        }
        if (uiState.currentlySelectedMedias.isNotEmpty()) {
            BottomLayout(uiState, onUIEvent)
        }
        if (uiState.showWarning) {
            ShowAlertDialog(onUIEvent)
        }
    }
}