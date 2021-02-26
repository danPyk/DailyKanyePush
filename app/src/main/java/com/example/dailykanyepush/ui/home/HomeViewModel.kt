package com.example.dailykanyepush.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "HomeViewModel"

class HomeViewModel(application: Application) :
    AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext


    fun onStartTracking() {
        viewModelScope.launch {
            val sdf = SimpleDateFormat(" kk:mm")
            val currentTime = sdf.format(Date())
            insert(currentTime)
        }
    }
    private suspend fun insert(currentTime: String) {
        val filenamee = "myfileeeeeeeee"
        context.openFileOutput(filenamee, Context.MODE_PRIVATE).use {
            it.write(currentTime?.toByteArray())
        }
    }

}