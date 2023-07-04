package com.astdev.indexeye.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.astdev.indexeye.R
import com.astdev.indexeye.classes.AlertDialogClass
import com.astdev.indexeye.databinding.HomeScreenBinding
import com.google.firebase.auth.FirebaseAuth

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: HomeScreenBinding = HomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val navController: NavController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)
        setupWithNavController(binding.bottomNavigationView,navController)

    }

    fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.topbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_deconnexion -> {
                showDismiss()
                /*val logOutDialog = AlertDialogClass.logOutDialog(this@HomeScreen)
                logOutDialog.show()*/
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDismiss(){
        val logOutDialog = AlertDialogClass.logOutDialog(this@HomeScreen)
        logOutDialog.show()
        val btnOui: Button = logOutDialog.findViewById(R.id.btnOui)
        btnOui.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            logOutDialog.dismiss()
            startActivity(Intent(applicationContext, FirstActivity::class.java))
            finish()
        }

        val btnNon: Button = logOutDialog.findViewById(R.id.btnNon)
        btnNon.setOnClickListener {
            logOutDialog.dismiss()
        }
    }
}