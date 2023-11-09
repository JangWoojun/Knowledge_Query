package com.woojun.knowledge_query.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavigation.setItemSelected(R.id.home)

            bottomNavigation.setOnItemSelectedListener {
                when (it) {
                    R.id.home -> navController.navigate(R.id.home)
                    R.id.myLibrary -> navController.navigate(R.id.myLibrary)
                    R.id.query -> navController.navigate(R.id.query)
                }
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                bottomNavigation.setItemSelected(destination.id)
            }
        }

    }

    fun hideBottomNavigation(state: Boolean){
        binding.apply {
            if (state) {
                bottomNavigation.visibility = View.GONE
            } else {
                bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    fun changeWindow(type: Boolean) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            if (type) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.reader_sub_background_light)
            } else {
                window.statusBarColor = ContextCompat.getColor(this, R.color.white)
            }
        } else {
            if (type) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.reader_sub_background_dark)
            } else {
                window.statusBarColor = ContextCompat.getColor(this, R.color.black)
            }
        }
    }


}