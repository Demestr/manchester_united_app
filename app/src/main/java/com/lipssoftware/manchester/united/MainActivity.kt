/*
 * Created by Dmitry Lipski on 13.01.21 10:45
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 13.01.21 10:45
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lipssoftware.manchester.united.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_news, R.id.navigation_standings, R.id.navigation_squad))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.navigation_fullnews -> {
                    supportActionBar?.hide()
                    navView.isVisible = false
                }
                else -> {
                    supportActionBar?.show()
                    navView.isVisible = true
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    private fun hideBottomView(hide: Boolean){
//        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
//        val offset = if(hide) navView.height.toFloat() else 0f
//        ObjectAnimator.ofFloat(navView, "translationY", offset).apply {
//            duration = 500
//            start()
//        }.doOnEnd { navView.isVisible = hide }
//    }
}