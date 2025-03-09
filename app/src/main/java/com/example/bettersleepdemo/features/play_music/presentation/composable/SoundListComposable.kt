package com.example.bettersleepdemo.features.play_music.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bettersleepdemo.features.play_music.presentation.ui_event.MediaUIEvent
import com.example.bettersleepdemo.features.play_music.presentation.ui_state.MediaPlaybackUiState

@Composable
fun SoundListComposable(
    modifier: Modifier = Modifier,
    uiState: MediaPlaybackUiState,
    onUIEvent: (MediaUIEvent) -> Unit
) {
    val mediaList = uiState.mediaButtonStateList
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.wrapContentSize()) {
            items(mediaList) { it ->
                Button(onClick = {
                    if (!it.second) {
                        onUIEvent(MediaUIEvent.PlayMusic(it.first.first))
                    } else {
                        onUIEvent(MediaUIEvent.PauseMusic(it.first.first))
                    }
                }) {
                    Text(text = it.first.second)
                }
            }
        }
        Button(onClick = {
            onUIEvent(MediaUIEvent.PlayAllMusic)
        }) {
            Text("Play all")
        }
        Button(onClick = {
            onUIEvent(MediaUIEvent.PauseAllMusic)
        }) {
            Text("Stop all")
        }
    }
}