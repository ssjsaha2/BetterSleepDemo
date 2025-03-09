package com.example.bettersleepdemo.features.play_music.di

import android.content.Context
import android.content.SharedPreferences
import com.example.bettersleepdemo.R
import com.example.bettersleepdemo.features.play_music.data.repository.SoundRepositoryImpl
import com.example.bettersleepdemo.features.play_music.data.usecase.MediaPlayBackControllerUseCaseImpl
import com.example.bettersleepdemo.features.play_music.domain.repository.SoundRepository
import com.example.bettersleepdemo.features.play_music.domain.usecase.MediaPlayBackControllerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideStaticMediaFileResIds(): List<Pair<Int, String>> {
        return listOf(
            Pair(R.raw.birds, "Birds"),
            Pair(R.raw.flute, "Flute"),
            Pair(R.raw.lounge, "Lounge"),
            Pair(R.raw.musicbox, "Music Box"),
            Pair(R.raw.ocean, "Ocean"),
            Pair(R.raw.orchestral, "Orchestral"),
            Pair(R.raw.piano, "Piano"),
            Pair(R.raw.rain, "Rain"),
            Pair(R.raw.wind, "Wind")
        )
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) : Context{
        return context
    }
}