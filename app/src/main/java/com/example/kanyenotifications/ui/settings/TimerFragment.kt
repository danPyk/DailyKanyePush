package com.example.kanyenotifications.ui.settings

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
import com.example.kanyenotifications.R
import com.example.kanyenotifications.databinding.FragmentTimerBinding


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

    private fun hideKeyboardInputInTimePicker(orientation: Int, timePicker: TimePicker)
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
