package com.example.plateful

import android.app.Application
import com.example.plateful.data.AppContainer
import com.example.plateful.data.DefaultAppContainer

class PlatefulApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}