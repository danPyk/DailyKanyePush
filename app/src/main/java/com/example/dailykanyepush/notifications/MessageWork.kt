package com.example.dailykanyepush.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dailykanyepush.receiver.sendNotification
const val KEY_RESULT = "result"

 class MessageWork(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    var text: String = ""


     override fun doWork(): Result {

         return try{

             text = inputData.getString(MESSAGE)!!

            val notificationManager =
                ContextCompat.getSystemService(
                    applicationContext,
                    NotificationManager::class.java
                ) as NotificationManager
            notificationManager.sendNotification(text, applicationContext)
           //  Toast.makeText(applicationContext, "doWorkOnTheEnd", Toast.LENGTH_SHORT).show()

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

