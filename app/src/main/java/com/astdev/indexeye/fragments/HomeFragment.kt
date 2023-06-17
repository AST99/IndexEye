package com.astdev.indexeye.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.astdev.indexeye.R
import com.astdev.indexeye.activities.HomeScreen
import com.astdev.indexeye.classes.GraphClass.Companion.graphAnnuel
import com.astdev.indexeye.classes.GraphClass.Companion.graphHerbdo
import com.astdev.indexeye.classes.GraphClass.Companion.graphMensuel
import com.astdev.indexeye.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Objects

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val user = FirebaseAuth.getInstance().currentUser
        databaseReference = FirebaseDatabase.getInstance().getReference("Simple Users").child(user!!.uid)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        (requireActivity() as HomeScreen).setActionBarTitle("Accueil")

        // Inflate the layout for this fragment
        return binding!!.root
    }

    @SuppressLint("NonConstantResourceId", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinnerItem: MutableList<String> = ArrayList()
        spinnerItem.add("Résumé herbdomadaire")
        spinnerItem.add("Résumé mensuel")
        spinnerItem.add("Résumé annuel")
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerItem)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                when (i) {
                    0 -> graphHerbdo(binding!!.barChart)
                    1 -> graphMensuel(binding!!.barChart)
                    2 -> graphAnnuel(binding!!.barChart)
                    else -> throw IllegalStateException("Unexpected value: $i")
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        userData()
    }

    private fun userData(){
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val userName = Objects.requireNonNull(snapshot.child("name").value).toString()
                    binding!!.txtWel.text = "Bonjour $userName"
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }
}