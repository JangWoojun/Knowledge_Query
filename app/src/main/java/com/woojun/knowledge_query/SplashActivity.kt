package com.woojun.knowledge_query

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (Preference.prefs.isFirst()) {
            Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finishAffinity()
            }, 1500)
        } else {
            Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                finishAffinity()
            }, 1500)
        }
    }
}