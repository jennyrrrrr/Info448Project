package com.example.info448project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.info448project.R
import kotlinx.android.synthetic.main.profile_page.*

class ProfileFragment: Fragment() {

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.setTitle("Profile")
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

        btnEdit.setOnClickListener {
            val fragmentManager: FragmentManager = activity!!.supportFragmentManager

            fragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, EditProfileFragment(), EditProfileFragment.TAG)
                .addToBackStack(EditProfileFragment.TAG)
                .commit()
        }
//        val username = etUsername.text.toString()
//        (context?.applicationContext as? ProjectApplication)?.userManager.let { userManager ->
//            userManager?.changeUsername(username)
//        }

    }

}