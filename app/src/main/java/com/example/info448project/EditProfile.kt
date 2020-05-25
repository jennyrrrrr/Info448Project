package com.example.info448project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.edit_profile.*

class EditProfile: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)
        title = "Edit Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnComplete.setOnClickListener {
            userInfo()
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun userInfo() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        return super.onNavigateUp()
    }


}