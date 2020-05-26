package com.example.info448project.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.info448project.ProjectApp
import com.example.info448project.R
import com.example.info448project.manager.AccountManager
import kotlinx.android.synthetic.main.log_in.*
import kotlinx.android.synthetic.main.profile_page.*

class LoginActivity: AppCompatActivity() {
    private lateinit var accountManager: AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)
        title = getString(R.string.log_in)

        accountManager = (application as ProjectApp).accountManager

        etUsername.setOnClickListener {
            closeKeyboard()
        }

        btnLogin2.setOnClickListener {
            if (TextUtils.isEmpty(etUsername.text)) {
                Toast.makeText(this, "Username can not be empty!", Toast.LENGTH_SHORT).show()
            } else {
                accountManager.changeUsername(etUsername.text.toString())
                closeKeyboard()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        tvCreateAccount2.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Chance only comes once, you have to memorize your password!", Toast.LENGTH_SHORT).show()
        }

//        btnLogin2.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            val username = etUsername.text.toString()
//            val password0 = "abc"
//            (this.applicationContext as? ProjectApplication)?.userManager.let { userManager ->
//                userManager?.login(username, password0)
//            }
//        }
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}