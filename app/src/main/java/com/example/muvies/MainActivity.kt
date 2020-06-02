package com.example.muvies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

//        setupActionBarWithNavController(navController, appBarConfiguration)

        bottom_nav.setupWithNavController(navController)

        setupNavigation()

    }

    private fun setupNavigation() {
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.featured_nav -> {
                    Toast.makeText(this, "Featured clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.movies_nav ->{
                    Toast.makeText(this, "Movies clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.tv_shows_nav -> {
                    Toast.makeText(this, "TV Shows clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
    }
}
