package com.example.plateful

import android.app.Application
import com.example.plateful.data.AppContainer
import com.example.plateful.data.DefaultAppContainer

/**
 * The main application class for the Plateful application.
 *
 * This class is instantiated when the application starts and provides a centralpoint
 * for initializing application-wide components, such as dependency injection, database setup,
 * or network configuration.
 */
class PlatefulApplication : Application() {
    lateinit var container: AppContainer

    /**
     * Called when the application is starting.
     *
     * This method is called before any other application components, such as activities or services,
     * are created. It's the ideal place to initialize application-wide components and resources.
     */
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}
