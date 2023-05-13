package com.astdev.indexeye

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.astdev.indexeye.databinding.FragmentInscriptionBinding
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.Objects

private const val CAMERA_REQUEST_CODE = 101


class InscriptionFragment : Fragment() {


    private lateinit var codeScanner: CodeScanner

    private lateinit var names:String
    private lateinit var mail:String
    private lateinit var passWrd:String
    private lateinit var qrScannResult:String

    private lateinit var auth: FirebaseAuth
    private var thisContext: Context? = null

    private lateinit var binding: FragmentInscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding = FragmentInscriptionBinding.inflate(layoutInflater)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment

        binding = FragmentInscriptionBinding.inflate(inflater, container, false)

        thisContext = container!!.context


        setupPermission()

        nameFocusListener()
        mailFocusListener()
        passWrdFocusListener()
        confirmPassWrdFocusListener()

        binding.txtViewAccessAccount.setOnClickListener {
            val connexionFragment = ConnexionFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_1, connexionFragment)?.commit()
        }

        binding.btnNextAndSignIn.setOnClickListener {
            if (binding.Etape1.isVisible){
                if (TextUtils.isEmpty(binding.InscriptionNomPrenom.text) &&
                    TextUtils.isEmpty(binding.MailInscription.text)){
                    binding.NameInscriptionContainer.error = "Le nom et le prénom sont requis!"
                    binding.InscriptionMailContainer.error = "Votre e-mail est requis!"
                }
                else step1Done()
            }
            else if (binding.Etape2.isVisible){
                if (TextUtils.isEmpty(binding.passWrdInscription.text)) {
                    binding.passWrdIncrptionContainer.error = "Veuillez saisir votre mot de passe!"
                } else if (binding.passWrdInscription.length() < 5) {
                    binding.passWrdIncrptionContainer.error = "Votre mot de passe doit contenir " +
                            "au moins 5 caractères"
                } else if (TextUtils.isEmpty(binding.ConfirmPassWrdInscription.text)) {
                    binding.ConfirmPassWrdIncrptionContainer.error = "Confirmez votre mot de passe!"
                }
                else step2Done()
            }
        }

        binding.btnPrecedAndSignIn.setOnClickListener {
            if(binding.Etape2.isVisible){
                binding.Etape2.visibility = View.GONE
                binding.progressStep1.progress = 0
                binding.Etape1.visibility = View.VISIBLE
                binding.btnPrecedAndSignIn.visibility = View.GONE
            }
        }

        return binding.root
    }

    private fun step1Done(){
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

    private fun step2Done(){
        binding.progressStep2.progress=100
        binding.txtInscription.visibility = View.GONE
        binding.linearLayoutSteps.visibility = View.GONE
        binding.Etape2.visibility = View.GONE
        binding.Etape3.visibility = View.VISIBLE
        qrCodeScanner()
        binding.btnNextAndSignIn.visibility = View.GONE
        binding.txtViewAccessAccount.visibility = View.GONE
        binding.ConfirmPassWrdIncrptionContainer.error = null
        passWrd = Objects.requireNonNull(binding.passWrdInscription.text).toString().trim { it <= ' ' }
    }

    /******************************Inscription simple utilisateur */
    //=>m: mail, p: mot de passe, n: nom/prénom
    private fun createSimpleUserWithMail(m: String, p: String, n: String) {
        auth.createUserWithEmailAndPassword(m, p).addOnCompleteListener { it ->
            if (it.isSuccessful) {
                val user = UsersModels(m, n, p)
                FirebaseDatabase.getInstance().getReference("Users").child(qrScannResult)
                    .child("User info").setValue(user).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(activity, "Votre inscription a réussi !", Toast.LENGTH_LONG).show()
                            auth.currentUser
                        }
                        else Toast.makeText(activity, "Votre inscription a échoué ! " +
                                "Essayez à nouveau ou vérifiez votre connexion internet.", Toast.LENGTH_LONG).show()
                    }
            }
            else Toast.makeText(activity, "Votre inscription a échoué ! " +
                    "Essayez à nouveau ou vérifiez votre connexion internet.",
                Toast.LENGTH_LONG).show()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun qrCodeScanner(){
        codeScanner = CodeScanner(thisContext!!, binding.scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                activity?.runOnUiThread {
                    qrScannResult = it.text

                    binding.imgViewSucces.visibility = View.VISIBLE
                    binding.txtViewAccessAccount.visibility = View.GONE
                    binding.txtViewSucces.visibility = View.VISIBLE
                    binding.txtQr1.visibility = View.GONE
                    binding.txtQr2.visibility = View.GONE
                    binding.scannerView.visibility = View.GONE
                    binding.linearLayoutSteps.visibility = View.VISIBLE
                    binding.btnPrecedAndSignIn.visibility = View.GONE
                    binding.progressStep3.progress = 100
                    binding.btnNextAndSignIn.visibility = View.VISIBLE
                    binding.btnNextAndSignIn.text = "Connexion"

                    binding.btnNextAndSignIn.setOnClickListener {
                        createSimpleUserWithMail(mail, passWrd, names)
                        val connexionFragment = ConnexionFragment()
                        fragmentManager?.beginTransaction()?.replace(R.id.nav_1, connexionFragment)?.commit()
                        activity?.intent?.putExtra("e-mail", mail)
                        activity?.intent?.putExtra("pass", passWrd)
                    }
                }
            }

            errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
                activity?.runOnUiThread  {
                    Toast.makeText(activity, "Camera initialization error: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        codeScanner.startPreview()
    }


    private fun setupPermission(){
        val permission:Int = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST_CODE->{
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(activity,"L'accès à la camera est nécessaire", Toast.LENGTH_LONG).show()
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