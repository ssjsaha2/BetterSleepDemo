package com.example.bettersleepdemo.features.play_music.domain.usecase

interface MediaPlayBackControllerUseCase {
    fun playMusic(id: Int)
    fun pauseMusic(id: Int)
    fun playAllMusic(mediaList: List<Int>)
    fun pauseAllMusic(mediaList: List<Int>)
    fun clearAllMusic()
    suspend fun saveMusic(listOfMedia: List<Int>)
    suspend fun getAllMusic(): List<Int>
    suspend fun deleteAllSounds()
    fun unbindService()
    fun bindService()
}