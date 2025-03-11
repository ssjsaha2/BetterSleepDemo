package com.example.bettersleepdemo.features.play_music.presentation.fakes

import com.example.bettersleepdemo.features.play_music.domain.repository.SoundRepository

class FakeSoundRepository: SoundRepository {
    val savedList: ArrayList<Int> = arrayListOf()
    override suspend fun saveSound(listOfMedia: List<Int>) {
       savedList.addAll(listOfMedia)
    }

    override suspend fun getAllSavedSounds(): List<Int> {
        return savedList
    }

    override suspend fun deleteAllSounds() {
        savedList.clear()
    }
}