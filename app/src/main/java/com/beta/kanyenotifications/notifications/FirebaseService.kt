package com.beta.kanyenotifications.notifications

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*
import java.util.concurrent.TimeUnit


class FirebaseService : FirebaseMessagingService() {

    companion object {
        const val TAG = "MyFirebaseMsgService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.let {
            val data = remoteMessage.data
            val myCustomKey = data["key"]
            val fileName = "quote"

            insertQuote(fileName, myCustomKey)
            var fileExist = fileExist()
            if(fileExist==true) {
                applyWork(myCustomKey, getHourFromUser())
            }

        }
        //while app is in foreground
        remoteMessage.notification?.let {
        }
    }
    //TODO add coruting?
    //enqueue work
    private fun applyWork(txt: String?, userHours: Int) {

        val data = Data.Builder().putAll(mapOf(MessageWork.MESSAGE to txt)).build()
        val constraints: Constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
        }.build()
        val request =
            OneTimeWorkRequestBuilder<MessageWork>()
                .setConstraints(constraints)
                .setInputData(data)
               .delay(userHours)
                .build()
        val workManager = WorkManager.getInstance(application)
        workManager.enqueue(request)
    }
    //todo change hour
    //set delay of notification
    private fun OneTimeWorkRequest.Builder.delay(userTimeSettings: Int): OneTimeWorkRequest.Builder {
        val actualHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).times(60)
        val actualMinute = Calendar.getInstance().get(Calendar.MINUTE)

        val sumOfActuaolHourAndMinute = actualHour+actualMinute

        val messageDelay = when {
            sumOfActuaolHourAndMinute < userTimeSettings -> userTimeSettings - sumOfActuaolHourAndMinute
            sumOfActuaolHourAndMinute > userTimeSettings -> 1440 - sumOfActuaolHourAndMinute + userTimeSettings
            else -> 0
        }
        val messageDelayLong = messageDelay.toLong()
        return setInitialDelay(
            10L,
            TimeUnit.SECONDS)
    }
     fun fileExist(): Boolean? {
        return application.getFileStreamPath("UserTimeSetting")?.exists()
    }
    private fun getHourFromUser(): Int {
        val userHourString =
            applicationContext.openFileInput("UserTimeSetting")?.bufferedReader()
                ?.useLines { lines ->
                    lines.fold("") { some, text ->
                        "$some\n$text"
                    }
                }

        val hour = userHourString?.substring(1, 3)?.toInt()
        val hourMultiple = hour!!.times(60)
        val minute = userHourString.substring(3, 5).toInt()
        application.openFileInput("UserTimeSetting")?.close()

        return minute.plus(hourMultiple)
    }
    private fun insertQuote(fileName: String, timSetByUser: String?) {
        this.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(timSetByUser?.toByteArray())

        }
    }
    //genereting new token after first launch
    override fun onNewToken(p0: String) {
        Log.d(TAG, "Refreshed token is $p0")
        sendRegistrationToServer(p0)
    }

    private fun sendRegistrationToServer(token: String?) {
    }



}
