package com.astdev.indexeye.classes

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CrudClass {

    companion object{

        private val user = FirebaseAuth.getInstance().currentUser
        private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance()
            .getReference("Simple Users").child((user)!!.uid)

        @SuppressLint("SetTextI18n")
        fun upToDate(context: Context, name: String, mail: String, phone: String): Boolean? {
            val userMap = HashMap<String, Any>()
            var isUpDated: Boolean? = null
            userMap["name"] = name
            userMap["mail"] = mail
            userMap["phone"] = phone
            //userMap["passWrd"] = passWrd

            databaseReference.updateChildren(userMap).addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Informations modifiées avec succès!", Toast.LENGTH_LONG).show()
                    isUpDated = true
                }
                else {
                    isUpDated = false
                    Toast.makeText(context, "Une erreur s'est produite", Toast.LENGTH_LONG).show()
                }
            }
            return isUpDated
        }


        // Here we are going to change our email using firebase re-authentication
        fun changeMailAndPassWrd(context: Context, email: String, password: String, mailUpdated: String) {

            // Get auth credentials from the user for re-authentication
            val credential = EmailAuthProvider.getCredential(email, password) // Current Login Credentials

            // Prompt the user to re-provide their sign-in credentials
            user!!.reauthenticate(credential).addOnCompleteListener {
                Log.d("value", "User re-authenticated.")

                // Now change your email address \\
                //----------------Code for Changing Email Address----------\\
                user.updateEmail(mailUpdated).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Email Changed" + " Current Email is "
                                + mailUpdated, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

    }
}