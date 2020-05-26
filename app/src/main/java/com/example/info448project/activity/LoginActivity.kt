package com.example.info448project.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.info448project.ProjectApplication
import com.example.info448project.R
import kotlinx.android.synthetic.main.log_in.*

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)
        title = getString(R.string.log_in)

        tvCreateAccount2.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Chance only comes once, you have to memorize your password!", Toast.LENGTH_SHORT).show()
        }

        btnLogin2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            val username = etUsername.text.toString()
//            val password0 = "abc"
//            (this.applicationContext as? ProjectApplication)?.userManager.let { userManager ->
//                userManager?.login(username, password0)
//            }
        }
    }


}