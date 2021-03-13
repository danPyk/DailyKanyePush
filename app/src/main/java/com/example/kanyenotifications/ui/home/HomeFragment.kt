package com.example.kanyenotifications.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kanyenotifications.R
import com.google.firebase.messaging.FirebaseMessaging


//sys time, how to pass,

//first page with info
// report bug, buy bear, contact

//delete one cart
//first deploy on phone, then thinking how to manage hours

class HomeFragment : androidx.fragment.app.Fragment() {
    //topic used to send notificaions
    private val topic = "kanyepush"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val binding: com.example.kanyenotifications.databinding.FragmentHomeBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false
            )

        val application = requireNotNull(this.activity).application

        val viewModelFactory = HomeViewModelFactory(application)
        val homeViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        binding.quoteTextView.text = homeViewModel.getQuote()
        binding.quoteTextView.invalidate()
        binding.button.setOnClickListener {
            val quote = homeViewModel.getQuote()
            shareSucces(quote)
        }
        createChannel(
            getString(R.string.egg_notification_channel_id),
            getString(R.string.egg_notification_channel_name)
        )
        subscribeTopic()

        return binding.root
    }

    private fun createChannel(channelId: String, channelName: String) {
        // create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                //disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description =
                getString(R.string.breakfast_notification_channel_description)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    //allow send notif to multiple users
    private fun subscribeTopic() {
        // [START subscribe_topic]
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener {/* task ->
                var message = getString(R.string.message_subscribed)
                if (!task.isSuccessful) {
                    message = getString(R.string.message_subscribe_failed)
                }*/
            }
    }

    private fun shareSucces(quote: String) {
        try {

            startActivity(getShareIntent(quote))
        } catch (e: SQLiteConstraintException) {

        }
    }

    private fun getShareIntent(quote: String): Intent {

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, quote)
        return shareIntent
    }


}