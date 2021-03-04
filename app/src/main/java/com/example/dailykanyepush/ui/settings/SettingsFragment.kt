package com.example.dailykanyepush.ui.settings

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.appcompat.widget.Toolbar
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


        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)

  /*      toolbar.setNavigationOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment)
            navController.navigateUp()

        }
*/

        binding.timePicker.setIs24HourView(true)
        //format timepicker to 24h
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

            settingsViewModel.insert(fileName, sumTime)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


       // activity?.actionBar?.setLogo(R.drawable.kanye2)


        super.onViewCreated(view, savedInstanceState)
    }
/*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

                onBackPressed()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(): Boolean {
return true    }*/

}
