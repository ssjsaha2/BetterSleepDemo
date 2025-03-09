package com.example.bettersleepdemo.features.play_music.data.servie

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.provider.MediaStore.Audio.Media
import androidx.annotation.IdRes

class MediaPlayBackService: Service() {
    private val audioToMediaPlayerMap: HashMap<Int,MediaPlayer> = HashMap()

    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun getService(): MediaPlayBackService = this@MediaPlayBackService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    fun playMedia(@IdRes id: Int){
        createPlayerIfNull(id)
        audioToMediaPlayerMap[id]?.start()
       /*if(audioToMediaPlayerMap[id] == null){
           val mediaPlayer = MediaPlayer.create(this,id)
           mediaPlayer.isLooping = true
           audioToMediaPlayerMap[id] = mediaPlayer
           //audioToMediaPlayerMap[id]?.prepare()
           audioToMediaPlayerMap[id]?.start()
       }else{
          // audioToMediaPlayerMap[id]?.prepare()
           audioToMediaPlayerMap[id]?.isLooping = true
           audioToMediaPlayerMap[id]?.start()
       }*/
    }

    fun pauseMedia(@IdRes id: Int){
        audioToMediaPlayerMap[id]?.pause()
    }

    fun playAllMedia(mediaList:List<Int>){
        for(id in mediaList){
            createPlayerIfNull(id)
            audioToMediaPlayerMap[id]?.start()
        }
    }

    fun pauseAllMedia(mediaList:List<Int>){
        for(id in mediaList){
            audioToMediaPlayerMap[id]?.pause()
        }
    }

    fun clearAllMedia(){
        //todoo
    }

    private fun createPlayerIfNull(id: Int){
        if(audioToMediaPlayerMap[id] == null){
            val mediaPlayer = MediaPlayer.create(this,id)
            mediaPlayer.isLooping = true
            audioToMediaPlayerMap[id] = mediaPlayer
        }
    }
}