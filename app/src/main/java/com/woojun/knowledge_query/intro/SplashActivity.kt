package com.woojun.knowledge_query.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.woojun.knowledge_query.util.Preference
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (Preference.prefs.isFirst()) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finishAffinity()
            } else {
                startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                finishAffinity()
            }
        }, 1500)
    }
}