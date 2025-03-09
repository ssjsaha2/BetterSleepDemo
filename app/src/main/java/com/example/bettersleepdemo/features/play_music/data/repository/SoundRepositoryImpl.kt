package com.example.bettersleepdemo.features.play_music.data.repository

import android.content.SharedPreferences
import androidx.collection.intListOf
import com.example.bettersleepdemo.features.play_music.domain.repository.SoundRepository
import javax.inject.Inject
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

class SoundRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>): SoundRepository {
    private val KEY_INT_LIST = stringPreferencesKey("media_id_list")

    override suspend fun saveSound(listOfMedia: List<Int>) {
        val json = Gson().toJson(listOfMedia)
        dataStore.edit { preferences ->
            preferences[KEY_INT_LIST] = json
        }
    }

    override suspend fun getAllSavedSounds(): List<Int> {
        val preferences = dataStore.data.first()
        val json = preferences[KEY_INT_LIST]
        return json?.let { Gson().fromJson(it, object : TypeToken<List<Int>>() {}.type) } ?: emptyList()
    }

    override suspend fun deleteAllSounds() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_INT_LIST)
        }
    }
}