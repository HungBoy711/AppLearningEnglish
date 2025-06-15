package com.example.learningapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreen : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var splashLogo: ImageView
    private val splashDuration = 2000L
    private val progressIncrementDelay = splashDuration / 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        progressBar = findViewById(R.id.progress_bar)
        splashLogo = findViewById(R.id.splash_logo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        splashLogo.alpha = 0f
        splashLogo.animate()
            .alpha(1f)
            .setDuration(1000)
            .start()

        animateProgressBar()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, splashDuration)
    }

    private fun animateProgressBar() {
        var progress = 0
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (progress <= 100) {
                    progressBar.progress = progress
                    progress += 1
                    handler.postDelayed(this, progressIncrementDelay)
                }
            }
        }
        handler.post(runnable)
    }
}