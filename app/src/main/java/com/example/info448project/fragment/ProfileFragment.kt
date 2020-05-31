package com.example.info448project.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.info448project.ProjectApp
import com.example.info448project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.edit_profile.*
import kotlinx.android.synthetic.main.profile_page.*

class ProfileFragment: Fragment() {
    private lateinit var userId: String
    private lateinit var auth: FirebaseAuth

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
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(userId)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: ${snapshot.data}")
                tvNickname.text = snapshot.getString("nickname")
                tvMainUsername.text = snapshot.getString("email")
                tvMainBio.text = snapshot.getString("bio")
                tvLocation2.text = snapshot.getString("location")
            } else {
                Log.d(TAG, "Current data: null")
            }
        }

        btnEdit.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

            fragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, EditProfileFragment(), EditProfileFragment.TAG)
                .addToBackStack(EditProfileFragment.TAG)
                .commit()
        }

        tvNickname.text = (context?.applicationContext as? ProjectApp)?.accountManager.let { accountManager ->
            accountManager?.userNickname
        }
        tvMainUsername.text = (context?.applicationContext as? ProjectApp)?.accountManager.let { accountManager ->
            accountManager?.userName
        }
        tvMainBio.text = (context?.applicationContext as? ProjectApp)?.accountManager.let { accountManager ->
            accountManager?.bio
        }
        tvLocation2.text = (context?.applicationContext as? ProjectApp)?.accountManager.let { accountManager ->
            accountManager?.location
        }
        if (tvLocation2.text === "") {
            ivLocation.visibility = View.GONE
        } else {
            ivLocation.visibility = View.VISIBLE
        }
    }


}