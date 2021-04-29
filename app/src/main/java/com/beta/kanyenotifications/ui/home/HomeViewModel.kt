package com.beta.kanyenotifications.ui.home

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.reflect.InvocationTargetException

class HomeViewModel(application: Application) :
    AndroidViewModel(application) {

    private val context = application


    fun getQuote(): String {

        try {
            val userHourString =
                context.openFileInput("quote")?.bufferedReader()
                    ?.useLines { lines ->
                        lines.fold("") { some, text ->
                            "$some\n$text"
                        }
                    }

            val userHours = userHourString!!.trim()
            context.openFileInput("quote")?.close()

            return userHours
        } catch (e: InvocationTargetException) {
            return "Here gonna be quote"
        } catch (e: IOException) {
            return "Here gonna be quote"
        } catch (e: FileNotFoundException) {
            return "Here gonna be quote"
        } catch (e: SQLiteConstraintException) {
            return "Here gonna be quote"
        }
    }

}