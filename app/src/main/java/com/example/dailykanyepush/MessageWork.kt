package com.example.dailykanyepush

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dailykanyepush.receiver.sendNotification

class MessageWork(appcontext: Context, workerParams: WorkerParameters): Worker(appcontext, workerParams) {
    override fun doWork(): Result {


        return Result.retry()
    }
}

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