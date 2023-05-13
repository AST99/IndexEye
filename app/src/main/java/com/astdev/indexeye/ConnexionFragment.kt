package com.astdev.indexeye

import android.content.Context
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
import com.astdev.indexeye.databinding.FragmentConnexionBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Objects

class ConnexionFragment : Fragment() {

    private lateinit var binding: FragmentConnexionBinding

    private lateinit var auth: FirebaseAuth

    private var thisContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding = FragmentConnexionBinding.inflate(layoutInflater)

        mailAndPassWrdFocusListener()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment

        binding = FragmentConnexionBinding.inflate(inflater, container, false)

        thisContext = container!!.context

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

        val dialog = AlertDialogClass.progressDialog(thisContext)

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
                        startActivity(Intent(activity, HomeScreen::class.java))
                        dialog.dismiss()
                        binding.Mail.text!!.clear()
                        binding.passWrd.text!!.clear()

                    } else {
                        Toast.makeText(thisContext, "Adresse mail ou " +
                                "mot de passe incorrect", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
            } else
                Toast.makeText(thisContext, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun valideMail(): String? {
        if (TextUtils.isEmpty(binding.Mail.text))
            return "Votre e-mail est requis!"
        else if (!Patterns.EMAIL_ADDRESS.matcher(
                Objects.requireNonNull<Editable>(binding.Mail.text)
                    .toString().trim { it <= ' ' }).matches()) {
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

}