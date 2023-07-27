package com.hann.disasterguard.util

import androidx.appcompat.app.AppCompatDelegate

enum class DarkMode(val value: Int) {

    ON(AppCompatDelegate.MODE_NIGHT_YES),
    OFF(AppCompatDelegate.MODE_NIGHT_NO)

}