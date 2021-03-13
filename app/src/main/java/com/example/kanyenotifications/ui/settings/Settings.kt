package com.example.kanyenotifications.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.kanyenotifications.R

class Settings : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        val preferenceFragment: androidx.preference.Preference? = findPreference(getString(R.string.timer_key))
       // val preferenceFragment2: androidx.preference.Preference? = findPreference(getString(R.string.switch_key))
        val switch: SwitchPreferenceCompat? = findPreference(getString(R.string.switch_key))

        //todo understand it better
        val sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        switch?.setSwitchTextOff(R.string.snackbar_message)
        switch?.setSwitchTextOn(R.string.enable_header)


        preferenceFragment?.onPreferenceClickListener = androidx.preference.Preference.OnPreferenceClickListener {
            view?.findNavController()?.navigate(R.id.nav_timer)

            true
        }
 /*       preferenceFragment2?.onPreferenceClickListener = androidx.preference.Preference.OnPreferenceClickListener {
            switch?.setSwitchTextOff(R.string.disable_header)
            switch?.setSwitchTextOn(R.string.enable_header)

            true
        }*/

    }

    private fun getPreference(): Boolean? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        return sharedPref?.getBoolean(getString(R.string.switch_key), true)

    }
    @SuppressWarnings("SameParameterValue")
    private fun savePreference(key: Int, value: Boolean?) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(getString(key), value!!)
            apply()
        }
    }

   //called when switch is switched
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
