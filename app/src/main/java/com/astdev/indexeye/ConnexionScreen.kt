package com.astdev.indexeye

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.astdev.indexeye.databinding.ConnexionScreenBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Objects

class ConnexionScreen : AppCompatActivity() {

    private lateinit var binding: ConnexionScreenBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ConnexionScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.AccessAccount.setOnClickListener {
            mailAndPassWrdConnexion()
        }

        binding.CreateAccount.setOnClickListener {
            startActivity(Intent(this, InscriptionScreen::class.java)) }

        val inscriptionMail: String? = intent.getStringExtra("e-mail")
        val inscriptionPassWrd: String?= intent.getStringExtra("pass")
        binding.Mail.setText(inscriptionMail)
        binding.passWrd.setText(inscriptionPassWrd)

        mailAndPassWrdFocusListener()
    }

    private fun mailAndPassWrdConnexion(){

        val mail: String = binding.Mail.text.toString().trim()
        val passWrd: String = binding.passWrd.text.toString().trim()

        if ( /*valideMail()!=null || */TextUtils.isEmpty(binding.Mail.text))
            binding.mailContainer.error = "Votre e-mail est requis!"
        else if ( /*validePassWrd()!=null*/TextUtils.isEmpty(binding.passWrd.text))
            binding.passWrdContainer.error = "Votre mot de passe est requis!"
        else if (binding.passWrd.text!!.length < 5)
            binding.passWrdContainer.error = "Votre mot de passe doit contenir au moins 5 caractères"
        else {
            if (mail.isNotEmpty() && passWrd.isNotEmpty()){
                auth.signInWithEmailAndPassword(mail, passWrd).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, HomeScreen::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Adresse mail ou " +
                                "mot de passe incorrecte", Toast.LENGTH_SHORT).show()
                    }
                }
            } else
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
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