package com.astdev.indexeye

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*var mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
        mDatabase.child()*/

        /*DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().
        getCurrentUser()).getUid()).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful())
                Log.e("firebase", "Error getting data", task.getException());
            else {
                strAdress=String.valueOf(task.getResult().child("quartier").getValue(String.class));
                strNbrePersonne=String.valueOf(task.getResult().child("nbrePersonne").getValue(String.class));
                strConsoMoyenne=String.valueOf(task.getResult().child("consoMoyenne").getValue(String.class));
                userName=(String.valueOf(task.getResult().child("nomPrenom").getValue(String.class)));*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}