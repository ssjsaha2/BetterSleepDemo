package com.example.bettersleepdemo.features.play_music.presentation

import androidx.lifecycle.ViewModel
import com.example.bettersleepdemo.features.play_music.domain.usecase.MediaPlayBackControllerUseCase
import com.example.bettersleepdemo.features.play_music.presentation.ui_event.MediaUIEvent
import com.example.bettersleepdemo.features.play_music.presentation.ui_state.MediaPlaybackUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MediaPlayViewModel @Inject constructor
    (
    private val useCase: MediaPlayBackControllerUseCase,
    private val mediaFileIds: List<Pair<Int, String>>
) :
    ViewModel() {

    private var _mediaUiState: MutableStateFlow<MediaPlaybackUiState> =
        MutableStateFlow(MediaPlaybackUiState(mediaButtonStateList = createInitialMediaToPlayerPair()))

    val mediaUiState = _mediaUiState.asStateFlow()

    init {
        useCase.bindService()
    }

    fun onEvent(mediaUIEvent: MediaUIEvent) {
        when (mediaUIEvent) {
            MediaUIEvent.ClearAllMusic -> {

            }

            MediaUIEvent.PauseAllMusic -> {
                useCase.pauseAllMusic()
            }

            is MediaUIEvent.PauseMusic -> {
                useCase.pauseMusic(mediaUIEvent.id)
                val mediaItem = _mediaUiState.value.mediaButtonStateList.find {
                    it.first.first == mediaUIEvent.id
                }
                val index = _mediaUiState.value.mediaButtonStateList.indexOf(mediaItem)
                val updatedMedia = mediaItem?.copy(
                    second = false
                )
                val mutableList = _mediaUiState.value.mediaButtonStateList.toMutableList()
                updatedMedia?.let {
                    mutableList[index] = updatedMedia
                    _mediaUiState.update {
                        _mediaUiState.value.copy(
                            mediaButtonStateList = mutableList.toList()
                        )
                    }
                }
            }

            MediaUIEvent.PlayAllMusic -> {
                useCase.playAllMusic()
            }

            is MediaUIEvent.PlayMusic -> {
                useCase.playMusic(mediaUIEvent.id)
                val mediaItem = _mediaUiState.value.mediaButtonStateList.find {
                    it.first.first == mediaUIEvent.id
                }
                val index = _mediaUiState.value.mediaButtonStateList.indexOf(mediaItem)
                val updatedMedia = mediaItem?.copy(
                    second = true
                )
                val mutableList = _mediaUiState.value.mediaButtonStateList.toMutableList()
                updatedMedia?.let {
                    mutableList[index] = updatedMedia
                    _mediaUiState.update {
                        _mediaUiState.value.copy(
                            mediaButtonStateList = mutableList.toList()
                        )
                    }
                }

            }
        }
    }

    private fun createInitialMediaToPlayerPair(): List<Pair<Pair<Int, String>, Boolean>> {
        val res: ArrayList<Pair<Pair<Int, String>, Boolean>> = ArrayList()
        for (i in mediaFileIds) {
            res.add(Pair(i, false))
        }
        return res.toList()
    }

    override fun onCleared() {
        super.onCleared()
        useCase.unbindService()
    }
}