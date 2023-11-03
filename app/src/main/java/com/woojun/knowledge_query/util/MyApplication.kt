package com.woojun.knowledge_query.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        super.onCreate()
    }
}

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("default value", Context.MODE_PRIVATE)

    fun isFirst(): Boolean {
        return prefs.getBoolean("isFirst", false)
    }

    fun notFirst() {
        prefs.edit().putBoolean("isFirst", true).apply()
    }

    fun isDarkMode(): Boolean {
        return prefs.getBoolean("isDarkMode", false)
    }

    fun setMode(isDarkMode: Boolean) {
        prefs.edit().putBoolean("isDarkMode", isDarkMode).apply()
    }
}