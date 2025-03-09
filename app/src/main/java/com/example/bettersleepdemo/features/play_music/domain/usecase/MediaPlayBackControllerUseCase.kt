package com.example.bettersleepdemo.features.play_music.domain.usecase

interface MediaPlayBackControllerUseCase {
    fun playMusic(id: Int)
    fun pauseMusic(id: Int)
    fun playAllMusic()
    fun pauseAllMusic()
    fun clearAllMusic()
    suspend fun saveMusic(id: Int)
    suspend fun getAllMusic(): List<Int>
    fun unbindService()
    fun bindService()
}