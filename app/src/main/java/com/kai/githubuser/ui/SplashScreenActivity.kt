package com.kai.githubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import androidx.core.os.HandlerCompat.postDelayed

import com.kai.githubuser.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar!!.hide()

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, 1300)
    }
}