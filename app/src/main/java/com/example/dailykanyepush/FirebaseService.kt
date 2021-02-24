package com.example.dailykanyepush

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.dailykanyepush.receiver.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.*


class FirebaseService: FirebaseMessagingService() {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        //        //while app is in background
            val data = remoteMessage.data
            val myCustomKey = data["my_custom_key"]
            val filename = "myfile"

            this.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(myCustomKey?.toByteArray())
                startTimer()
        }
        //while app is in foreground
        remoteMessage?.notification?.let {
            Log.i(TAG, "onMessageReceived: notification.let sarted")
            val sdf = SimpleDateFormat(" kk:mm")
            val currentDate = sdf.format(Date())

            if (getUserTime() != currentDate ) {
                Log.i(TAG, "onMessageReceived: if passed")
               // sendNotification(it.body!!)
            }
        }
    }//TODO add coruting

    private fun getUserTime(): String {
        Log.i(TAG, "getUserTime: started")

        val instanceDB = SleepDatabase.getInstance(application)
        var userTime = instanceDB.sleepDatabaseDao.getAllNights().toString()
        return userTime
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