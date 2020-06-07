package com.example.info448project.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.info448project.ProjectApp
import com.example.info448project.fragment.ProfileFragment
import com.example.info448project.R
import com.example.info448project.fragment.DataOutputFragment
import com.example.info448project.fragment.OnStateSelectListener
import com.example.info448project.fragment.StateCardFragment
import com.example.info448project.manager.AccountManager
import com.example.info448project.manager.DataManager
import com.example.info448project.manager.WorkBackgroundManager
import com.example.info448project.model.CountryInfo
import com.example.info448project.model.StateInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnStateSelectListener {
    private lateinit var accountManager: AccountManager
    private lateinit var dataManager: DataManager
    private lateinit var workBackgroundManager: WorkBackgroundManager
    private var dataOutputFragment = DataOutputFragment()
    private var stateInfo: ArrayList<StateInfo>? = null
    private var countryInfo: ArrayList<CountryInfo>? = null

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
        if (savedInstanceState != null) {

            dataOutputFragment =
                supportFragmentManager.getFragment(savedInstanceState, DataOutputFragment.TAG) as DataOutputFragment
        }
        showData()
        btnProfile.setOnClickListener { showProfile() }
        btnData.setOnClickListener { showData() }

        // enable my workmanager class
        workBackgroundManager = (this.applicationContext as ProjectApp).workBackgroundManager
        workBackgroundManager.startFetchForDaily()
    }

    private fun getDataOutputFragment() =
        supportFragmentManager.findFragmentByTag(DataOutputFragment.TAG) as? DataOutputFragment

    // enable data fragment here
    private fun showData() {
        supportFragmentManager.popBackStack()

        val argumentBundle = Bundle()
        if (countryInfo == null) {
            Toast.makeText(this, "I am here 2", Toast.LENGTH_SHORT).show()
            Log.i("shengtianyi2", "I am here 2")
            dataManager = (application as ProjectApp).dataManager
            dataManager.getCountry() {
                if (it.equals(null)) {
                    Toast.makeText(this, "problem occurred", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Loading Data", Toast.LENGTH_LONG).show()
                }
                val input = ArrayList(it)
                countryInfo = input
                argumentBundle.putParcelableArrayList(DataOutputFragment.COUNTRY_INFO, input)

                val dataOutputFragment = DataOutputFragment();
                dataOutputFragment.arguments = argumentBundle

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, dataOutputFragment, dataOutputFragment.tag)
                    .addToBackStack(DataOutputFragment.TAG)
                    .commit()
            }
        } else {
            Toast.makeText(this, "I am here", Toast.LENGTH_SHORT).show()
            Log.i("shengtianyi2", "I am here1")
            if (stateInfo == null) {
                argumentBundle.putParcelableArrayList(DataOutputFragment.COUNTRY_INFO, countryInfo)

                val dataOutputFragment = DataOutputFragment();
                dataOutputFragment.arguments = argumentBundle

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, dataOutputFragment, dataOutputFragment.tag)
                    .addToBackStack(DataOutputFragment.TAG)
                    .commit()
            } else {
                argumentBundle.putParcelableArrayList(DataOutputFragment.STATE_INFO, stateInfo)

                val dataOutputFragment = DataOutputFragment();
                dataOutputFragment.arguments = argumentBundle

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, dataOutputFragment, dataOutputFragment.tag)
                    .addToBackStack(DataOutputFragment.TAG)
                    .commit()
            }
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
        supportFragmentManager.popBackStack()

        val dataOutputFragment = DataOutputFragment();
        val argumentBundle = Bundle()
        dataManager = (application as ProjectApp).dataManager
        dataManager.getOneState(state) {

            if (it.equals(null)) {
                Toast.makeText(this, "problem occurred", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Loading Data", Toast.LENGTH_LONG).show()
            }
            val input = ArrayList(it)
            stateInfo = input
            argumentBundle.putParcelableArrayList(DataOutputFragment.STATE_INFO, input)
            dataOutputFragment.arguments = argumentBundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragContainer, dataOutputFragment, dataOutputFragment.tag)
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        supportFragmentManager.putFragment(outState, DataOutputFragment.TAG, dataOutputFragment)
    }
}
