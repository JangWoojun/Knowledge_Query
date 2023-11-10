package com.woojun.knowledge_query.intro

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.main.MainActivity
import com.woojun.knowledge_query.util.BookViewModel
import com.woojun.knowledge_query.util.MyApplication

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val check = MyApplication.prefs.isDarkMode()
        if (check) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        viewModel.fetchData()

        Handler().postDelayed({
            if (MyApplication.prefs.isFirst()) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finishAffinity()
            } else {
                startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                finishAffinity()
            }
        }, 1500)

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }
}