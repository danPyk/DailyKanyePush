package com.example.dailykanyepush.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.dailykanyepush.R

class ChooseSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val preferenceFragment: Preference? = findPreference(getString(R.string.timer_key))

        preferenceFragment?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            view?.findNavController()?.navigate(R.id.nav_settings)
            true
        }

    }

}