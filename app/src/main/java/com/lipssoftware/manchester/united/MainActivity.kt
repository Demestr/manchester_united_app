/*
 * Created by Dmitry Lipski on 15.01.21 17:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 15.01.21 14:24
 */

package com.lipssoftware.manchester.united

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lipssoftware.manchester.united.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_news, R.id.navigation_standings, R.id.navigation_squad))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.navigation_fullnews -> { collapseBars() }
                R.id.navigation_player_profile -> { collapseBars() }
                else -> { collapseBars(false) }
            }
        }
    }

    private fun collapseBars(hide: Boolean = true){
        if (hide) supportActionBar?.hide() else supportActionBar?.show()
        binding.bottomNavView.isVisible = !hide
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}