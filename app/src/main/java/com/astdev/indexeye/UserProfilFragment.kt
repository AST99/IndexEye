package com.astdev.indexeye

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.astdev.indexeye.databinding.FragmentUserProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Objects


class UserProfilFragment : Fragment() {

    private lateinit var binding: FragmentUserProfilBinding

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentUserProfilBinding.inflate(layoutInflater)

        val user = FirebaseAuth.getInstance().currentUser
        databaseReference = FirebaseDatabase.getInstance().getReference("Simple Users")
            .child((user)!!.uid)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserProfilBinding.inflate(inflater, container, false)

        (requireActivity() as HomeScreen).setActionBarTitle("IndexEye - User Profil")

        getUserData()

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun getUserData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val userName = snapshot.child("name").value.toString()
                val phone = snapshot.child("phone").value.toString()
                val mail = snapshot.child("mail").value.toString()
                //val passWrd = snapshot.child("passWrd").value.toString()
                val deviceId = snapshot.child("deviceId").value.toString()
                binding.NameProfil.text = userName
                binding.MailProfil.text = mail
                binding.MobileProfil.text = phone
                //binding.passWrdProfil.text = passWrd
                binding.DeviceId.text = deviceId
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

}