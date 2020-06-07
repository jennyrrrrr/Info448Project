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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.create_account.*

class CreateAccountActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account)
        title = getString(R.string.create_account)
        progressBar.visibility = View.GONE

        auth = FirebaseAuth.getInstance()

        btnCreateAccount.setOnClickListener { createUser() }
        tvLogin1.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun createUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val nickname = etUserNickname.text.toString()
        val location = etLocation.text.toString().trim()
        firebaseFirestore = FirebaseFirestore.getInstance()

        progressBar.visibility = View.VISIBLE

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.i("jen", "createUserWithEmail:success", task.exception)
                        Toast.makeText(this, "Authentication success.", Toast.LENGTH_SHORT).show()
                        val userInfo = hashMapOf(
                            "nickname" to nickname,
                            "email" to email,
                            "location" to location,
                            "bio" to ""
                        )
                        userId = auth.currentUser!!.uid
                        firebaseFirestore.collection("users").document(userId)
                            .set(userInfo)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show()
                                Log.d("jen", "DocumentSnapshot added with ID: $userId")
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { e ->
                                Log.w("jen", "Error adding document", e)
                            }
                    } else {
                        Log.i("jen", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                    progressBar.visibility = View.GONE
                }
        }
    }
}