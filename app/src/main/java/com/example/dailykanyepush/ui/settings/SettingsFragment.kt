package com.example.dailykanyepush.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.dailykanyepush.R
import com.example.dailykanyepush.databinding.FragmentSettingsBinding

class SettingsFragment : androidx.fragment.app.Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        val application = requireNotNull(this.activity).application
        val settingsViewModelFactory = SettingsViewModelFactory(application)

        val settingsViewModel =
            ViewModelProvider(
                this, settingsViewModelFactory
            ).get(SettingsViewModel::class.java)
        binding.setLifecycleOwner(this)

        binding.settingsViewModel = settingsViewModel

        binding.btnDB.setOnClickListener{

            var settedTime = binding.timePicker1.hour.toString()
            //Todo: add pm/am
            binding.textview.text = getString(R.string.set_time, settedTime)
            var fileName = "UserTimeSetting"

            settingsViewModel.insert(fileName, settedTime)

        }

        return binding.root
    }
}