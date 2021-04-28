package com.beta.kanyenotifications.ui.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.*
import androidx.navigation.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.beta.kanyenotifications.R
class Settings : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        val preferenceFragment: androidx.preference.Preference? = findPreference(getString(R.string.timer_key))
        val preferenceFragment2: androidx.preference.Preference? = findPreference(getString(R.string.details_key))

        val sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)


        preferenceFragment?.onPreferenceClickListener = androidx.preference.Preference.OnPreferenceClickListener {
            view?.findNavController()?.navigate(R.id.action_nav_settings_to_nav_timer)

            true
        }
        preferenceFragment2?.onPreferenceClickListener = androidx.preference.Preference.OnPreferenceClickListener {
            notificationDetails()
            true
        }

    }

    private fun getPreference(): Boolean? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        return sharedPref?.getBoolean(getString(R.string.switch_key), false)

    }
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
        /*    "sound" -> {

                var prefs = getPreference()
                prefs = prefs?.not()

                savePreference(R.string.sound_key, prefs)
            }
*/
        }
    }
    //notofications details
    private fun notificationDetails(){
        val intent =
            Intent(ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                putExtra(EXTRA_APP_PACKAGE, context?.packageName)
                putExtra(EXTRA_CHANNEL_ID,getString( R.string.notification_channel_id)) }
        startActivity(intent)

    }

}
