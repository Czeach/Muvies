package com.example.muvies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toolbar
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

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)

//        setupActionBarWithNavController(navController, appBarConfiguration)

//        bottom_nav.setupWithNavController(navController)

        FeaturedFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, FeaturedFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        setupNavigation()

    }

    private fun setupNavigation() {
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.featured_nav -> {
                    FeaturedFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, FeaturedFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()

                    true
                }
                R.id.movies_nav -> {
                    MoviesFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, MoviesFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()

                    true
                }
                R.id.tv_shows_nav -> {
                    TvShowsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, TvShowsFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()

                    true
                }
                else -> true
            }
        }
    }
}
