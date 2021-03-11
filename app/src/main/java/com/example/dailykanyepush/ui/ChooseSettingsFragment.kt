package com.example.dailykanyepush.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.dailykanyepush.R

class ChooseSettingsFragment() : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.choose_settings_preferences, rootKey)

        val preferenceFragment: Preference? = findPreference(getString(R.string.timer_key))
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        preferenceFragment?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            view?.findNavController()?.navigate(R.id.nav_settings)

            true
        }
    }

    fun getPreference(): Boolean? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val highScore = sharedPref?.getBoolean(getString(R.string.switch_key), false)
        var sum = highScore
        return sum

    }

    fun savePreference(key: Int, value: Boolean?) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(getString(key), value!!)
            apply()
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "switch" -> {
                var prefs = getPreference()
                prefs = prefs?.not()

                savePreference(R.string.switch_key, prefs)
            }
        }
    }


}
