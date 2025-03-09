package com.example.bettersleepdemo.features.play_music.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.bettersleepdemo.R
import com.example.bettersleepdemo.features.play_music.presentation.ui_event.MediaUIEvent
import com.example.bettersleepdemo.features.play_music.presentation.ui_state.MediaPlaybackUiState


//bottom layout composable (for playing and deleting medias)
@Composable
fun BoxScope.BottomLayout(
    uiState: MediaPlaybackUiState,
    onUIEvent: (MediaUIEvent) -> Unit
) {
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