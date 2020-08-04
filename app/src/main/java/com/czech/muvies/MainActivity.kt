package com.czech.muvies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

const val BASE_IMAGE_PATH = "https://image.tmdb.org/t/p/w780"
const val LANGUAGE = "en-US"

class MainActivity : AppCompatActivity() {

    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
        val appBarConfig = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.featuredFragment,
                R.id.moviesFragment,
                R.id.tvShowsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfig)

    }

    fun showBottomNavigation() {
        bottom_nav.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        bottom_nav.visibility = View.GONE
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)

        if (navController.graph.startDestination == navController.currentDestination?.id) {
            if (backPressedOnce) {
                super.onBackPressed()
                return
            }
            backPressedOnce = true
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(2000) {
                backPressedOnce = false
            }
        } else {
            super.onBackPressed()
        }
    }
}
