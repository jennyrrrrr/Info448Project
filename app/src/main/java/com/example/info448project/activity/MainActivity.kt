package com.example.info448project.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import com.example.info448project.fragment.ForumFragment
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

        accountManager = (this.applicationContext as ProjectApp).accountManager
        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackEntries = supportFragmentManager.backStackEntryCount > 1
            supportActionBar?.setDisplayHomeAsUpEnabled(hasBackEntries)

             if (hasBackEntries) {
                 tabBar?.visibility = View.GONE
             } else {
                 tabBar?.visibility = View.VISIBLE
            }

        }
        btnData.setBackgroundResource(R.color.grey)
        accountManager.getUserInfo()

//        if (savedInstanceState != null) {
//            dataOutputFragment =
//                supportFragmentManager.getFragment(savedInstanceState, DataOutputFragment.TAG) as DataOutputFragment
//        }

        showData()
        btnNews.setOnClickListener { showTips() }
        btnProfile.setOnClickListener {
            btnProfile.setBackgroundResource(R.color.grey)
            btnData.setBackgroundColor(Color.TRANSPARENT)
            showProfile()
        }

        btnData.setOnClickListener {
            btnData.setBackgroundResource(R.color.grey)
            btnProfile.setBackgroundColor(Color.TRANSPARENT)
            showData()
        }

        btnForum.setOnClickListener{ showForum() }

        // enable my workmanager class
        workBackgroundManager = (this.applicationContext as ProjectApp).workBackgroundManager
        workBackgroundManager.startFetchForDaily()

        supportFragmentManager.addOnBackStackChangedListener {
            val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment.PTAG) as? ProfileFragment
            if (profileFragment != null && profileFragment.isVisible == true) {
                profileFragment.setInfo()
                title="Profile"
            }
        }
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
        val argumentBundle = Bundle()

        if (countryInfo == null) {
            dataManager = (application as ProjectApp).dataManager
            dataManager.getCountry() {
                if (it.equals(null)) {
                    Toast.makeText(this, "Failed Loading Data", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Loading Data", Toast.LENGTH_LONG).show()
                }
                val input = ArrayList(it)
                countryInfo = input
                argumentBundle.putParcelableArrayList(DataOutputFragment.COUNTRY_INFO, input)
                showDataFrag(argumentBundle)
            }
        } else {
            if (stateInfo == null) {
                argumentBundle.putParcelableArrayList(DataOutputFragment.COUNTRY_INFO, countryInfo)
            } else {
                argumentBundle.putParcelableArrayList(DataOutputFragment.STATE_INFO, stateInfo)
            }
            showDataFrag(argumentBundle)
        }
    }

    // enable data fragment here
    private fun showDataFrag(argumentBundle: Bundle) {
        val dataOutputFragment = DataOutputFragment();
        dataOutputFragment.arguments = argumentBundle

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
            .add(R.id.fragContainer, profileFragment, ProfileFragment.PTAG)
            .addToBackStack(ProfileFragment.PTAG)
            .commit()
    }

    // enable Forum fragment here
    private fun showForum() {
        supportFragmentManager.popBackStack()

        val forumFragment = ForumFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, forumFragment, ForumFragment.TAG)
            .addToBackStack(ForumFragment.TAG)
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
