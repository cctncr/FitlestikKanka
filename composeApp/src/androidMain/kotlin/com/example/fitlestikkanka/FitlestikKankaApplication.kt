package com.example.fitlestikkanka

import android.app.Application
import com.example.fitlestikkanka.core.di.appModule
import com.example.fitlestikkanka.core.di.authModule
import com.example.fitlestikkanka.core.di.chatModule
import com.example.fitlestikkanka.core.di.debtsModule
import com.example.fitlestikkanka.core.di.platformModule
import com.example.fitlestikkanka.core.di.tasksModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Android Application class.
 * Initializes Koin dependency injection with Android context.
 */
class FitlestikKankaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger(Level.ERROR)

            // Reference Android context
            androidContext(this@FitlestikKankaApplication)

            // Load modules
            modules(
                platformModule,
                appModule,
                authModule,
                chatModule,
                tasksModule,
                debtsModule
            )
        }
    }
}
