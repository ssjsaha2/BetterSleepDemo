package com.example.bettersleepdemo.features.play_music.data.repository

import android.content.SharedPreferences
import com.example.bettersleepdemo.features.play_music.domain.repository.SoundRepository
import javax.inject.Inject
import androidx.core.content.edit

class SoundRepositoryImpl @Inject constructor(private val sharedPref: SharedPreferences): SoundRepository {
    override suspend fun saveSound(resId: Int) {
        sharedPref.edit() { putInt("", 0) }
    }

    override suspend fun getAllSavedSounds(): List<Int> {
        return listOf()
    }
}