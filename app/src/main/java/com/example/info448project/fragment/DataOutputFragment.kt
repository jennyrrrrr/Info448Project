package com.example.info448project.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class  DataOutputFragment: Fragment() {
    companion object {
        val TAG = StateCardFragment::class.java.simpleName

        const val STATE_INFO = "state list"

        const val COUNTRY_INFO = "country list"
    }

    private var stateInfo: ArrayList<StateInfo>? = null
    private var countryInfo: ArrayList<CountryInfo>? = null
    lateinit var tvPositive: TextView
    lateinit var tvRecovered: TextView
    lateinit var tvHospitalized: TextView
    lateinit var tvDeath: TextView
    lateinit var tvNewPositive: TextView
    lateinit var tvNewDeath: TextView
    lateinit var chart: LineChart
    lateinit var tvIntroduction: TextView
    lateinit var txChoose: TextView
    private var entries: ArrayList<Entry> = arrayListOf()

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val bundle = arguments

        if (bundle?.containsKey(COUNTRY_INFO)!!) {
            countryInfo = this.arguments?.getParcelableArrayList<CountryInfo>(COUNTRY_INFO)!!
            //Toast.makeText(context, countryInfo.toString(), Toast.LENGTH_LONG).show()
        } else if (bundle.containsKey(STATE_INFO)) {
            stateInfo = this.arguments?.getParcelableArrayList<StateInfo>(STATE_INFO)!!
            //Toast.makeText(context, stateInfo.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            if (savedInstanceState?.containsKey(COUNTRY_INFO)!!) {
                countryInfo = savedInstanceState.getParcelableArrayList(COUNTRY_INFO)
                Toast.makeText(context, countryInfo.toString(), Toast.LENGTH_LONG).show()
            } else if (savedInstanceState.containsKey(STATE_INFO)) {
                stateInfo = savedInstanceState.getParcelableArrayList(STATE_INFO)
                Toast.makeText(context, stateInfo.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.already_exist_location, container, false)
    }


    @SuppressLint("SetTextI18n")
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
        tvIntroduction = view.findViewById(R.id.tvIntroduction)
        txChoose = view.findViewById(R.id.txChoose)

        if (!(stateInfo == null)) {
            txChoose.text = "The Data Quality Grade in " + stateInfo?.first()?.state + " is " + stateInfo?.first()?.dataQualityGrade
            tvIntroduction.text = "Here is Covid-19 Data in the state of " + stateInfo?.first()?.state
            tvPositive.text = stateInfo?.first()?.positive.toString()
            tvRecovered.text = stateInfo?.first()?.recovered.toString()
            tvHospitalized.text = stateInfo?.first()?.hospitalized.toString()
            tvDeath.text = stateInfo?.first()?.death.toString()
            tvNewPositive.text = stateInfo?.first()?.positiveIncrease.toString()
            tvNewDeath.text = stateInfo?.first()?.deathIncrease.toString()

            // for graph
            chart = view.findViewById(R.id.chart)

            var num = 0

            val reverse: MutableList<StateInfo> = stateInfo?.subList(0, 10)?.reversed()?.toMutableList()!!

            for (item in reverse) {
                val entry = Entry(num.toFloat(), item.positive.toFloat())
                num++
                entries.add(entry)
            }

            val dataSet = LineDataSet(entries, "Date")
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            val lineData = LineData(dataSet)
            chart.setData(lineData)
            chart.notifyDataSetChanged()
        } else {
            // for table
            txChoose.text = ""
            tvIntroduction.text = "Here is Covid-19 Data in the United States"
            tvPositive.text = countryInfo?.first()?.positive.toString()
            tvRecovered.text = countryInfo?.first()?.recovered.toString()
            tvHospitalized.text = countryInfo?.first()?.hospitalized.toString()
            tvDeath.text = countryInfo?.first()?.death.toString()
            tvNewPositive.text = countryInfo?.first()?.positiveIncrease.toString()
            tvNewDeath.text = countryInfo?.first()?.deathIncrease.toString()

            // for graph
            chart = view.findViewById(R.id.chart)

            var num = 0

            val reverse: MutableList<CountryInfo> = countryInfo?.reversed()?.toMutableList()!!

            for (item in reverse) {
                val entry = Entry(num.toFloat(), item.positive.toFloat())
                num++
                entries.add(entry)
            }

            val dataSet = LineDataSet(entries, "Date")
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            val lineData = LineData(dataSet)
            chart.setData(lineData)
            chart.invalidate()
        }


        txLocation.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
//                .replace(R.id.fragContainer, stateCardFragment, stateCardFragment.tag)
                .add(R.id.fragContainer, stateCardFragment, stateCardFragment.tag)
                .addToBackStack(stateCardFragment.tag)
                .commit()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = Bundle()

        if (stateInfo != null) {
            bundle.putParcelableArrayList(STATE_INFO, stateInfo)
            onSaveInstanceState(bundle)
        } else {
            bundle.putParcelableArrayList(COUNTRY_INFO, countryInfo)
            onSaveInstanceState(bundle)
        }
    }
}