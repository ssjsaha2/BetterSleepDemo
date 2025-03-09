package com.example.bettersleepdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BetterSleepApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}