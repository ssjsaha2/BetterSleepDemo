package com.example.bettersleepdemo.features.play_music.presentation.ui_state

data class MediaPlaybackUiState(
    val mediaButtonStateList: List<Pair<Pair<Int,String>,Boolean>> = listOf(),
    val isAnyMediaBeingPlayed: Boolean = false
)