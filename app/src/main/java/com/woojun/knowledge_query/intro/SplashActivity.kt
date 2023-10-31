package com.woojun.knowledge_query.intro

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.woojun.knowledge_query.util.Preference
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        if (uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES) {
            Preference.prefs.setMode(true)
        } else {
            Preference.prefs.setMode(false)
        }

        Handler().postDelayed({
            if (Preference.prefs.isFirst()) {
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