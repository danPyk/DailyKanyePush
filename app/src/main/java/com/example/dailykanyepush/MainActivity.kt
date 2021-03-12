package com.example.dailykanyepush

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.dailykanyepush.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
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
                var file = fileExist()
                if (file) {
                    return NavigationUI.navigateUp(navController, drawerLayout)

                } else {
                    Toast.makeText(this, "you need to save time", Toast.LENGTH_SHORT).show()
                    return true
                }
            }
            else -> {
                return NavigationUI.navigateUp(navController, drawerLayout)
            }
        }

    }
    fun fileExist() : Boolean{
       val file = getFileStreamPath("UserTimeSetting")

        var exist = file.exists()
        return exist
    }



}
