package com.example.dailykanyepush

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.RemoteMessage

class FirebaseBroadcastReceiver: BroadcastReceiver() {

    val TAG: String = FirebaseBroadcastReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {

        val dataBundle = intent.extras
        if (dataBundle != null)
            for (key in dataBundle.keySet()) {
                Log.d(TAG, "dataBundleeeeeee: " + key + " : " + dataBundle.get(key))
            }
        val remoteMessage = RemoteMessage(dataBundle)
    }
}
