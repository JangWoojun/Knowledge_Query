package com.woojun.knowledge_query.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woojun.knowledge_query.databinding.ActivityIntroBinding

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