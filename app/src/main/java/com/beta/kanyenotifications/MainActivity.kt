package com.beta.kanyenotifications

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.beta.kanyenotifications.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        firstLaunch()

        //  val coordinatorLayout = findViewById<CoordinatorLayout>(R.id.coordinatorLayout)
        setSupportActionBar(toolbar)
        //context used to shared pref

        drawerLayout = binding.drawerLayout
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_timer, R.id.nav_about
            ), drawerLayout
        )
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)


    }

    /*    override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.dot_menu, menu)
            return true
        }*/
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return when (navController.currentDestination?.id) {
            R.id.nav_timer -> {
                val file = fileExist()
                if (file) {
                    NavigationUI.navigateUp(navController, drawerLayout)

                } else {
                    Toast.makeText(this, "You need to save time first", Toast.LENGTH_SHORT).show()
                    true
                }
            }
            else -> {
                NavigationUI.navigateUp(navController, drawerLayout)
            }
        }

    }
    private fun displayTimerFragment() {
        findNavController(R.id.nav_host_fragment).navigate(R.id.nav_timer)
    }

    //checks is that first launch of app, and if yes then opens timer
    private fun firstLaunch() {
        val prefs: SharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(baseContext)
        val previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false)
        if (!previouslyStarted) {
            val edit = prefs.edit()
            edit.putBoolean(getString(R.string.pref_previously_started), java.lang.Boolean.TRUE)
            edit.apply()
            displayTimerFragment()

        }
    }

    private fun fileExist(): Boolean {
        val file = getFileStreamPath("UserTimeSetting")

        return file.exists()
    }


}
