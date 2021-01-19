/*
 * Created by Dmitry Lipski on 19.01.21 16:24
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 19.01.21 16:23
 */

package com.lipssoftware.manchester.united

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.lipssoftware.manchester.united.databinding.ActivityMainBinding
import com.lipssoftware.manchester.united.utils.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.news, R.navigation.standings, R.navigation.fixtures, R.navigation.squad)
        val controller = binding.bottomNavView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        controller.observe(this) { navController ->
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.navigation_fullnews -> {
                        collapseBars()
                    }
                    R.id.navigation_player_profile -> {
                        collapseBars()
                    }
                    else -> {
                        collapseBars(false)
                    }
                }
            }
        }
        currentNavController = controller
    }

    private fun collapseBars(hide: Boolean = true) {
        if (hide) binding.bottomNavView.isVisible = false
        else Handler(Looper.getMainLooper()).postDelayed(
            { binding.bottomNavView.isVisible = true }, 350)
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}