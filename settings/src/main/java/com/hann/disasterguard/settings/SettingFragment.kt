package com.hann.disasterguard.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.hann.disasterguard.util.DarkMode

class SettingFragment:  PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val switchPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_theme))
        switchPreference?.setOnPreferenceChangeListener{ _, newValue ->
            val checkValue = newValue as Boolean
            updateTheme(if (checkValue) DarkMode.ON.value else DarkMode.OFF.value)
            true
        }
    }

    private fun updateTheme(mode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(mode)
        requireActivity().recreate()
        return true
    }
}