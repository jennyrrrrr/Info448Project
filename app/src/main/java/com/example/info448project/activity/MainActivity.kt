package com.example.info448project.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentManager
import com.example.info448project.ProjectApp
import com.example.info448project.fragment.ProfileFragment
import com.example.info448project.R
import com.example.info448project.fragment.DataOutputFragment
import com.example.info448project.fragment.StateCardFragment
import com.example.info448project.manager.AccountManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private lateinit var accountManager: AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "C19-SUPPORT"

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackEntries = supportFragmentManager.backStackEntryCount > 1
            supportActionBar?.setDisplayHomeAsUpEnabled( hasBackEntries)

            tabBar?.visibility = if (hasBackEntries) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        accountManager = (this.applicationContext as ProjectApp).accountManager
        accountManager.getUserInfo()
        showData()
        btnNews.setOnClickListener { showTips() }
        btnProfile.setOnClickListener { showProfile() }
        btnData.setOnClickListener { showData() }
    }

    private fun getDataOutputFragment() =
        supportFragmentManager.findFragmentByTag(DataOutputFragment.TAG) as? DataOutputFragment

    private fun showTips() {
        val intent = Intent(this, HealthTipListActivity::class.java)
        startActivity(intent)
    }

    // enable data fragment here
    private fun showData() {
        supportFragmentManager.popBackStack()

        val dataOutputFragment = DataOutputFragment();
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, dataOutputFragment, dataOutputFragment.tag)
            .addToBackStack(DataOutputFragment.TAG)
            .commit()
    }

    // enable profile fragment here
    private fun showProfile() {
        supportFragmentManager.popBackStack()

        val profileFragment = ProfileFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, profileFragment, ProfileFragment.TAG)
            .addToBackStack(ProfileFragment.TAG)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onSupportNavigateUp()
    }

}
