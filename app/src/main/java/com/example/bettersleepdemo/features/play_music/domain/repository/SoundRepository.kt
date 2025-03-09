package com.example.bettersleepdemo.features.play_music.domain.repository


interface SoundRepository {
    suspend fun saveSound(listOfMedia: List<Int>)
    suspend fun getAllSavedSounds(): List<Int>
    suspend fun deleteAllSounds()
}