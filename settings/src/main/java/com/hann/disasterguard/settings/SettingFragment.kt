package com.hann.disasterguard.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.hann.disasterguard.util.DarkMode

class SettingFragment:  PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val theme =findPreference<ListPreference>(getString(R.string.pref_key_dark))
        theme?.setOnPreferenceChangeListener{ _,newValue ->
            when(newValue){
                "auto" -> updateTheme(DarkMode.FOLLOW_SYSTEM.value)
                "on" -> updateTheme(DarkMode.ON.value)
                "off" -> updateTheme(DarkMode.OFF.value)
            }
            true
        }

    }

    private fun updateTheme(mode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(mode)
        requireActivity().recreate()
        return true
    }
}