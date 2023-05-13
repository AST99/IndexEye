package com.astdev.indexeye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.astdev.indexeye.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //supportFragmentManager.beginTransaction().replace(R.id.nav_1, WelcomeFragment()).commit()
    }
}