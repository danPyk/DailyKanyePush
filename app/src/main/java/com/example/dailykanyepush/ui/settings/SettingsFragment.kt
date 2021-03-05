package com.example.dailykanyepush.ui.settings

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.dailykanyepush.R
import com.example.dailykanyepush.databinding.FragmentSettingsBinding


class SettingsFragment : androidx.fragment.app.Fragment() {


    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        settingsViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_settings,
            container,
            false)

        val application = requireNotNull(this.activity).application
        val settingsViewModelFactory = SettingsViewModelFactory(application)

        val settingsViewModel =
            ViewModelProvider(
                this, settingsViewModelFactory
            ).get(SettingsViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.settingsViewModel = settingsViewModel


        activity?.actionBar?.setLogo(R.drawable.ic_arrow_back_24px)

        binding.timePicker.setIs24HourView(true)
        hideKeyboardInputInTimePicker(this.resources.configuration.orientation, binding.timePicker)

        binding.btnDB.setOnClickListener{

            var settedTimeHour = binding.timePicker.hour.toString()
            var hourAffterCheck = settingsViewModel.checkIfSingle(settedTimeHour)

            var settedTimeMinute = binding.timePicker.minute.toString()
            var minuteAffterCheck = settingsViewModel.checkIfSingle(settedTimeMinute)

            var sumTime = hourAffterCheck+minuteAffterCheck
            //Todo: add pm/am
            binding.textview.text = getString(R.string.set_time,
                "$hourAffterCheck:$minuteAffterCheck")

            var fileName = "UserTimeSetting"

            settingsViewModel.insertTimeToFile(fileName, sumTime)
        }

        return binding.root
    }

    fun hideKeyboardInputInTimePicker(orientation: Int, timePicker: TimePicker)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            try
            {
                if (orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    ((timePicker.getChildAt(0) as LinearLayout).getChildAt(4) as LinearLayout).getChildAt(
                        0).visibility = View.GONE
                }
                else
                {
                    (((timePicker.getChildAt(0) as LinearLayout).getChildAt(2) as LinearLayout).getChildAt(
                        2) as LinearLayout).getChildAt(0).visibility = View.GONE
                }
            }
            catch (ex: Exception)
            {
            }

        }
    }

}
