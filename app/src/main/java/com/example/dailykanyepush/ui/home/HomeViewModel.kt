package com.example.dailykanyepush.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel

const val TAG = "HomeViewModel"

class HomeViewModel(application: Application) :
    AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
/*    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text*/

    fun getQuote(): String {
        var userHourString =
            context.openFileInput("quote")?.bufferedReader()
                ?.useLines { lines ->
                    lines.fold("") { some, text ->
                        "$some\n$text"
                    }
                }
        var userHours = userHourString!!.trim()
        return userHours
    }

}