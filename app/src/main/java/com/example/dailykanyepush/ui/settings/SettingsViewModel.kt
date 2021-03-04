package com.example.dailykanyepush.ui.settings

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.dailykanyepush.R
import com.google.firebase.messaging.FirebaseMessaging

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

/*    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text*/


    fun insert(fileName: String, timSetByUser: String) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(timSetByUser?.toByteArray())
        }
    }
    fun checkIfSingle(string: String): String{
        var copyString = string
        if(copyString.length == 1){
            copyString= "0$copyString"
        }
        return copyString
    }
    //allow send notif to multiple users


}