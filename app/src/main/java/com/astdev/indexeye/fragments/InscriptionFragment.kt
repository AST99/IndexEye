package com.astdev.indexeye.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.astdev.indexeye.R
import com.astdev.indexeye.databinding.FragmentInscriptionBinding
import com.astdev.indexeye.models.PlumberModels
import com.astdev.indexeye.models.UsersModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import java.util.Objects


class InscriptionFragment : Fragment() {

    private lateinit var names:String
    private lateinit var mail:String
    private lateinit var passWrd:String
    private lateinit var phone:String

    private lateinit var qrResult: String

    private lateinit var auth: FirebaseAuth
    private lateinit var pAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var simpleUserDatabaseReference: DatabaseReference
    private lateinit var plumberDatabaseReference: DatabaseReference

    private lateinit var userType: String

    private lateinit var binding: FragmentInscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        pAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        simpleUserDatabaseReference = database.getReference("Simple Users")
        plumberDatabaseReference = database.getReference("Plumbers")



        binding = FragmentInscriptionBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentInscriptionBinding.inflate(inflater, container, false)

        nameFocusListener()
        mailFocusListener()
        passWrdFocusListener()
        confirmPassWrdFocusListener()

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            if (binding.radiobtnSimpleUser.isChecked) {
                userType = "Simple Users"
                binding.btnNextAndSignIn.visibility = View.VISIBLE
                binding.btnPrecedAndSignIn.visibility = View.VISIBLE
                binding.btnPlumberInscription.visibility = View.GONE
            }
            if(binding.radioBtnPlumber.isChecked) {
                userType = "Plumbers"
            }
        }

        binding.txtViewAccessAccount.setOnClickListener {
            val connexionFragment = ConnexionFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_1, connexionFragment)?.commit()
        }

        binding.btnNextAndSignIn.setOnClickListener {
            if (binding.Etape1.isVisible){
                if (TextUtils.isEmpty(binding.InscriptionNomPrenom.text) && TextUtils.isEmpty(binding.MailInscription.text)){
                    binding.NameInscriptionContainer.error = "Le nom et le prénom sont requis!"
                    binding.InscriptionMailContainer.error = "Votre e-mail est requis!"
                }
                else stepOne()
            }
            else if (binding.Etape2.isVisible){
                if (TextUtils.isEmpty(binding.passWrdInscription.text)) {
                    binding.passWrdIncrptionContainer.error = "Veuillez saisir votre mot de passe!"
                } else if (binding.passWrdInscription.length() < 5) {
                    binding.passWrdIncrptionContainer.error = "Votre mot de passe doit contenir " +
                            "au moins 5 caractères"
                } else if (TextUtils.isEmpty(binding.ConfirmPassWrdInscription.text))
                    binding.ConfirmPassWrdIncrptionContainer.error = "Confirmez votre mot de passe!"
                else stepTwo()
            }
            else if (binding.Etape3.isVisible){
                if (TextUtils.isEmpty(binding.PhoneInscription.text))
                    binding.InscriptionPhoneContainer.error = "Votre numéro de téléphone est réquis!"
                else stepThree()
            }
        }

        binding.btnPrecedAndSignIn.setOnClickListener {
            binding.btnNextAndSignIn.visibility = View.VISIBLE
            binding.btnNextAndSignIn.text = "Suivant"
            binding.btnScan.visibility = View.GONE
            if(binding.Etape2.isVisible){
                binding.Etape2.visibility = View.GONE
                binding.progressStep1.progress = 0
                binding.Etape1.visibility = View.VISIBLE
                binding.btnPrecedAndSignIn.visibility = View.GONE
            }
            if (binding.Etape3.isVisible){
                binding.Etape3.visibility = View.GONE
                binding.progressStep2.progress = 0
                binding.Etape2.visibility = View.VISIBLE
                binding.btnPrecedAndSignIn.visibility = View.VISIBLE
            }
            if (binding.Etape4.isVisible){
                binding.Etape4.visibility = View.GONE
                binding.progressStep3.progress = 0
                binding.Etape3.visibility = View.VISIBLE
                binding.btnPrecedAndSignIn.visibility = View.VISIBLE
            }
        }
        return binding.root
    }

    private fun stepOne(){
        if (TextUtils.isEmpty(binding.InscriptionNomPrenom.text)) {
            binding.NameInscriptionContainer.error = "Le nom et le prénom sont requis!"
        } else if (TextUtils.isEmpty(binding.MailInscription.text)) {
            binding.InscriptionMailContainer.error = "Votre e-mail est requis!"
        }
        else{
            binding.progressStep1.progress=100
            binding.Etape1.visibility = View.GONE
            binding.Etape2.visibility = View.VISIBLE
            binding.btnPrecedAndSignIn.visibility = View.VISIBLE
            mail = Objects.requireNonNull(binding.MailInscription.text).toString().trim { it <= ' ' }
            names = Objects.requireNonNull(binding.InscriptionNomPrenom.text).toString().trim { it <= ' ' }
        }
    }

    private fun stepTwo(){
        if (TextUtils.isEmpty(binding.passWrdInscription.text))
            binding.passWrdIncrptionContainer.error = "Veuillez saisir un mot de passe!"
        else if (TextUtils.isEmpty(binding.ConfirmPassWrdInscription.text))
            binding.ConfirmPassWrdIncrptionContainer.error = "Confirmez le mot de passe"
        else{
            binding.progressStep2.progress=100
            binding.Etape2.visibility = View.GONE
            binding.Etape3.visibility = View.VISIBLE
            binding.ConfirmPassWrdIncrptionContainer.error = null
            passWrd = Objects.requireNonNull(binding.passWrdInscription.text).toString().trim { it <= ' ' }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun stepThree(){

        if (TextUtils.isEmpty(binding.PhoneInscription.text)){
            binding.InscriptionPhoneContainer.error = "Votre numéro de téléphone"
        }
        else{
            binding.progressStep3.progress=100
            binding.Etape3.visibility = View.GONE
            binding.Etape4.visibility = View.VISIBLE
            binding.InscriptionPhoneContainer.error = null
            phone = Objects.requireNonNull(binding.PhoneInscription.text).toString().trim { it <= ' ' }


            if (userType == "Plumbers"){
                binding.btnScan.visibility = View.GONE
                binding.txtViewAccessAccount.visibility = View.GONE
                binding.txtQr1.visibility = View.VISIBLE
                binding.txtQr1.text = "Vous allez vous inscrire en tant que plombier"
                binding.imgViewPlumber.visibility = View.VISIBLE
                binding.imgViewCodeBar.visibility = View.GONE
                binding.linearLayoutSteps.visibility = View.VISIBLE
                binding.btnPrecedAndSignIn.visibility = View.GONE
                binding.progressStep4.progress = 0
                binding.btnNextAndSignIn.visibility = View.VISIBLE
                binding.btnNextAndSignIn.text = "Valider"
                binding.btnPrecedAndSignIn.visibility = View.VISIBLE

                binding.btnNextAndSignIn.setOnClickListener {
                    createPlumberWithMail(mail, passWrd, names, phone)
                    //newCreateSimpleUserWithMail(mail, passWrd, names, phone, qrResult)
                }
            }

            if (userType == "Simple Users"){
                //Step4 pour lancer le scan
                binding.txtViewAccessAccount.visibility = View.GONE
                binding.txtQr1.visibility = View.VISIBLE
                binding.imgViewCodeBar.visibility = View.VISIBLE
                binding.imgViewPlumber.visibility = View.GONE
                binding.linearLayoutSteps.visibility = View.VISIBLE
                binding.btnPrecedAndSignIn.visibility = View.GONE
                binding.progressStep4.progress = 0
                binding.btnNextAndSignIn.visibility = View.GONE
                binding.btnPrecedAndSignIn.visibility = View.VISIBLE
                binding.btnScan.visibility = View.VISIBLE

                binding.btnScan.setOnClickListener {
                    barCodeReader()
                }
            }
        }
    }

    private fun barCodeReader() {
        val intentIntegrator: IntentIntegrator = IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setOrientationLocked(false)
        intentIntegrator.setPrompt("Scanner SVP")
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        intentIntegrator.initiateScan()
    }

    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            val contents = intentResult.contents
            if (contents != null) {
                qrResult = intentResult.contents
                Toast.makeText(requireContext(),qrResult,Toast.LENGTH_SHORT).show()

                binding.imgViewSucces.visibility = View.VISIBLE
                binding.imgViewCodeBar.visibility = View.GONE
                binding.txtViewAccessAccount.visibility = View.GONE
                binding.txtViewSucces.visibility = View.VISIBLE

                binding.txtViewSucces.text = "Scan réussi !"
                binding.txtQr1.visibility = View.GONE
                binding.linearLayoutSteps.visibility = View.VISIBLE
                binding.btnPrecedAndSignIn.visibility = View.GONE
                binding.progressStep4.progress = 100
                binding.btnNextAndSignIn.visibility = View.VISIBLE

                binding.btnNextAndSignIn.text = "Valider"
                binding.btnScan.visibility = View.GONE

                binding.btnNextAndSignIn.setOnClickListener {
                    createSimpleUserWithMail(mail, passWrd, names, phone, qrResult)
                }
            }
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    /******************************Inscription simple utilisateur */
    //=>m: mail, p: mot de passe, n: nom/prénom, tel: numéro de téléphone, deviceId
    @SuppressLint("SetTextI18n")
    private fun createSimpleUserWithMail(m: String, p: String, n: String, tel: String, deviceId: String) {

        auth.createUserWithEmailAndPassword(m, p).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = UsersModels(m, n, p, tel, deviceId)
                simpleUserDatabaseReference.child(Objects.requireNonNull<FirebaseUser?>(FirebaseAuth
                    .getInstance().currentUser).uid).setValue(user).addOnSuccessListener {
                    val connexionFragment = ConnexionFragment()
                    fragmentManager?.beginTransaction()?.replace(R.id.nav_1, connexionFragment)?.commit()
                    activity?.intent?.putExtra("e-mail", mail)
                    activity?.intent?.putExtra("pass", passWrd)

                    binding.imgViewSucces.visibility = View.VISIBLE
                    binding.txtViewSucces.text = "Votre inscription a réussi !"
                    Toast.makeText(activity, "Votre inscription a réussi !", Toast.LENGTH_LONG).show()
                    auth.currentUser
                }
            }
            else {
                val failFragment = InscriptionFail()
                fragmentManager?.beginTransaction()?.replace(R.id.nav_1, failFragment)?.commit()
            }
        }
    }


    /******************************Inscription plumber*/
    @SuppressLint("SetTextI18n")
    private fun createPlumberWithMail(m: String, p: String, n: String, tel: String) {

        pAuth.createUserWithEmailAndPassword(m, p).addOnCompleteListener {
            if (it.isSuccessful) {
                val plumber = UsersModels(m, n, p, tel)
                plumberDatabaseReference.child(Objects.requireNonNull<FirebaseUser?>(FirebaseAuth
                    .getInstance().currentUser).uid).setValue(plumber).addOnSuccessListener {
                    val connexionFragment = ConnexionFragment()
                    fragmentManager?.beginTransaction()?.replace(R.id.nav_1, connexionFragment)?.commit()
                    activity?.intent?.putExtra("e-mail", mail)
                    activity?.intent?.putExtra("pass", passWrd)

                    binding.imgViewSucces.visibility = View.VISIBLE
                    binding.txtViewSucces.text = "Votre inscription a réussi !"
                    Toast.makeText(activity, "Votre inscription a réussi !", Toast.LENGTH_LONG).show()
                    pAuth.currentUser
                }
            }
            else {
                val failFragment = InscriptionFail()
                fragmentManager?.beginTransaction()?.replace(R.id.nav_1, failFragment)?.commit()
            }
        }
    }

    private fun valideMail(): String? {
        if (TextUtils.isEmpty(binding.MailInscription.text))
            return "Votre e-mail est requis!"
        else if (!Patterns.EMAIL_ADDRESS.matcher(Objects.requireNonNull
                (binding.MailInscription.text).toString().trim { it <= ' ' }).matches())
            return "Veillez fournir un e-mail valide!"
        return null
    }

    private fun mailFocusListener() {
        binding.MailInscription.setOnFocusChangeListener { _, b ->
            if (!b) binding.InscriptionMailContainer.error = valideMail()
        }
    }

    private fun valideNomPrenom(): String? {
        return if (TextUtils.isEmpty(binding.InscriptionNomPrenom.text)) {
            "Le nom et le prénom sont requis!"
        } else null
    }

    private fun nameFocusListener() {
        binding.InscriptionNomPrenom.setOnFocusChangeListener { _, b ->
            if (!b) binding.NameInscriptionContainer.error = valideNomPrenom()
        }
    }

    private fun validePassWrd(): String? {
        if (TextUtils.isEmpty(binding.passWrdInscription.text)) {
            return "Veuillez saisir un mot de passe!"
        } else if (binding.passWrdInscription.length() < 5) {
            return "Votre mot de passe doit contenir au moins 5 caractères"
        }
        return null
    }

    private fun passWrdFocusListener() {
        binding.passWrdInscription.setOnFocusChangeListener { _, b ->
            if (!b) binding.passWrdIncrptionContainer.error = validePassWrd()
        }
    }

    private fun valideConfirmPassWrd(): String? {
        return if (TextUtils.isEmpty(binding.ConfirmPassWrdInscription.text)) "Confirmez le mot de passe" else null
    }

    private fun confirmPassWrdFocusListener() {
        binding.ConfirmPassWrdInscription.setOnFocusChangeListener { _, b ->
            if (!b) binding.ConfirmPassWrdIncrptionContainer.error = valideConfirmPassWrd()
        }
    }
}