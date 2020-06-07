package com.example.info448project.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.info448project.ProjectApp
import com.example.info448project.R
import com.example.info448project.activity.LoginActivity
import com.example.info448project.manager.AccountManager
import com.example.info448project.model.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import kotlinx.android.synthetic.main.edit_profile.*

class EditProfileFragment: Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var userId: String
    private lateinit var auth: FirebaseAuth
    private lateinit var accountManager: AccountManager

    companion object {
        val TAG: String = EditProfileFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = "Edit Profile"

        database = FirebaseDatabase.getInstance().reference
        accountManager = (context?.applicationContext as ProjectApp).accountManager

        etName.setText(accountManager.nickname)
        etBio.setText(accountManager.bio)
        etLocation.setText(accountManager.location)

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        btnComplete.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()

            auth = FirebaseAuth.getInstance()
            userId = auth.currentUser!!.uid
            val nickname = etName.text.toString()
            val bio = etBio.text.toString()
            val location = etLocation.text.toString()

            database.child("users").child("$userId").child("nickname").setValue("$nickname");
        }
    }
}