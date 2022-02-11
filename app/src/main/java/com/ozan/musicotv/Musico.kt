package com.ozan.musicotv

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Musico : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}