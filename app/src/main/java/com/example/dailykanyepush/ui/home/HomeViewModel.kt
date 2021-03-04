package com.example.dailykanyepush.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import java.io.FileNotFoundException
import java.lang.reflect.InvocationTargetException

const val TAG = "HomeViewModel"

class HomeViewModel(application: Application) :
    AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    fun getQuote(): String {
        try{
            var userHourString =

                context.openFileInput("quote")?.bufferedReader()
                    ?.useLines { lines ->
                        lines.fold("") { some, text ->
                            "$some\n$text"
                        }
                    }
            var userHours = userHourString!!.trim()

            return userHours
        }catch (e: InvocationTargetException ){
            return ""
        }catch (e: RuntimeException ){
            return ""
        }
        catch (e: FileNotFoundException ){
            return ""
        }
    }
}