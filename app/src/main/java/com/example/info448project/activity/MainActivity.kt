package com.example.info448project.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
<<<<<<< HEAD
=======
import android.widget.Toast
>>>>>>> 24e138ef45d61f4d602ddacbda5e72da7f37572e
import com.example.info448project.ProjectApp
import com.example.info448project.fragment.ProfileFragment
import com.example.info448project.R
import com.example.info448project.fragment.DataOutputFragment
<<<<<<< HEAD
=======
import com.example.info448project.fragment.OnStateSelectListener
>>>>>>> 24e138ef45d61f4d602ddacbda5e72da7f37572e
import com.example.info448project.manager.AccountManager
import com.example.info448project.manager.DataManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnStateSelectListener {
    private lateinit var accountManager: AccountManager
    private lateinit var dataManager: DataManager

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
        btnProfile.setOnClickListener { showProfile() }
        btnData.setOnClickListener { showData() }
    }

    private fun getDataOutputFragment() =
        supportFragmentManager.findFragmentByTag(DataOutputFragment.TAG) as? DataOutputFragment

    // enable data fragment here
    private fun showData() {
        supportFragmentManager.popBackStack()

        val argumentBundle = Bundle()
        dataManager = (application as ProjectApp).dataManager
        dataManager.getCountry() {
            if (it.equals(null)) {
                Toast.makeText(this, "problem occurred", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Loading Data", Toast.LENGTH_LONG).show()
            }
            argumentBundle.putParcelable(DataOutputFragment.COUNTRY_INFO, it)
            val dataOutputFragment = DataOutputFragment();
            dataOutputFragment.arguments = argumentBundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, dataOutputFragment, dataOutputFragment.tag)
                .addToBackStack(DataOutputFragment.TAG)
                .commit()
        }
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

    override fun onStateClick(state: String) {
        val dataOutputFragment = DataOutputFragment();
        val argumentBundle = Bundle()
        dataManager = (application as ProjectApp).dataManager
        dataManager.getOneState(state) {

            if (it.equals(null)) {
                Toast.makeText(this, "problem occurred", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Loading Data", Toast.LENGTH_LONG).show()
            }
            argumentBundle.putParcelable(DataOutputFragment.STATE_INFO, it)
            dataOutputFragment.arguments = argumentBundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragContainer, dataOutputFragment, dataOutputFragment.tag)
                .commit()
        }
    }

}
