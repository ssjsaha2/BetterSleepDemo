package com.example.bettersleepdemo.features.play_music.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.bettersleepdemo.common.mediaToNameMap
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
    fun provideStaticMediaFileResIds(): HashMap<Int,String> {
        return mediaToNameMap
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) : Context{
        return context
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences>{
        return context.dataStore
    }
}