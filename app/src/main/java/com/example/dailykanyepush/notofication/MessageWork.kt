package com.example.dailykanyepush.notofication

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dailykanyepush.receiver.sendNotification
const val KEY_RESULT = "result"

 class MessageWork(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    var text: String = ""


     override fun doWork(): Result {

         return try{
             Log.i(FirebaseService.TAG, "doWork: ")

             text = inputData.getString(MESSAGE)!!

            val notificationManager =
                ContextCompat.getSystemService(
                    applicationContext,
                    NotificationManager::class.java
                ) as NotificationManager
            notificationManager.sendNotification(text, applicationContext)
             Toast.makeText(applicationContext, "doWorkOnTheEnd", Toast.LENGTH_SHORT).show()

        addingDebug()
            return Result.success()
        }
        catch (throwable: Throwable) {

            // Error.e(throwable, "Error failure")
        Result.failure()
       }
    }
    companion object {
        const val MESSAGE = "message"
        const val TAG = "fcm"
    }

}

fun addingDebug(): Int{
    var sum = 3
    return sum
}



