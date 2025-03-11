package com.example.bettersleepdemo.features.play_music.presentation.fakes

import com.example.bettersleepdemo.features.play_music.domain.usecase.MediaPlayBackControllerUseCase

class FakeMediaControllerUseCase(private val repo: FakeSoundRepository): MediaPlayBackControllerUseCase {
    private val musicListWithState: HashMap<Int,Boolean> = HashMap()
    override fun playMusic(id: Int) {
        musicListWithState[id] = true
    }

    override fun pauseMusic(id: Int) {
        musicListWithState[id] = false
    }

    override fun playAllMusic(mediaList: List<Int>) {
        for(key in musicListWithState.keys){
            musicListWithState[key] = true
        }
    }

    override fun pauseAllMusic(mediaList: List<Int>) {
        for(key in musicListWithState.keys){
            musicListWithState[key] = false
        }
    }


    override suspend fun saveMusic(listOfMedia: List<Int>) {
        repo.saveSound(listOfMedia)
    }

    override suspend fun getAllMusic(): List<Int> {
        return repo.getAllSavedSounds()
    }

    override suspend fun deleteAllSounds() {
        repo.deleteAllSounds()
    }

    override fun unbindService() {
        //avoid service functions for simplicity and time constraints
    }

    override fun bindService() {
        //avoid service functions for simplicity and time constraints

    }
}