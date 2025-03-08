package com.example.bettersleepdemo.features.play_music.domain.repository


interface SoundRepository {
    suspend fun saveSound(resId: Int)
    suspend fun getAllSavedSounds(): List<Int>
}