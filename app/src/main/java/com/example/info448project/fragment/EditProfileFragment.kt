package com.example.info448project.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.info448project.ProjectApp
import com.example.info448project.R
import com.example.info448project.activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.edit_profile.*

class EditProfileFragment: Fragment() {

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
        activity?.setTitle("Edit Profile")

        etName.setText((context?.applicationContext as? ProjectApp)?.accountManager?.userNickname)
        etBio.setText((context?.applicationContext as? ProjectApp)?.accountManager?.bio)
        etLocation.setText((context?.applicationContext as? ProjectApp)?.accountManager?.location)

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        btnComplete.setOnClickListener {
            val fragmentManager: FragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()

            (context?.applicationContext as? ProjectApp)?.accountManager.let { accountManager ->
                accountManager?.changeNickname(etName.text.toString())
            }
            (context?.applicationContext as? ProjectApp)?.accountManager.let { accountManager ->
                accountManager?.changeBio(etBio.text.toString())
            }
            (context?.applicationContext as? ProjectApp)?.accountManager.let { accountManager ->
                accountManager?.changeLocation(etLocation.text.toString())
            }

        }

    }

}