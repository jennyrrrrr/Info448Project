package com.example.info448project.fragment

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
import com.example.info448project.manager.AccountManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.profile_page.*

class ProfileFragment: Fragment() {
    private lateinit var userId: String
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var accountManager: AccountManager

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.profile_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Profile"

        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser!!.uid
        firebaseFirestore = FirebaseFirestore.getInstance()
        accountManager = (context?.applicationContext as ProjectApp).accountManager

//        accountManager.getUserInfo()

        tvNickname.text = accountManager.nickname
        Toast.makeText(context, "${accountManager.nickname}", Toast.LENGTH_SHORT).show()
        tvMainUsername.text = accountManager.email
        tvMainBio.text = accountManager.bio
        tvLocation2.text = accountManager.location

        btnEdit.setOnClickListener {showEditProfileFragment() }

        if (tvLocation2.text === "") {
            ivLocation.visibility = View.GONE
        } else {
            ivLocation.visibility = View.VISIBLE
        }
    }

    private fun showEditProfileFragment() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        fragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, EditProfileFragment(), EditProfileFragment.TAG)
            .addToBackStack(EditProfileFragment.TAG)
            .commit()
    }
}