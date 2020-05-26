package com.example.info448project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.info448project.ProjectApp
import com.example.info448project.R
import kotlinx.android.synthetic.main.edit_profile.*
import kotlinx.android.synthetic.main.profile_page.*

class ProfileFragment: Fragment() {

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        activity?.setTitle("Profile")

        btnEdit.setOnClickListener {
            val fragmentManager: FragmentManager = activity!!.supportFragmentManager

            fragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, EditProfileFragment(), EditProfileFragment.TAG)
                .addToBackStack(EditProfileFragment.TAG)
                .commit()
        }

        tvNickname.text = (context?.applicationContext as? ProjectApp)?.accountManager.let { accountManager ->
            accountManager?.nickName
        }
        tvMainBio.text = (context?.applicationContext as? ProjectApp)?.accountManager.let { accountManager ->
            accountManager?.bio
        }
        tvLocation2.text = (context?.applicationContext as? ProjectApp)?.accountManager.let { accountManager ->
            accountManager?.location
        }

    }

}