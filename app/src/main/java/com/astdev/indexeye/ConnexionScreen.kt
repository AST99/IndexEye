package com.astdev.indexeye

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.astdev.indexeye.databinding.ConnexionScreenBinding
import java.util.*

class ConnexionScreen : AppCompatActivity() {

    private lateinit var binding: ConnexionScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ConnexionScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AccessAccount.setOnClickListener {
            mailAndPassWrdConnexion()
            startActivity(Intent(this, HomeScreen::class.java))
        }

        binding.CreateAccount.setOnClickListener {
            startActivity(Intent(this, InscriptionScreen::class.java)) }

        mailAndPassWrdFocusListener()
    }


    private fun mailAndPassWrdConnexion(){

        var mail: String = Objects.requireNonNull(binding.Mail.text.toString().trim())
        var passWrd: String =Objects.requireNonNull(binding.passWrd.text.toString().trim())


        if ( /*valideMail()!=null || */TextUtils.isEmpty(binding.Mail.text))
            binding.mailContainer.error = "Votre e-mail est requis!";
        else if ( /*validePassWrd()!=null*/TextUtils.isEmpty(binding.passWrd.text))
            binding.passWrdContainer.setError("Votre mot de passe est requis!");
        else if (binding.passWrd.text!!.length < 5)
            binding.passWrdContainer.error = "Votre mot de passe doit contenir au moins 5 caractères";
        else {
           /* progressDialog.setMessage("Connexion en cours...!");
            progressDialog.show();*/
            /*try {
                mAuth.signInWithEmailAndPassword(mail, passWrd).addOnSuccessListener(authResult -> {
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(ConnexionPage.this, "Vous êtes connecté en tant que plombier",
                        Toast.LENGTH_SHORT).show();
                    editTxtPassWrd.setText("");
                    editTxtmail.setText("");
                    finish();
                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(ConnexionPage.this, "La connexion a échouer ! Vérifiez vos" +
                            " informations ou créez un compte !", Toast.LENGTH_LONG).show();
                })
            } catch (Exception e) {
                e.printStackTrace();
            }*/
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
        binding.Mail.setOnFocusChangeListener { view, b ->
            if (!b) binding.mailContainer.error = valideMail()
        }
        binding.passWrd.setOnFocusChangeListener { view, b ->
            if (!b) binding.passWrdContainer.error = validePassWrd()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}