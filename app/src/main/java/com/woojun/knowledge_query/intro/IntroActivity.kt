package com.woojun.knowledge_query.intro

import android.app.UiModeManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woojun.knowledge_query.databinding.ActivityIntroBinding
import com.woojun.knowledge_query.util.Preference

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            if (uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES) {
                Preference.prefs.setMode(true)
            } else {
                Preference.prefs.setMode(false)
            }
        }
    }
}