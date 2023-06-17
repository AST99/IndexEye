package com.astdev.indexeye.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astdev.indexeye.R
import com.astdev.indexeye.activities.HomeScreen

class ServicesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as HomeScreen).setActionBarTitle("Services")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services, container, false)
    }

}