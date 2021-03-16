package com.beta.kanyenotifications.ui.settings

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.reflect.InvocationTargetException

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

/*    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text*/


    fun insertTimeToFile(fileName: String, timSetByUser: String) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(timSetByUser.toByteArray())
        }
    }
    //add 0 to hour/minute if it's single
    fun checkIfSingle(string: String): String{
        var copyString = string
        if(copyString.length == 1){
            copyString= "0$copyString"
        }
        return copyString
    }
    //allow send notif to multiple users
    fun getTime(): String {
        try{
            val userHourString =

                context.openFileInput("UserTimeSetting")?.bufferedReader()
                    ?.useLines { lines ->
                        lines.fold("") { some, text ->
                            "$some\n$text"
                        }

                    }
            context.openFileInput("UserTimeSetting")?.close()

            val userHours = userHourString!!.trim()
            val result = "Set time: "+userHours.substring(0, 2)+":"+userHours.substring(2, 4)

            return result
        }catch (e: InvocationTargetException){
            return ""
        }catch (e: IOException){
            return ""
        }
        catch (e: FileNotFoundException){
            return ""
        }catch(e: SQLiteConstraintException){
            return ""
        }
    }


}