package com.astdev.indexeye.fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.astdev.indexeye.activities.HomeScreen
import com.astdev.indexeye.adapters.PlumberListAdapter
import com.astdev.indexeye.databinding.FragmentServicesBinding
import com.astdev.indexeye.models.PlumberModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ServicesFragment : Fragment(){

    private lateinit var binding: FragmentServicesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentServicesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentServicesBinding.inflate(inflater, container, false)


        (requireActivity() as HomeScreen).setActionBarTitle("Services")


        requireActivity().onBackPressedDispatcher
            .addCallback(requireActivity(), object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Fragment back pressed invoked")
                    // Do custom work here
                    binding.presentService.visibility = View.VISIBLE
                    binding.plumberContactList.visibility = View.GONE
                }
            })


        // Inflate the layout for this fragment
        return binding.root

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContacterPlombier.setOnClickListener {
            (requireActivity() as HomeScreen).setActionBarTitle("Trouver un plombier")
            /*(requireActivity() as HomeScreen).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (requireActivity() as HomeScreen).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
            (requireActivity() as HomeScreen).supportActionBar!!.setDisplayShowHomeEnabled(true)*/


            binding.presentService.visibility = View.GONE
            binding.plumberContactList.visibility = View.VISIBLE

            getPlumberList()
        }

        binding.btnSignalerFuite.setOnClickListener {
            //showPlumberList()
        }
    }


    private fun getPlumberList() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.recyclerView.layoutManager = layoutManager

        val mDatabase = FirebaseDatabase.getInstance().getReference("Plumbers")
        val userList: MutableList<PlumberModels?> = ArrayList()
        val adapter = PlumberListAdapter(userList)
        binding.recyclerView.adapter = adapter

        mDatabase.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    try {
                        userList.add(snapshot.getValue(PlumberModels::class.java))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}