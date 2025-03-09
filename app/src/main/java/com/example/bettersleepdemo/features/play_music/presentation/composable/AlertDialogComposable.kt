package com.example.bettersleepdemo.features.play_music.presentation.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.bettersleepdemo.features.play_music.presentation.ui_event.MediaUIEvent


// alert dialog for exceeding max limit
@Composable
fun ShowAlertDialog(onUIEvent: (MediaUIEvent) -> Unit) {
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
