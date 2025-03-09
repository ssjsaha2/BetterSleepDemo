package com.example.bettersleepdemo.features.play_music.di

import com.example.bettersleepdemo.features.play_music.data.repository.SoundRepositoryImpl
import com.example.bettersleepdemo.features.play_music.data.usecase.MediaPlayBackControllerUseCaseImpl
import com.example.bettersleepdemo.features.play_music.domain.repository.SoundRepository
import com.example.bettersleepdemo.features.play_music.domain.usecase.MediaPlayBackControllerUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsecaseAndRepoModule {

    @Binds
    @Singleton
    abstract fun bindSoundRepository(soundRepositoryImpl: SoundRepositoryImpl): SoundRepository

    @Binds
    @Singleton
    abstract fun bindMediaPlackBackUseCase
                (mediaPlayBackControllerUseCaseImpl: MediaPlayBackControllerUseCaseImpl):
            MediaPlayBackControllerUseCase
}