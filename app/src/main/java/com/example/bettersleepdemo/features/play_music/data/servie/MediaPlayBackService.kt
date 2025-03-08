package com.example.bettersleepdemo.features.play_music.data.servie

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.annotation.IdRes

class MediaPlayBackService: Service() {
    private val audioToMediaPlayerMap: HashMap<Int,MediaPlayer> = HashMap()
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    fun playMedia(@IdRes id: Int){
        ///todo
    }

    fun pauseMedia(@IdRes id: Int){
        //todo
    }

    fun playAllMedia(){
        //todo
    }

    fun pauseAllMedia(){
        //todo
    }

    fun clearAllMedia(){
        //todo
    }
}