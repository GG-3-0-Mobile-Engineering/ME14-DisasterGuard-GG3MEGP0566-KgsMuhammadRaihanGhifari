package com.hann.disasterguard

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.hann.disasterguard.util.DarkMode

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val shouldNotify = preferences.getBoolean(applicationContext.getString(R.string.pref_key_theme), false)
        AppCompatDelegate.setDefaultNightMode(if (shouldNotify) { DarkMode.ON.value } else { DarkMode.OFF.value })

    }
}