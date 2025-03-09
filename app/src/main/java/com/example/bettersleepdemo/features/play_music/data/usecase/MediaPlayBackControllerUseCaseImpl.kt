package com.example.bettersleepdemo.features.play_music.data.usecase

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.bettersleepdemo.features.play_music.data.servie.MediaPlayBackService
import com.example.bettersleepdemo.features.play_music.domain.repository.SoundRepository
import com.example.bettersleepdemo.features.play_music.domain.usecase.MediaPlayBackControllerUseCase
import javax.inject.Inject

class MediaPlayBackControllerUseCaseImpl @Inject constructor(
    private var repo: SoundRepository,
    private val context: Context,
) : MediaPlayBackControllerUseCase {

    private var mediaService: MediaPlayBackService? = null

    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val musicBinder = binder as? MediaPlayBackService.MusicBinder
            mediaService = musicBinder?.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            mediaService = null
        }
    }

    override fun bindService() {
        val intent = Intent(context, MediaPlayBackService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun unbindService() {
        if (isBound) {
            context.unbindService(serviceConnection)
            isBound = false
        }
    }

    override fun playMusic(id: Int) {
        mediaService?.playMedia(id)
    }

    override fun pauseMusic(id: Int) {
        mediaService?.pauseMedia(id)
    }

    override fun playAllMusic(mediaList:List<Int>) {
        mediaService?.playAllMedia(mediaList)
    }

    override fun pauseAllMusic(mediaList:List<Int>) {
        mediaService?.pauseAllMedia(mediaList)
    }

    override fun clearAllMusic() {
        mediaService?.clearAllMedia()
    }

    override suspend fun saveMusic(listOfMedia: List<Int>) {
        repo.saveSound(listOfMedia)
    }

    override suspend fun getAllMusic(): List<Int> = repo.getAllSavedSounds()

    override suspend fun deleteAllSounds() {
        repo.deleteAllSounds()
    }
}