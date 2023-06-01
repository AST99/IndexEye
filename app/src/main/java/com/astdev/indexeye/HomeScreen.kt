package com.astdev.indexeye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.astdev.indexeye.databinding.HomeScreenBinding

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: HomeScreenBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= HomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController= Navigation.findNavController(this,R.id.activity_main_nav_host_fragment)
        setupWithNavController(binding.bottomNavigationView,navController)
    }
}