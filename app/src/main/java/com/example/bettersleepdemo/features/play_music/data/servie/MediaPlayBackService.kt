package com.example.bettersleepdemo.features.play_music.data.servie

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.annotation.IdRes

class MediaPlayBackService: Service() {
    private val audioToMediaPlayerMap: HashMap<Int,MediaPlayer> = HashMap()

    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun getService(): MediaPlayBackService = this@MediaPlayBackService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    fun playMedia(@IdRes id: Int){
       if(audioToMediaPlayerMap[id] == null){
           val mediaPlayer = MediaPlayer.create(this,id)
           audioToMediaPlayerMap[id] = mediaPlayer
           //audioToMediaPlayerMap[id]?.prepare()
           audioToMediaPlayerMap[id]?.start()
       }else{
          // audioToMediaPlayerMap[id]?.prepare()
           audioToMediaPlayerMap[id]?.start()
       }
    }

    fun pauseMedia(@IdRes id: Int){
        audioToMediaPlayerMap[id]?.pause()
    }

    fun playAllMedia(){
        for((_,player) in audioToMediaPlayerMap){
            player.start()
        }
    }

    fun pauseAllMedia(){
        for((_,player) in audioToMediaPlayerMap){
            player.pause()
        }
    }

    fun clearAllMedia(){
        //todoo
    }
}