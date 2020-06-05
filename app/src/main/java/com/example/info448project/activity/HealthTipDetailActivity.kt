package com.example.info448project.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.example.info448project.R
import kotlinx.android.synthetic.main.activity_healthtip_detail.*

class HealthTipDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthtip_detail)
        setSupportActionBar(detail_toolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = HealthTipDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        HealthTipDetailFragment.ARG_ITEM_ID,
                        intent.getStringExtra(HealthTipDetailFragment.ARG_ITEM_ID)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.healthtip_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button.
                navigateUpTo(Intent(this, HealthTipListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
