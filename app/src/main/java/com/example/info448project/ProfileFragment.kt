package com.example.info448project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.profile_page.*

class ProfileFragment: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        btn_edit.setOnClickListener {
            val intent = Intent(this, EditProfileFragment::class.java)
            startActivity(intent)
        }
    }


}