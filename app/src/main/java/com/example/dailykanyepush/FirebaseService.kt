package com.example.dailykanyepush

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dailykanyepush.receiver.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class FirebaseService: FirebaseMessagingService() {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        remoteMessage?.data?.let {
            val data = remoteMessage.data
            val myCustomKey = data["key"]
            val filename = "myfile"

            Log.i(TAG, "onMessageReceived: notification.let sarted")
            val sdf = SimpleDateFormat(" kk:mm")
            val currentTime = sdf.format(Date()).trim()

            this.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(myCustomKey?.toByteArray())
            }

            val saveRequest =
                PeriodicWorkRequestBuilder<MessageWork>(24, TimeUnit.HOURS)
                  //  .setInitialDelay()
          //  PeriodicWorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS
                    .build()
            WorkManager.getInstance(applicationContext).enqueue(saveRequest)


          if (res == currentTime ) {
                Log.i(TAG, "onMessageReceived: if passed")
                // sendNotification(it.body!!)
                startTimer()
         }
        }
        //wile app is in foreground
        remoteMessage?.notification?.let {

        }
    }//TODO add coruting

    fun getDataFromUser(): String?{
        var stringHolder: String? = application?.openFileInput("myfileeeeeeeee")?.bufferedReader()?.useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
        return stringHolder?.trim()
    }


    //TODO Step 3.2 log registration token
    // [START on_new_token]
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */

    override fun onNewToken(p0: String) {
        Log.d(TAG, "Refreshed token is $p0")
        sendRegistrationToServer(p0)
    }
    // [END on_new_token]
    /**
     * Persist token to third-party servers.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
    }
    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     *///todo
/*    private fun sendNotification(messageBody: String) {

        val notificationManager = ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }*/
    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
    //todo check sendnotification arguments
    private fun startTimer() {

        // TODO: Step 1.15 call cancel notification
        val notificationManager =
            ContextCompat.getSystemService(
                this.application,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.cancelNotifications()

       notificationManager.sendNotification(Context.NOTIFICATION_SERVICE,  this.application)
    }
    fun NotificationManager.cancelNotifications() {
        cancelAll()
    }


}