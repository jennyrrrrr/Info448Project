package com.example.info448project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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

//        btnComplete.setOnClickListener {
//            userInfo()
//        }

        btnLogout.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        btnComplete.setOnClickListener {
            val fragmentManager: FragmentManager = activity!!.supportFragmentManager

            fragmentManager.popBackStack()
        }

    }

//    private fun userInfo() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//    }

}