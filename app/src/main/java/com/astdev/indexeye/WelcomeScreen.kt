package com.astdev.indexeye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.astdev.indexeye.databinding.WelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {

    private lateinit var binding : WelcomeScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AccessAccount.setOnClickListener{
            startActivity(Intent(this,ConnexionScreen::class.java))
        }

        binding.CreateAccount.setOnClickListener {
            startActivity(Intent(this, InscriptionScreen::class.java))
        }
    }
}