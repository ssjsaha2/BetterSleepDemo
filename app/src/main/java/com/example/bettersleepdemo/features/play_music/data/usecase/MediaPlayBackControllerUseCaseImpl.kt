package com.example.bettersleepdemo.features.play_music.data.usecase

import android.content.Context
import android.media.MediaPlayer
import com.example.bettersleepdemo.features.play_music.data.servie.MediaPlayBackService
import com.example.bettersleepdemo.features.play_music.domain.repository.SoundRepository
import com.example.bettersleepdemo.features.play_music.domain.usecase.MediaPlayBackControllerUseCase

class MediaPlayBackControllerUseCaseImpl(
    private var repo: SoundRepository,
    private val context: Context,
) : MediaPlayBackControllerUseCase {

    private val mediaService: MediaPlayBackService? = null
    override fun playMusic(id: Int) {
        mediaService?.playMedia(id)
    }

    override fun pauseMusic(id: Int) {
        mediaService?.pauseMedia(id)

    }

    override fun playAllMusic() {
        mediaService?.playAllMedia()
    }

    override fun pauseAllMusic() {
        mediaService?.pauseAllMedia()
    }

    override fun clearAllMusic() {
        mediaService?.clearAllMedia()
    }

    override suspend fun saveMusic(id: Int) {
        repo.saveSound(id)
    }

    override suspend fun getAllMusic(): List<Int> = repo.getAllSavedSounds()
}