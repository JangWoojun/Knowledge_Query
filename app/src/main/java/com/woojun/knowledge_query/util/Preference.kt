package com.woojun.knowledge_query.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class Preference : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("default value", Context.MODE_PRIVATE)

    fun getMode(): Boolean {
        return prefs.getBoolean("mode", true)
    }

    fun setMode(mode: Boolean) {
        prefs.edit().putBoolean("mode", mode).apply()
    }

    fun isFirst(): Boolean {
        return prefs.getBoolean("isFirst", false)
    }

    fun notFirst() {
        prefs.edit().putBoolean("isFirst", true).apply()
    }
}