package com.astdev.indexeye

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.astdev.indexeye.databinding.InscriptionScreenBinding
import com.budiyev.android.codescanner.*
import java.util.*


private const val CAMERA_REQUEST_CODE = 101

class InscriptionScreen : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    private lateinit var binding: InscriptionScreenBinding
    private lateinit var names:String
    private lateinit var mail:String
    private lateinit var passWrd:String

    private val usersModels = UsersModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = InscriptionScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPermission()

        nameFocusListener()
        mailFocusListener()
        passWrdFocusListener()
        confirmPassWrdFocusListener()

        binding.txtViewAccessAccount.setOnClickListener { startActivity(Intent(this, ConnexionScreen::class.java)) }

        binding.btnNextAndSignIn.setOnClickListener {
            if (binding.Etape1.isVisible){
                if (TextUtils.isEmpty(binding.InscriptionNomPrenom.text) && TextUtils.isEmpty(binding.MailInscription.text)){
                    binding.NameInscriptionContainer.error = "Le nom et le prénom sont requis!"
                    binding.InscriptionMailContainer.error = "Votre e-mail est requis!"
                }
                else step1Done()
            }
            else if (binding.Etape2.isVisible){
                if (TextUtils.isEmpty(binding.passWrdInscription.text)) {
                    binding.passWrdIncrptionContainer.error = "Veuillez saisir votre mot de passe!"
                } else if (binding.passWrdInscription.length() < 5) {
                    binding.passWrdIncrptionContainer.error = "Votre mot de passe doit contenir au moins 5 caractères"
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

    }

    private fun step1Done(){
        if (TextUtils.isEmpty(binding.InscriptionNomPrenom.text)) {
            binding.NameInscriptionContainer.error = "Le nom et le prénom sont requis!"
        } else if (TextUtils.isEmpty(binding.MailInscription.getText())) {
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

    @SuppressLint("SetTextI18n")
    private fun qrCodeScanner(){
        codeScanner = CodeScanner(this, binding.scannerView)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread{
                    Toast.makeText(this@InscriptionScreen, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
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

                        /*val usr = UsersModels()
                        usr.mail = mail
                        usr.passWrd = passWrd*/

                        val intent=Intent(this@InscriptionScreen, ConnexionScreen::class.java)
                        intent.putExtra("e-mail", mail)
                        intent.putExtra("pass", passWrd)
                        startActivity(intent)

                        //Toast.makeText(this@InscriptionScreen, ""+usr.mail, Toast.LENGTH_LONG).show()

                        //startActivity(Intent(this@InscriptionScreen, ConnexionScreen::class.java))
                    }
                }
            }

            errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
                runOnUiThread {
                    Toast.makeText(this@InscriptionScreen, "Camera initialization error: ${it.message}",
                        Toast.LENGTH_LONG).show()
                }
            }

            codeScanner.startPreview()

            /*binding.scannerView.setOnClickListener {
                codeScanner.startPreview()
            }*/
        }
    }


    private fun setupPermission(){
        val permission:Int = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST_CODE->{
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this@InscriptionScreen,"L'accès à la camera est nécessaire", Toast.LENGTH_LONG).show()
                }
                else{
                    //
                }
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
            return "Veuillez saisir votre mot de passe!"
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
        return if (TextUtils.isEmpty(binding.ConfirmPassWrdInscription.text)) "Confirmez votre mot de passe" else null
    }

    private fun confirmPassWrdFocusListener() {
        binding.ConfirmPassWrdInscription.setOnFocusChangeListener { _, b ->
            if (!b) binding.ConfirmPassWrdIncrptionContainer.error = valideConfirmPassWrd()
        }
    }

    /*override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }*/

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}