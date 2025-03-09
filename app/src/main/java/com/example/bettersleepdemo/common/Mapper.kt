package com.example.bettersleepdemo.common

import com.example.bettersleepdemo.R

fun getMediaIcon(id: Int,isSelected: Boolean): Int{
    when(id){
        R.raw.birds -> {
            return if(isSelected){
                R.drawable.sound_birds_selected
            }else{
                R.drawable.sound_birds_normal
            }
        }
        R.raw.flute -> {
            return if(isSelected){
                R.drawable.sound_flute_selected
            }else{
                R.drawable.sound_flute_normal
            }

        }
        R.raw.lounge -> {
            return if(isSelected){
                R.drawable.sound_lounge_selected
            }else{
                R.drawable.sound_lounge_normal
            }

        }
        R.raw.musicbox ->{
            return if(isSelected){
                R.drawable.sound_musicbox_selected
            }else{
                R.drawable.sound_musicbox_normal
            }

        }
        R.raw.ocean -> {
            return if(isSelected){
                R.drawable.sound_ocean_selected
            }else{
                R.drawable.sound_ocean_normal
            }

        }
        R.raw.orchestral ->{
            return if(isSelected){
                R.drawable.sound_orchestral_selected
            }else{
                R.drawable.sound_orchestral_normal
            }

        }
        R.raw.piano -> {
            return if(isSelected){
                R.drawable.sound_piano_selected
            }else{
                R.drawable.sound_piano_normal
            }

        }
        R.raw.rain -> {
            return if(isSelected){
                R.drawable.sound_rain_selected
            }else{
                R.drawable.sound_rain_normal
            }

        }
        R.raw.wind -> {
            return if(isSelected){
                R.drawable.sound_wind_selected
            }else{
                R.drawable.sound_wind_normal
            }

        }
        else -> {
            return R.drawable.sound_rain_normal
        }
    }
}
val mediaToNameMap: HashMap<Int,String> =
    hashMapOf(
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
