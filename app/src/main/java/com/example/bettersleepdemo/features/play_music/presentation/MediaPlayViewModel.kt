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
        getInitialSetupMusic()
    }

    private fun getInitialSetupMusic(){
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

    private fun onTapClearButton(){
        onEvent(MediaUIEvent.PauseAllMusic)
        _mediaUiState.update {
            MediaPlaybackUiState(createInitialMediaToPlayerPair())
        }
        viewModelScope.launch {
            useCase.deleteAllSounds()
        }
    }

    private fun onTapPlayAllButton(){
        useCase.playAllMusic(_mediaUiState.value.currentlySelectedMedias.map { it.first })
        _mediaUiState.update {
            _mediaUiState.value.copy(
                isAllSelectedMusicPlaying = true
            )
        }
    }

    private fun onTapPauseAll(){
        useCase.pauseAllMusic(_mediaUiState.value.currentlySelectedMedias.map { it.first })
        _mediaUiState.update {
            _mediaUiState.value.copy(
                isAllSelectedMusicPlaying = false
            )
        }
    }

    private fun dismissWarningDialog(){
        _mediaUiState.update {
            _mediaUiState.value.copy(
                showWarning = false
            )
        }
    }

    private fun deleteFromCurrentlySelectedMedia(mediaItem: Pair<Pair<Int,String>,Boolean>?):
            MutableList<Pair<Int,String>>{
        val mutableCurrentMediaList =
            _mediaUiState.value.currentlySelectedMedias.toMutableList()
        mutableCurrentMediaList.remove(
            Pair(
                mediaItem?.first?.first,
                mediaItem?.first?.second
            )
        )
        return mutableCurrentMediaList
    }

    private fun saveMediaLocally(){
        viewModelScope.launch {
            useCase.saveMusic(
                _mediaUiState.value.currentlySelectedMedias
                    .map { it.first })
        }
    }

    fun onEvent(mediaUIEvent: MediaUIEvent) {
        when (mediaUIEvent) {
            MediaUIEvent.ClearAllMusic -> {
                onTapClearButton()
            }

            MediaUIEvent.PauseAllMusic -> {
                onTapPauseAll()
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
                val mutableButtonStateList = _mediaUiState.value.mediaButtonStateList.toMutableList()
                val updatedCurrentlySelectedList = deleteFromCurrentlySelectedMedia(mediaItem)
                updatedMedia?.let {
                    mutableButtonStateList[index] = updatedMedia
                    _mediaUiState.update {
                        _mediaUiState.value.copy(
                            mediaButtonStateList = mutableButtonStateList.toList(),
                            currentlySelectedMedias = updatedCurrentlySelectedList.toList()
                        )
                    }
                }
                onEvent(MediaUIEvent.PlayAllMusic)
            }

            MediaUIEvent.PlayAllMusic -> {
                onTapPlayAllButton()
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
                    saveMediaLocally()
                } else {
                    showWarningDialog()
                }

            }

            MediaUIEvent.DismissWarningDialog -> {
                dismissWarningDialog()
            }
        }
    }

    private fun showWarningDialog() {
        _mediaUiState.update {
            _mediaUiState.value.copy(
                showWarning = true
            )
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