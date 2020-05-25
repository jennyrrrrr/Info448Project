package com.example.info448project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EditProfile: AppCompatActivity() {

    companion object {
        const val OUT_USER = "OUT_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)
        title = "Edit Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        btnComplete.setOnClickListener { userInfo(userInfo) }
    }

    private fun userInfo(userInfo: UserInfo) {
//        val intent = Intent(this, ProfileFragment::class.java)
//        intent.putExtra(OUT_USER, userInfo)
//        startActivity(intent)
    }

    override fun onNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return super.onSupportNavigateUp()
    }


}