package com.astdev.indexeye.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.astdev.indexeye.R
import com.astdev.indexeye.adapters.Adapter
import com.astdev.indexeye.classes.AlertDialogClass
import com.astdev.indexeye.databinding.ActivityListeFuiteBinding
import com.astdev.indexeye.models.FuitesModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListeFuite : AppCompatActivity() {

    private lateinit var binding: ActivityListeFuiteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListeFuiteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.title = "Fuite(s) signalée(s)"

        listeFuites()
    }



    fun listeFuites() {

        val layoutM = LinearLayoutManager(this)
        layoutM.orientation = LinearLayoutManager.VERTICAL

        binding.recyclerView.layoutManager = layoutM

        val model_List: MutableList<FuitesModel?> = ArrayList()
        val adapter = Adapter(model_List)
        binding.recyclerView.adapter = adapter


        val mDatabase = FirebaseDatabase.getInstance().reference.child("Fuites signalées")
        mDatabase.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    try {
                        model_List.add(snapshot.getValue(FuitesModel::class.java))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ListeFuite, databaseError.message, Toast.LENGTH_SHORT).show()
            }
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
        val logOutDialog = AlertDialogClass.logOutDialog(this@ListeFuite)
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