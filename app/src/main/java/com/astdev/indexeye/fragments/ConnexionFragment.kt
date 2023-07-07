package com.astdev.indexeye.fragments

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.astdev.indexeye.R
import com.astdev.indexeye.activities.HomeScreen
import com.astdev.indexeye.activities.PlumberProfile
import com.astdev.indexeye.classes.AlertDialogClass
import com.astdev.indexeye.databinding.FragmentConnexionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.*
import java.util.Objects


class ConnexionFragment : Fragment() {

    private lateinit var binding: FragmentConnexionBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var type: String

    private lateinit var databaseReference: DatabaseReference

    //private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        binding = FragmentConnexionBinding.inflate(layoutInflater)

        //mAuth = FirebaseAuth.getInstance()
        mailAndPassWrdFocusListener()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment

        binding = FragmentConnexionBinding.inflate(inflater, container, false)

        val inscriptionMail: String? = activity?.intent?.getStringExtra("e-mail")
        val inscriptionPassWrd: String?= activity?.intent?.getStringExtra("pass")
        binding.Mail.setText(inscriptionMail)
        binding.passWrd.setText(inscriptionPassWrd)

        binding.CreateAccount.setOnClickListener {
            val inscriptionFragment = InscriptionFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_1, inscriptionFragment)?.commit()
        }

        binding.AccessAccount.setOnClickListener {
            mailAndPassWrdConnexion()
        }

        mailAndPassWrdFocusListener()
        return binding.root
    }


    private fun mailAndPassWrdConnexion(){

        val mail: String = binding.Mail.text.toString().trim()
        val passWrd: String = binding.passWrd.text.toString().trim()
        val dialog = AlertDialogClass.progressDialog(requireActivity())

        if ( /*valideMail()!=null || */TextUtils.isEmpty(binding.Mail.text))
            binding.mailContainer.error = "Votre e-mail est requis!"
        else if ( /*validePassWrd()!=null*/TextUtils.isEmpty(binding.passWrd.text))
            binding.passWrdContainer.error = "Votre mot de passe est requis!"
        else if (binding.passWrd.text!!.length < 5)
            binding.passWrdContainer.error = "Votre mot de passe doit contenir au moins 5 caractères"
        else {
            if (mail.isNotEmpty() && passWrd.isNotEmpty()){
                dialog.show()
                auth.signInWithEmailAndPassword(mail, passWrd).addOnCompleteListener {

                    if (it.isSuccessful) {
                        getUserData()
                        binding.Mail.text!!.clear()
                        binding.passWrd.text!!.clear()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(requireActivity(), "Adresse mail ou " +
                                "mot de passe incorrect", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
            } else
                Toast.makeText(requireActivity(), "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun valideMail(): String? {
        if (TextUtils.isEmpty(binding.Mail.text))
            return "Votre e-mail est requis!"
        else if (!Patterns.EMAIL_ADDRESS.matcher(
                Objects.requireNonNull<Editable>(binding.Mail.text).toString().trim { it <= ' ' }).matches()) {
            return "Veillez fournir un e-mail valide!"
        }
        return null
    }

    private fun validePassWrd(): String? {
        if (TextUtils.isEmpty(binding.passWrd.text))
            return "Veuillez saisir votre mot de passe!"
        else if (binding.passWrd.text!!.length < 5)
            return "Votre mot de passe doit contenir au moins 5 caractères"
        return null
    }

    private fun mailAndPassWrdFocusListener() {
        binding.Mail.setOnFocusChangeListener { _, b ->
            if (!b) binding.mailContainer.error = valideMail()
        }
        binding.passWrd.setOnFocusChangeListener { _, b ->
            if (!b) binding.passWrdContainer.error = validePassWrd()
        }
    }


    private fun getUserData() {

        val dialog = AlertDialogClass.progressDialog(requireActivity())

        databaseReference = FirebaseDatabase.getInstance().getReference("Simple Users").child((FirebaseAuth
            .getInstance().currentUser)!!.uid)


        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                type = snapshot.child("type").value.toString()
                if (type == "particular"){
                    writeOnFile(type)
                    startActivity(Intent(activity, HomeScreen::class.java))
                }
                else{
                    writeOnFile("plumber")
                    startActivity(Intent(activity, PlumberProfile::class.java))
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        dialog.dismiss()
    }


    private fun writeOnFile(txt:String) {
        //File path: /data/data/com.astdev.indexeye/files/usrType.txt
        val fileName = "usrType.txt"
        context?.openFileOutput(fileName, MODE_PRIVATE).use { output ->
            output!!.write(txt.toByteArray())
        }

        /*var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput("position", MODE_PRIVATE)
            fos.write(java.lang.String.valueOf(position).toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }*/
    }
}