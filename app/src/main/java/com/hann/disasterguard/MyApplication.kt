package com.hann.disasterguard

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.hann.disasterguard.coreapp.di.databaseModule
import com.hann.disasterguard.coreapp.di.networkModule
import com.hann.disasterguard.coreapp.di.repositoryModule
import com.hann.disasterguard.coreapp.di.useCaseModule
import com.hann.disasterguard.util.DarkMode
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule
                )
            )
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val shouldNotify = preferences.getBoolean(applicationContext.getString(R.string.pref_key_theme), false)
        AppCompatDelegate.setDefaultNightMode(if (shouldNotify) { DarkMode.ON.value } else { DarkMode.OFF.value })

    }
}