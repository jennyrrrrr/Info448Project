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
import kotlinx.android.synthetic.main.sign_up.*

class CreateAccountActivity: AppCompatActivity() {
    private lateinit var accountManager: AccountManager
    private lateinit var auth: FirebaseAuth
//    private lateinit var alertDialog: AlertDialog

    private fun hideKeypad(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)
        title = getString(R.string.create_account)

        accountManager = (application as ProjectApp).accountManager
        auth = FirebaseAuth(FirebaseApp.getInstance())

//        frameLayout.setOnClickListener { hideKeypad(frameLayout) }
        etPassword.setOnClickListener{ hideKeypad(etPassword) }
        btnCreateAccount.setOnClickListener { createUser() }
        tvLogin1.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
//        alertDialog.setMessage("Creating User");
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Username or Password can not be empty!", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.i("jen", "createUserWithEmail:success", task.exception)
                    Toast.makeText(this, "Authentication success.", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    Log.i("jen", user.toString())
                    accountManager.changeUsername(user.toString().substringAfter("@"))
//                    accountManager.changeUsername(email.substring(0,8))
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.i("jen", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}