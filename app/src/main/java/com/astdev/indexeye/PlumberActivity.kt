package com.astdev.indexeye

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.astdev.indexeye.activities.FirstActivity
import com.astdev.indexeye.classes.AlertDialogClass
import com.astdev.indexeye.databinding.ActivityPlumberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlumberBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        databaseReference = FirebaseDatabase.getInstance().getReference("Plumbers").child((user)!!.uid)

        setSupportActionBar(binding.toolbar)

        getPlumberData()
    }


    private fun getPlumberData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value.toString()
                val phone = snapshot.child("phone").value.toString()
                val mail = snapshot.child("mail").value.toString()
                val passWrd = snapshot.child("passWrd").value.toString()

                binding.NameProfil.text = name
                binding.Mail.text = mail
                binding.Mobile.text = phone
                //binding.passWrdProfil.text = passWrd
            }
            override fun onCancelled(error: DatabaseError) {}
        })
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
        val logOutDialog = AlertDialogClass.logOutDialog(this@PlumberActivity)
        logOutDialog.show()
        val btnOui: Button = logOutDialog.findViewById(R.id.btnOui)
        btnOui.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            logOutDialog.dismiss()
            finish()
        }

        val btnNon: Button = logOutDialog.findViewById(R.id.btnNon)
        btnNon.setOnClickListener {
            logOutDialog.dismiss()
        }
    }

}