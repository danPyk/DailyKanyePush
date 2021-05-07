package com.beta.kanyenotifications.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.beta.kanyenotifications.BuildConfig
import com.beta.kanyenotifications.R
import com.beta.kanyenotifications.databinding.FragmentHomeBinding
import com.google.firebase.messaging.FirebaseMessaging


class HomeFragment : androidx.fragment.app.Fragment() {
    //topic used to send notificaions
    private val topic = "kanyepushh"
    private  var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
         _binding =
             FragmentHomeBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = HomeViewModelFactory(application)
        val homeViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(HomeViewModel::class.java)
        //why where i chane this to just binding i dont have error? its a val
        _binding?.lifecycleOwner = this
        _binding?.homeViewModel = homeViewModel


        _binding?.quoteTextView?.text = homeViewModel.getQuote()
        _binding?.quoteTextView?.invalidate()
        _binding?.button?.setOnClickListener {
            val quote = homeViewModel.getQuote()
            shareSucces(quote)

        }
        createChannel(
            getString(R.string.notification_channel_id),

            getString(R.string.notification_channel_name)
        )
        subscribeTopic()

/*        AppWatcher.objectWatcher.expectWeaklyReachable(binding.quoteTextView, "View was detached")
        AppWatcher.objectWatcher.expectWeaklyReachable(binding.button, "View was detached")
        AppWatcher.objectWatcher.expectWeaklyReachable( application, "View was detached")*/

        val view = binding?.root
        return view!!
    }

     private fun createChannel(channelId: String, channelName: String) {


        // create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
                //disable badges for this channel
                .apply {
                    setShowBadge(false)
                }
            //custom sound
            val att = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
            val uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID
                    + "/" + R.raw.homecoming)

            val preference = getPreference()
            if(!preference!!){
                notificationChannel.setSound(uri, att)
            }
          //  Log.i(TAG, "createChannel: $preference")
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
/*            notificationChannel.description =
                getString(R.string.breakfast_notification_channel_description)*/
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
        } catch (e: ActivityNotFoundException) {

        }
    }

    private fun getShareIntent(quote: String): Intent {

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, quote)
        return shareIntent
    }
    private fun getPreference(): Boolean? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        return sharedPref?.getBoolean(getString(R.string.sound_key), false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}



