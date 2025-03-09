package com.example.bettersleepdemo.features.play_music.presentation.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.bettersleepdemo.R
import com.example.bettersleepdemo.common.getMediaIcon

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

    // Start rotating continuously when the Composable is first composed
    LaunchedEffect(Unit) {
        rotation.animateTo(
            targetValue = 10f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 2_000
                    0f at 0 with LinearEasing
                    10f at 0 with LinearEasing
                    -10f at 0 with LinearEasing
                },
                repeatMode = RepeatMode.Reverse
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(
                WindowInsets.navigationBars
            )
    ) {
        Image(
            modifier = Modifier.align(Alignment.BottomCenter),
            painter = painterResource(R.drawable.bg_lake),
            contentDescription = ""
        )
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxWidth(),
            painter = painterResource(R.drawable.bg_main),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            painter = painterResource(R.drawable.bg_main),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = modifier.fillMaxSize()) {
            LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                itemsIndexed(mediaList) { index, media ->
                    val paddingTop = if (index % 2 == 0) {
                        16.dp
                    } else {
                        0.dp
                    }
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, paddingTop, 0.dp, 0.dp),
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
            }
        }
        if (uiState.currentlySelectedMedias.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .wrapContentHeight()
                    .align(Alignment.BottomCenter)
                    .zIndex(1f)
                    .background(MaterialTheme.colorScheme.onTertiary),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = uiState.currentlySelectedMedias.joinToString(" , ") { it.second }
                        )
                    }
                    Image(
                        modifier = Modifier.clickable {
                            if (uiState.isAllSelectedMusicPlaying) {
                                onUIEvent(MediaUIEvent.PauseAllMusic)
                            } else {
                                onUIEvent(MediaUIEvent.PlayAllMusic)
                            }
                        },
                        painter = if (uiState.isAllSelectedMusicPlaying) {
                            painterResource(R.drawable.button_pause)
                        } else {
                            painterResource(R.drawable.button_play)
                        },
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Image(
                        painter = painterResource(R.drawable.button_clear),
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            onUIEvent(MediaUIEvent.ClearAllMusic)
                        }
                    )
                }
            }

        }
        if (uiState.showWarning) {
            AlertDialog(
                onDismissRequest = { onUIEvent(MediaUIEvent.DismissWarningDialog) },
                title = { Text(text = "Selection Limit") },
                text = { Text(text = "You can select max 3 medias at a time") },
                confirmButton = {
                    TextButton(onClick = { onUIEvent(MediaUIEvent.DismissWarningDialog) }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}