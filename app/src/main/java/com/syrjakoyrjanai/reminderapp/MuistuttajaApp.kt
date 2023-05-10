package com.syrjakoyrjanai.reminderapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MuistuttajaApp : Application() {
    override fun onCreate()  {
        super.onCreate()
        Graph.provide(this)
    }

}