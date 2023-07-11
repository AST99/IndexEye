package com.astdev.indexeye

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.astdev.indexeye.adapters.PlumberListAdapter
import com.astdev.indexeye.databinding.ActivityPlumberDetailBinding


class PlumberDetail : AppCompatActivity() {

    private lateinit var binding: ActivityPlumberDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlumberDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plumberDetail()

        binding.btnCall.setOnClickListener {
            callPlumber()
        }
    }


    private fun plumberDetail(){
        binding.plumberName.text = PlumberListAdapter.strShowName
        binding.Mobile.text = PlumberListAdapter.strShowPhone
        binding.Mail.text = PlumberListAdapter.strShowMail
    }

    private fun callPlumber(){
        val u: Uri = Uri.parse("tel:" + PlumberListAdapter.strShowPhone)
        try {
            startActivity(Intent(Intent.ACTION_DIAL, u))
        } catch (s: SecurityException) {
            Toast.makeText(this@PlumberDetail, "Une erreur s'est produite", Toast.LENGTH_LONG).show()
        }
    }
}