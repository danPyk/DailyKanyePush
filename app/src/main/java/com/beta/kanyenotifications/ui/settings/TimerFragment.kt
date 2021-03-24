package com.beta.kanyenotifications.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.beta.kanyenotifications.R
import com.beta.kanyenotifications.databinding.FragmentTimerBinding
import com.google.android.material.snackbar.Snackbar


class TimerFragment : androidx.fragment.app.Fragment() {


    private lateinit var timerViewModel: TimerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        timerViewModel =
                ViewModelProvider(this).get(TimerViewModel::class.java)
        val binding: FragmentTimerBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_timer,
            container,
            false)

        val application = requireNotNull(this.activity).application
        val timerViewModelFactory = TimerViewModelFactory(application)

        val timerViewModel =
            ViewModelProvider(
                this, timerViewModelFactory
            ).get(TimerViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.timerViewModel = timerViewModel

        binding.timePicker.setIs24HourView(true)
        hideKeyboardInputInTimePicker(this.resources.configuration.orientation, binding.timePicker)

        binding.textview.text = timerViewModel.getTime()
        binding.btnDB.setOnClickListener{

            var settedTimeHour = binding.timePicker.hour.toString()
            var hourAffterCheck = timerViewModel.checkIfSingle(settedTimeHour)

            var settedTimeMinute = binding.timePicker.minute.toString()
            var minuteAffterCheck = timerViewModel.checkIfSingle(settedTimeMinute)

            var sumTime = hourAffterCheck+minuteAffterCheck
            binding.textview.text = getString(R.string.set_time,
                "$hourAffterCheck:$minuteAffterCheck")

            val fileName = "UserTimeSetting"

            timerViewModel.insertTimeToFile(fileName, sumTime)
        }


        return binding.root
    }

    //set pop up
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firstLaunch()
    }

    private fun hideKeyboardInputInTimePicker(orientation: Int, timePicker: TimePicker) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    ((timePicker.getChildAt(0) as LinearLayout).getChildAt(4) as LinearLayout).getChildAt(
                        0).visibility = View.GONE
                }
                else
                {
                    (((timePicker.getChildAt(0) as LinearLayout).getChildAt(2) as LinearLayout).getChildAt(
                        2) as LinearLayout).getChildAt(0).visibility = View.GONE
                }
            } catch (ex: Exception) {
            }

        }

    }

    //custom back behavior
    private fun fileExist(): Boolean? {
        return context?.getFileStreamPath("UserTimeSetting")?.exists()
    }

    //handle back btn after first launch
    override fun onAttach(context: Context) {
        super.onAttach(context)
        object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                Toast.makeText(context, "handleOnBackPressed", Toast.LENGTH_SHORT).show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, true) {
            val fileExist = fileExist()
            if (fileExist!!) {
                findNavController().navigate(R.id.nav_settings)
            } else {
                Toast.makeText(context, "You need to save time first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firstLaunch() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val previouslyStarted = prefs.getBoolean(getString(R.string.pref_first_see_timer), false)
        if (!previouslyStarted) {
            val edit = prefs.edit()
            edit.putBoolean(getString(R.string.pref_first_see_timer), java.lang.Boolean.TRUE)
            edit.apply()
            showPopUp()
        }
    }

    private fun showPopUp() {
        val coordinatorLayout =
            requireView().findViewById(R.id.coordinatorLayout) as CoordinatorLayout
        try {
            Snackbar.make(
                coordinatorLayout,
                R.string.snackbar_message,
                2500
            ).show()
        } catch (e: NullPointerException) {

        }
    }


}
