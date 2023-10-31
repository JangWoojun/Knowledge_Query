package com.woojun.knowledge_query.main

import android.app.UiModeManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.ActivityMainBinding
import com.woojun.knowledge_query.util.Preference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        if (uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES) {
            Preference.prefs.setMode(true)
        } else {
            Preference.prefs.setMode(false)
        }

        binding.apply {
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavigation.setItemSelected(R.id.home)

            bottomNavigation.setOnItemSelectedListener {
                when (it) {
                    R.id.home -> navController.navigate(R.id.home)
                    R.id.myLibrary -> navController.navigate(R.id.myLibrary)
                    R.id.bookmark -> navController.navigate(R.id.bookmark)
                    R.id.setting -> navController.navigate(R.id.setting)
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
                bottomNavigation.visibility = View.INVISIBLE
            } else {
                bottomNavigation.visibility = View.VISIBLE
            }
        }
    }


}