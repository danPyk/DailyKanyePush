package com.example.dailykanyepush.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

const val TAG = "HomeViewModel"

class HomeViewModel(application: Application) :
    AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext


    fun onStartTracking(time: String) {
        viewModelScope.launch {
       /*     val sdf = SimpleDateFormat(" kk:mm")
            val currentTime = sdf.format(Date())*/
            insert(time)
        }
    }
    private suspend fun insert(currentTime: String) {
        val filenamee = "myfileeeeeeeee"
        context.openFileOutput(filenamee, Context.MODE_PRIVATE).use {
            it.write(currentTime?.toByteArray())
        }
    }

/*    fun applyWork(){
        val workManager = WorkManager.getInstance(getApplication())
        workManager.enqueueUniquePeriodicWork("unique-name", ExistingPeriodicWorkPolicy.REPLACE, request)
    }*/

}