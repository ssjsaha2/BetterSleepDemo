package com.example.bettersleepdemo.features.play_music.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettersleepdemo.features.play_music.domain.usecase.MediaPlayBackControllerUseCase
import com.example.bettersleepdemo.features.play_music.presentation.ui_event.MediaUIEvent
import com.example.bettersleepdemo.features.play_music.presentation.ui_state.MediaPlaybackUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaPlayViewModel @Inject constructor(
    private val useCase: MediaPlayBackControllerUseCase,
    private val mediaFileIdMap: HashMap<Int, String>
) :
    ViewModel() {

    private var _mediaUiState: MutableStateFlow<MediaPlaybackUiState> =
        MutableStateFlow(MediaPlaybackUiState(mediaButtonStateList = createInitialMediaToPlayerPair()))

    val mediaUiState = _mediaUiState.asStateFlow()

    init {
        useCase.bindService()
        viewModelScope.launch {
            val savedMedias = useCase.getAllMusic()
            _mediaUiState.update {
                _mediaUiState.value.copy(
                    isAllSelectedMusicPlaying = false,
                    currentlySelectedMedias = savedMedias.map { Pair(it,mediaFileIdMap[it]?:"") },
                    mediaButtonStateList = getInitialButtonStates(savedMedias)
                )
            }
        }
    }

    fun onEvent(mediaUIEvent: MediaUIEvent) {
        when (mediaUIEvent) {
            MediaUIEvent.ClearAllMusic -> {
                onEvent(MediaUIEvent.PauseAllMusic)
                _mediaUiState.update {
                    MediaPlaybackUiState(createInitialMediaToPlayerPair())
                }
                viewModelScope.launch {
                    useCase.deleteAllSounds()
                }
            }

            MediaUIEvent.PauseAllMusic -> {
                useCase.pauseAllMusic(_mediaUiState.value.currentlySelectedMedias.map { it.first })
                _mediaUiState.update {
                    _mediaUiState.value.copy(
                        isAllSelectedMusicPlaying = false
                    )
                }
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
                val mutableCurrentMediaList =
                    _mediaUiState.value.currentlySelectedMedias.toMutableList()
                mutableCurrentMediaList.remove(
                    Pair(
                        mediaItem?.first?.first,
                        mediaItem?.first?.second
                    )
                )
                updatedMedia?.let {
                    mutableList[index] = updatedMedia
                    _mediaUiState.update {
                        _mediaUiState.value.copy(
                            mediaButtonStateList = mutableList.toList(),
                            currentlySelectedMedias = mutableCurrentMediaList.toList()
                        )
                    }
                }
                onEvent(MediaUIEvent.PlayAllMusic)
            }

            MediaUIEvent.PlayAllMusic -> {
                useCase.playAllMusic(_mediaUiState.value.currentlySelectedMedias.map { it.first })
                _mediaUiState.update {
                    _mediaUiState.value.copy(
                        isAllSelectedMusicPlaying = true
                    )
                }
            }

            is MediaUIEvent.PlayMusic -> {
                val isPermissable = checkIfLimitExceeded(3)
                if (isPermissable) {
                    useCase.playMusic(mediaUIEvent.id)
                    val mediaItem = _mediaUiState.value.mediaButtonStateList.find {
                        it.first.first == mediaUIEvent.id
                    }
                    val index = _mediaUiState.value.mediaButtonStateList.indexOf(mediaItem)
                    val updatedMedia = mediaItem?.copy(
                        second = true
                    )
                    val mutableList = _mediaUiState.value.mediaButtonStateList.toMutableList()
                    val mutableCurrentMediaList =
                        _mediaUiState.value.currentlySelectedMedias.toMutableList()
                    mediaItem?.let {
                        mutableCurrentMediaList.add(Pair(it.first.first, it.first.second))
                    }
                    updatedMedia?.let {
                        mutableList[index] = updatedMedia
                        _mediaUiState.update {
                            _mediaUiState.value.copy(
                                mediaButtonStateList = mutableList.toList(),
                                currentlySelectedMedias = mutableCurrentMediaList.toList(),
                                isAllSelectedMusicPlaying = true
                            )
                        }
                        onEvent(MediaUIEvent.PlayAllMusic)
                    }
                    viewModelScope.launch {
                        useCase.saveMusic(
                            _mediaUiState.value.currentlySelectedMedias
                                .map { it.first })
                    }
                } else {
                    _mediaUiState.update {
                        _mediaUiState.value.copy(
                            showWarning = true
                        )
                    }
                }

            }

            MediaUIEvent.DismissWarningDialog -> {
                _mediaUiState.update {
                    _mediaUiState.value.copy(
                        showWarning = false
                    )
                }
            }
        }
    }

    private fun createInitialMediaToPlayerPair(): List<Pair<Pair<Int, String>, Boolean>> {
        val res: ArrayList<Pair<Pair<Int, String>, Boolean>> = ArrayList()
        for ((id, name) in mediaFileIdMap) {
            res.add(Pair(Pair(id, name), false))
        }
        return res.toList()
    }

    private fun getInitialButtonStates(mediaList:List<Int>): List<Pair<Pair<Int,String>, Boolean>>{
        val res: ArrayList<Pair<Pair<Int, String>, Boolean>> = ArrayList()
        for ((id, name) in mediaFileIdMap) {
            val isSelected = id in mediaList
            res.add(Pair(Pair(id, name), isSelected))
        }
        return res.toList()
    }

    private fun checkIfLimitExceeded(limit: Int) =
        _mediaUiState.value.currentlySelectedMedias.size < limit

    override fun onCleared() {
        super.onCleared()
        useCase.unbindService()
    }
}