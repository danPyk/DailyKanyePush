package com.example.dailykanyepush.ui.home

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
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
        }catch (e: InvocationTargetException){
            return "Here gonna be quote"
        }catch (e: IOException){
            return "Here gonna be quote"
        }
        catch (e: FileNotFoundException){
            return "Here gonna be quote"
        }catch(e: SQLiteConstraintException){
            return "Here gonna be quote"
        }
    }

    fun fileExist() : Boolean{
        val fileName = "quote"
        var file = File(fileName)
        var fileExists = file.exists()

        return fileExists
    }
/*    fun fileExists(context: Context, filename: String?): Boolean {
        val file: File = context.getFileStreamPath(quote)
        return if (file == null || !file.exists()) {
            false
        } else true
    }*/

}