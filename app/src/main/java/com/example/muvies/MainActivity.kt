package com.example.muvies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.os.postDelayed
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.muvies.screens.featured.FeaturedFragment
import com.example.muvies.screens.movies.MoviesFragment
import com.example.muvies.screens.tvShows.TvShowsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

const val BASE_IMAGE_PATH = "https://image.tmdb.org/t/p/w780"

class MainActivity : AppCompatActivity() {

    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
//        actionBar?.setDisplayShowTitleEnabled(false)
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
