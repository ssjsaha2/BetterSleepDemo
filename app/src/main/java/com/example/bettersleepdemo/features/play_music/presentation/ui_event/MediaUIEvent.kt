package com.example.bettersleepdemo.features.play_music.presentation.ui_event

import android.provider.MediaStore.Audio.Media
import androidx.annotation.IdRes

sealed class MediaUIEvent {
    data class PlayMusic(@IdRes val id: Int): MediaUIEvent()
    data class PauseMusic(@IdRes val id: Int): MediaUIEvent()
    data object PlayAllMusic: MediaUIEvent()
    data object PauseAllMusic: MediaUIEvent()
    data object ClearAllMusic: MediaUIEvent()
}