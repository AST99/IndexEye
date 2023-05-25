package com.astdev.indexeye

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astdev.indexeye.databinding.FragmentInscriptionBinding
import com.astdev.indexeye.databinding.FragmentInscriptionFailBinding


class InscriptionFail : Fragment() {

    private lateinit var binding: FragmentInscriptionFailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentInscriptionFailBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentInscriptionFailBinding.inflate(inflater, container, false)

        binding.btnEssayer.setOnClickListener {
            val inscriptionFragment = InscriptionFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_1, inscriptionFragment)?.commit()
        }

        return binding.root
    }
}