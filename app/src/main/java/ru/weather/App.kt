package ru.weather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@HiltAndroidApp
class App : Application() {
    private val appScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
    }
}