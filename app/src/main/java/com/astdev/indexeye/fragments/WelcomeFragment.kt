package com.astdev.indexeye.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.astdev.indexeye.R
import com.astdev.indexeye.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentWelcomeBinding.inflate(layoutInflater)

    }

    @SuppressLint("CommitTransaction")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding.AccessAccount.setOnClickListener{
            val connexionFragment = ConnexionFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_1, connexionFragment)?.commit()
        }

        binding.CreateAccount.setOnClickListener {
            val inscriptionFragment = InscriptionFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_1, inscriptionFragment)?.commit()
        }

        return binding.root
    }

}