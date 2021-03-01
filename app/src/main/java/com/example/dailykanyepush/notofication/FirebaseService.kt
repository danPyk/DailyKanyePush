package com.example.dailykanyepush.notofication

import android.util.Log
import androidx.work.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

private const val DO_NOT_DISTURB_EVENING = 23L
private const val DO_NOT_DISTURB_MORNING = 7L
private const val HOURS_IN_DAY = 24L
private const val MINUTES_IN_HOUR = 24L

class FirebaseService: FirebaseMessagingService() {

    companion object {
        const val TAG = "MyFirebaseMsgService"
    }
    /**
     * Called when message is received.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        remoteMessage?.data?.let {
            val data = remoteMessage.data
            val myCustomKey = data["key"]
           // val filename = "myfile"

     /*       val sdf = SimpleDateFormat(" kk:mm")
            val currentTime = sdf.format(Date()).trim()

            this.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(myCustomKey?.toByteArray())
            }*/
            applyWork(myCustomKey)
        }
        //while app is in foreground
        remoteMessage?.notification?.let {
        }
    }

    //TODO add coruting?
    fun applyWork(txt: String?){
       val data = Data.Builder().putAll(mapOf(MessageWork.MESSAGE to txt)).build()
/*        val constraints: Constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
        }.build()*/

        val request =
            OneTimeWorkRequestBuilder<MessageWork>()
                //  .setInitialDelay()
                //  PeriodicWorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS
                //.setConstraints(constraints)
                .setInputData( data)
             //   .delay()
                .build()
        val workManager = WorkManager.getInstance(application)
        workManager.enqueue(request)
    }
    fun getDataFromUser(): String?{
        var stringHolder: String? = application?.openFileInput("myfileeeeeeeee")?.bufferedReader()?.useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
        return stringHolder?.trim()
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
     *///todo

    //todo check sendnotification arguments



}
