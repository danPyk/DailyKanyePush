package com.beta.kanyenotifications.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.beta.kanyenotifications.receiver.sendNotification


class MessageWork(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    var text: String = ""

    override fun doWork(): Result {

        return try {

            text = inputData.getString(MESSAGE)!!

            val preferences = getPreference()



            if (!preferences) {
                val notificationManager =
                    ContextCompat.getSystemService(
                        applicationContext,
                        NotificationManager::class.java
                    ) as NotificationManager
                notificationManager.sendNotification(text, applicationContext)
                //  Toast.makeText(applicationContext, "doWorkOnTheEnd", Toast.LENGTH_SHORT).show()
            }

            return Result.success()
        } catch (throwable: Throwable) {

            // Error.e(throwable, "Error failure")
            Result.failure()
        }
    }

    companion object {
        //pass msg content
        const val MESSAGE = "message"
    }

    private fun getPreference(): Boolean {
        val mPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        val result = mPrefs.getBoolean("switch", false)

        return result


    }


}

