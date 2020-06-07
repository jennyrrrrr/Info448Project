package com.example.info448project.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.info448project.R
import com.example.info448project.model.CountryInfo
import com.example.info448project.model.StateInfo


class  DataOutputFragment: Fragment() {
    companion object {
        val TAG = StateCardFragment::class.java.simpleName

        const val STATE_INFO = "state list"

        const val COUNTRY_INFO = "country list"
    }

    private var stateInfo: StateInfo? = null
    private var countryInfo: CountryInfo? = null
    lateinit var tvPositive: TextView
    lateinit var tvRecovered: TextView
    lateinit var tvHospitalized: TextView
    lateinit var tvDeath: TextView
    lateinit var tvNewPositive: TextView
    lateinit var tvNewDeath: TextView

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val bundle = arguments

        if (bundle?.containsKey(COUNTRY_INFO)!!) {
            countryInfo = this.arguments?.getParcelable<CountryInfo>(COUNTRY_INFO)!!
            Toast.makeText(context, countryInfo.toString(), Toast.LENGTH_LONG).show()
        } else if (bundle.containsKey(STATE_INFO)) {
            stateInfo = this.arguments?.getParcelable<StateInfo>(STATE_INFO)!!
            Toast.makeText(context, stateInfo.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.already_exist_location, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle("COVID Stats")

        val stateCardFragment = StateCardFragment()

        val txLocation = view.findViewById<Button>(R.id.txLocation)
        tvPositive = view.findViewById(R.id.tvPositive)
        tvRecovered = view.findViewById(R.id.tvRecovered)
        tvHospitalized = view.findViewById(R.id.tvHospitalized)
        tvDeath = view.findViewById(R.id.tvDeath)
        tvNewPositive = view.findViewById(R.id.tvNewPositive)
        tvNewDeath = view.findViewById(R.id.tvNewDeath)

        if (!(stateInfo == null)) {
            tvPositive.text = stateInfo?.positive.toString()
            tvRecovered.text = stateInfo?.recovered.toString()
            tvHospitalized.text = stateInfo?.hospitalized.toString()
            tvDeath.text = stateInfo?.death.toString()
            tvNewPositive.text = stateInfo?.positiveIncrease.toString()
            tvNewDeath.text = stateInfo?.deathIncrease.toString()
        } else {
            tvPositive.text = countryInfo?.positive.toString()
            tvRecovered.text = countryInfo?.recovered.toString()
            tvHospitalized.text = countryInfo?.hospitalized.toString()
            tvDeath.text = countryInfo?.death.toString()
            tvNewPositive.text = countryInfo?.positiveIncrease.toString()
            tvNewDeath.text = countryInfo?.deathIncrease.toString()
        }


        txLocation.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
//                .replace(R.id.fragContainer, stateCardFragment, stateCardFragment.tag)
                .add(R.id.fragContainer, stateCardFragment, stateCardFragment.tag)
//                .addToBackStack(stateCardFragment.tag)
                .commit()
        }

    }
}