package com.example.dailykanyepush.ui.home

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.dailykanyepush.R
import com.example.dailykanyepush.databinding.FragmentHomeBinding
import com.google.firebase.messaging.FirebaseMessaging


//sys time, how to pass,

//first page with info
// report bug, buy bear, contact

//delete one cart
//first deploy on phone, then thinking how to manage hours

class HomeFragment : Fragment() {
    private val TOPIC = "Daily-Kanye"

    private lateinit var homeViewModel: HomeViewModel
   // val br: BroadcastReceiver = MyBroadcastReceiver()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
/*        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)*/
        //cval root = inflater.inflate(R.layout.fragment_home, container, false)
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        //reference to app context
        //dao ref

        //instance of factory
        val viewModelFactory = SleepTrackerViewModelFactory(getDBref(), scope2())

        //ref to sleepTrackerViewModel
        val sleepTrackerViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(HomeViewModel::class.java)
        binding.setLifecycleOwner(this)

        binding.sleepTrackerViewModel = sleepTrackerViewModel
        /* val activityReceiver: BroadcastReceiver = object : BroadcastReceiver() {
             override fun onReceive(context: Context, intent: Intent) {
                 val bundle = intent.getBundleExtra("msg")
                 binding.quoteTextView.text = bundle!!.getString("msgBody")
             }
         }
 */

/*        binding.button.setOnClickListener {
            binding.textview.text = currentDate

        }*/
        /*    binding.button2.setOnClickListener{
                if(currentDate.toString() != binding.editTextTIme.toString()){
                    binding.textView2.text = null
                }
            }*/

        // textDate.setText(currentDate)

/*        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textDate.text = it
        })*/

        createChannel(
                getString(R.string.egg_notification_channel_id),
                getString(R.string.egg_notification_channel_name)
        )
        createChannel(
                getString(R.string.breakfast_notification_channel_id),
                getString(R.string.breakfast_notification_channel_name)
        )
        subscribeTopic()
        binding.sleepTrackerViewModel = sleepTrackerViewModel

        return binding.root
        /*       fun onCreate(savedInstanceState: Bundle?) {
                  super.onCreate(savedInstanceState)

                  if (activityReceiver != null) {
                      val intentFilter = IntentFilter("ACTION_STRING_ACTIVITY")
                      registerReceiver(activityReceiver, intentFilter)
                  }
              }*/
    }
    fun getDBref(): SleepDatabaseDao {
        var dataSource = SleepDatabase.getInstance(scope2()).sleepDatabaseDao
        return dataSource
    }

    fun scope2(): Application {
        val application = requireNotNull(this.activity).application
        return application
    }

    /*
        override fun onCreate(savedInstanceState: Bundle?) {
            val filter = IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY).apply {
                addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            }
            registerReceiver(br, filter)
            super.onCreate(savedInstanceState)
        }
    */
    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_HIGH
            )
                // TODO: Step 2.6 disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.breakfast_notification_channel_description)

            val notificationManager = requireActivity().getSystemService(
                    NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(notificationChannel)

        }
        // TODO: Step 1.6 END create channel
    }
    private fun subscribeTopic() {
        // [START subscribe_topic]
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->
                var message = getString(R.string.message_subscribed)
                if (!task.isSuccessful) {
                    message = getString(R.string.message_subscribe_failed)
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        // [END subscribe_topics]
    }


}