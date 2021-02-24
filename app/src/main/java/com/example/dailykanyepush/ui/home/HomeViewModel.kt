package com.example.dailykanyepush.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.launch

const val TAG = "HomeViewModel"

 class HomeViewModel(val database: SleepDatabaseDao, application: Application) :
     AndroidViewModel(application) {

    val nights = database.getAllNights()
    //to set content of textView
     val nightsString = Transformations.map(nights) { nights ->
         formatNights(nights, application.resources)
     }

     //private var tonight = MutableLiveData<SleepNight?>()
/*
    init {
        initializeTonight()
    }
*/
/*
    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }
*/
    //TODO: CHANGE
    private suspend fun getTonightFromDatabase(): SleepNight? {
        //i think in this description is mistake
        //It is a Room feature that every time the data in the database changes, the LiveData nights is updated to show the latest data.
        var night = database.getTonight()
        return night
    }

    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            //why i update this?
           // tonight.value = getTonightFromDatabase()
            Log.i(TAG, "onStartTracking: finished")
        }
    }
    private suspend fun insert(night: SleepNight) {
        database.insert(night)
        Log.i(TAG, "insert: finished")
    }
 }