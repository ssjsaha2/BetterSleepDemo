package com.example.bettersleepdemo.features.play_music.data.repository

import android.content.SharedPreferences
import com.example.bettersleepdemo.features.play_music.domain.repository.SoundRepository

class SoundRepositoryImpl(private val sharedPref: SharedPreferences): SoundRepository {
    override suspend fun saveSound(resId: Int) {
        sharedPref.edit().putInt("",0)
    }

    override suspend fun getAllSavedSounds(): List<Int> {
        return listOf()
    }
}