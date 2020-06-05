package com.example.info448project.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.info448project.ProjectApp
import com.example.info448project.R
import com.example.info448project.manager.AccountManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.log_in.*

class LoginActivity: AppCompatActivity() {
//    private lateinit var accountManager: AccountManager
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)
        title = getString(R.string.log_in)
        progressBarLogin.visibility = View.GONE

        auth = FirebaseAuth(FirebaseApp.getInstance())

        btnLoginLogin.setOnClickListener { loginUser() }
        tvCreateAccountLogin.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Chance only comes once, you have to memorize your password!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser() {
        val email = etEmailLogin.text.toString().trim()
        val password = etPasswordLogin.text.toString().trim()

        progressBarLogin.visibility = View.VISIBLE

        if (TextUtils.isEmpty(etEmailLogin.text) || TextUtils.isEmpty(etPasswordLogin.text)) {
            Toast.makeText(this, "Username or Password can not be empty!", Toast.LENGTH_SHORT).show()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.i("jen", "loginWithEmail:success", task.exception)
                        Toast.makeText(baseContext, "Authentication success.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.i("jen", "loginWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                     }
                    progressBarLogin.visibility = View.GONE
                }
        }
    }
}