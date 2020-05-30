package com.example.info448project.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.info448project.ProjectApp
import com.example.info448project.R
import com.example.info448project.manager.AccountManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.log_in.*

class LoginActivity: AppCompatActivity() {
    private lateinit var accountManager: AccountManager
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)
        title = getString(R.string.log_in)
        progressBar = ProgressBar(this);

        accountManager = (application as ProjectApp).accountManager
        auth = FirebaseAuth(FirebaseApp.getInstance())

//        etUsername.setOnClickListener { closeKeyboard() }
        btnLogin2.setOnClickListener { loginUser() }
        tvCreateAccount2.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Chance only comes once, you have to memorize your password!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser() {
        val email = etUsername.text.toString().trim()
        val password = etPassword2.text.toString().trim()

        if (TextUtils.isEmpty(etUsername.text) || TextUtils.isEmpty(etPassword2.text)) {
            Toast.makeText(this, "Username or Password can not be empty!", Toast.LENGTH_SHORT).show()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.i("jen", "signInWithEmail:success")
                        Toast.makeText(baseContext, "Authentication success.", Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        accountManager.changeUsername("@ ${user.toString().substringAfter("@")}")
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.i("jen", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}