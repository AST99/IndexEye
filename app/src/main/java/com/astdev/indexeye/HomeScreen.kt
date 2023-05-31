package com.astdev.indexeye

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.astdev.indexeye.databinding.HomeScreenBinding
import java.util.Objects


class HomeScreen : AppCompatActivity() {

    private lateinit var binding: HomeScreenBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= HomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.topAppBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navController= Navigation.findNavController(this,R.id.activity_main_nav_host_fragment)
        setupWithNavController(binding.bottomNavigationView,navController)

    }
}