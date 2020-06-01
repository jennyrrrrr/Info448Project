package com.example.info448project.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.info448project.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.on_boarding.*

class OnBoardingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.on_boarding)
        title = "C19-SUPPORT"

        if (FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnGetStart.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


}