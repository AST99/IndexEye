package com.astdev.indexeye.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.astdev.indexeye.R
import com.astdev.indexeye.activities.HomeScreen
import com.astdev.indexeye.classes.AlertDialogClass
import com.astdev.indexeye.classes.CrudClass
import com.astdev.indexeye.databinding.FragmentUserProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserProfilFragment : Fragment() {

    private lateinit var binding: FragmentUserProfilBinding

    private lateinit var databaseReference: DatabaseReference

    private lateinit var userName: String
    private lateinit var phone: String
    private lateinit var mail: String
    private lateinit var deviceId: String
    private lateinit var passWrd: String

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentUserProfilBinding.inflate(layoutInflater)

        val user = FirebaseAuth.getInstance().currentUser
        databaseReference = FirebaseDatabase.getInstance().getReference("Simple Users").child((user)!!.uid)
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserProfilBinding.inflate(inflater, container, false)

        (requireActivity() as HomeScreen).setActionBarTitle("Mon Profil")

        val updateDialog = AlertDialogClass.editUserInfoDialog(requireActivity())

        getUserData()

        val btnEditCancel: Button = updateDialog.findViewById(R.id.btnAnnuler)
        btnEditCancel.setOnClickListener {
            updateDialog.dismiss()
        }

        val btnEdit: Button = updateDialog.findViewById(R.id.btnModifier)
        val btnSave: Button = updateDialog.findViewById(R.id.btnSave)
        val txtFieldLayout: View? = updateDialog.findViewById(R.id.txtFieldLayout)
        val txtViewInfo: TextView = updateDialog.findViewById(R.id.txtViewInfo)

        val editTxtName = updateDialog.findViewById<EditText>(R.id.EditName)
        val editMail = updateDialog.findViewById<EditText>(R.id.EditMail)
        val editPhone = updateDialog.findViewById<EditText>(R.id.EditPhone)

        btnEdit.setOnClickListener {
            if (txtFieldLayout != null) {
                txtViewInfo.visibility = View.GONE
                txtFieldLayout.visibility = View.VISIBLE
                btnEditCancel.visibility = View.GONE
                btnSave.visibility = View.VISIBLE
                btnEdit.visibility = View.GONE
            }
        }

        btnSave.setOnClickListener {
            txtViewInfo.visibility = View.VISIBLE
            txtFieldLayout!!.visibility = View.GONE
            btnEditCancel.visibility = View.VISIBLE
            btnSave.visibility = View.GONE
            btnEdit.visibility = View.VISIBLE



            val nameUpdated: String = editTxtName.text.toString().trim()
            val mailUpdated: String = editMail.text.toString().trim()
            val phoneUpdated: String = editPhone.text.toString().trim()

            CrudClass.changeMailAndPassWrd(requireActivity(),mail,passWrd, mailUpdated)

            if (CrudClass.upToDate(requireContext(), nameUpdated, mailUpdated, phoneUpdated) == true){
                binding.NameProfil.text = nameUpdated
                binding.MailProfil.text = mailUpdated
                binding.MobileProfil.text = phoneUpdated
            }
            updateDialog.dismiss()

        }

        binding.floatingActionButton.setOnClickListener {
            updateDialog.show()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getUserData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                userName = snapshot.child("name").value.toString()
                phone = snapshot.child("phone").value.toString()
                mail = snapshot.child("mail").value.toString()
                passWrd = snapshot.child("passWrd").value.toString()
                deviceId = snapshot.child("deviceId").value.toString()
                binding.NameProfil.text = userName
                binding.MailProfil.text = mail
                binding.MobileProfil.text = phone
                //binding.passWrdProfil.text = passWrd
                binding.DeviceId.text = deviceId
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }


}