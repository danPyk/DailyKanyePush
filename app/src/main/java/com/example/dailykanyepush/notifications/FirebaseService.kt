package com.example.dailykanyepush.notifications

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

        remoteMessage?.data?.let {
            val data = remoteMessage.data
            val myCustomKey = data["key"]
            val fileName = "quote"

            insertQuote(fileName, myCustomKey)
            applyWork(myCustomKey, getHourFromUser())
        }
        //while app is in foreground
        remoteMessage?.notification?.let {
        }
    }
    //TODO add coruting?
    fun applyWork(txt: String?, userHours: Long) {
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

    private fun OneTimeWorkRequest.Builder.delay(userTimeSettings: Long): OneTimeWorkRequest.Builder {
        val actualHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toLong()

        val messageDelay = when {
            actualHour < userTimeSettings -> userTimeSettings - actualHour
            actualHour > userTimeSettings -> 24L - actualHour + userTimeSettings
            else -> 0
        }
        // return if (true) {
        //  val testDelayInMinutes = delayInHours
        return setInitialDelay(
            messageDelay,
            TimeUnit.MINUTES)
    }
    fun getHourFromUser(): Long {
        var userHourString =
            applicationContext.openFileInput("UserTimeSetting")?.bufferedReader()
                ?.useLines { lines ->
                    lines.fold("") { some, text ->
                        "$some\n$text"
                    }
                }
        var userHours = userHourString!!.trim().toLong()
        return userHours
    }
    fun insertQuote(fileName: String, timSetByUser: String?) {
        this.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(timSetByUser?.toByteArray())
        }
    }
    override fun onNewToken(p0: String) {
        Log.d(TAG, "Refreshed token is $p0")
        sendRegistrationToServer(p0)
    }

    /**
     * Persist token to third-party servers.
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
    }
    /**
     * Create and show a simple notification containing the received FCM message.
     * @param messageBody FCM message body received.
     */



}
