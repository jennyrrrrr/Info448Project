package com.example.info448project.manager

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.info448project.model.CountryInfo
import com.example.info448project.model.StateInfo
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class DataManager(context: Context) {

    private var queue: RequestQueue = Volley.newRequestQueue(context)
    lateinit var curState: MutableList<StateInfo>
    lateinit var stateURL: String
    private val curContext = context
    lateinit var curCountryInfo: MutableList<CountryInfo>



    fun getOneState(state: String, onStateInfoReady: (MutableList<StateInfo>) -> Unit) {
        val stateName = convert(state)
        stateURL = "https://covidtracking.com/api/v1/states/$stateName/daily.json"

        val request = StringRequest(
            Request.Method.GET, stateURL,
            { response ->
                // success
                Log.i("shengtianyi", response.toString())
                val gson = Gson()
                curState = gson.fromJson(response, Array<StateInfo>::class.java).toMutableList()
                onStateInfoReady(curState)
            },
            {
                //error
                Toast.makeText(curContext, "Some Errors when Get information from Json", Toast.LENGTH_SHORT).show()
            })
        queue.add(request)
    }

    fun getCountry(onCountryInfoReady: (MutableList<CountryInfo>) -> Unit) {
        val stateURL = "https://covidtracking.com/api/v1/us/daily.json"

        val request = StringRequest(
            Request.Method.GET, stateURL,
            { response ->
                // success
                Log.i("shengtianyi", response.toString())
                val gson = Gson()
                curCountryInfo = gson.fromJson(response, Array<CountryInfo>::class.java).toMutableList()
                onCountryInfoReady(curCountryInfo)
            },
            {
                //error
                Toast.makeText(curContext, "Some Errors when Get information from Json", Toast.LENGTH_SHORT).show()
            })
        queue.add(request)
    }

    private fun convert(state: String): String? {
        val states: MutableMap<String, String> = HashMap()
        states["Alabama"] = "AL"
        states["Alaska"] = "AK"
        states["Alberta"] = "AB"
        states["American Samoa"] = "AS"
        states["Arizona"] = "AZ"
        states["Arkansas"] = "AR"
        states["Armed Forces (AE)"] = "AE"
        states["Armed Forces Americas"] = "AA"
        states["Armed Forces Pacific"] = "AP"
        states["British Columbia"] = "BC"
        states["California"] = "CA"
        states["Colorado"] = "CO"
        states["Connecticut"] = "CT"
        states["Delaware"] = "DE"
        states["District Of Columbia"] = "DC"
        states["Florida"] = "FL"
        states["Georgia"] = "GA"
        states["Guam"] = "GU"
        states["Hawaii"] = "HI"
        states["Idaho"] = "ID"
        states["Illinois"] = "IL"
        states["Indiana"] = "IN"
        states["Iowa"] = "IA"
        states["Kansas"] = "KS"
        states["Kentucky"] = "KY"
        states["Louisiana"] = "LA"
        states["Maine"] = "ME"
        states["Manitoba"] = "MB"
        states["Maryland"] = "MD"
        states["Massachusetts"] = "MA"
        states["Michigan"] = "MI"
        states["Minnesota"] = "MN"
        states["Mississippi"] = "MS"
        states["Missouri"] = "MO"
        states["Montana"] = "MT"
        states["Nebraska"] = "NE"
        states["Nevada"] = "NV"
        states["New Brunswick"] = "NB"
        states["New Hampshire"] = "NH"
        states["New Jersey"] = "NJ"
        states["New Mexico"] = "NM"
        states["New York"] = "NY"
        states["Newfoundland"] = "NF"
        states["North Carolina"] = "NC"
        states["North Dakota"] = "ND"
        states["Northwest Territories"] = "NT"
        states["Nova Scotia"] = "NS"
        states["Nunavut"] = "NU"
        states["Ohio"] = "OH"
        states["Oklahoma"] = "OK"
        states["Ontario"] = "ON"
        states["Oregon"] = "OR"
        states["Pennsylvania"] = "PA"
        states["Prince Edward Island"] = "PE"
        states["Puerto Rico"] = "PR"
        states["Quebec"] = "QC"
        states["Rhode Island"] = "RI"
        states["Saskatchewan"] = "SK"
        states["South Carolina"] = "SC"
        states["South Dakota"] = "SD"
        states["Tennessee"] = "TN"
        states["Texas"] = "TX"
        states["Utah"] = "UT"
        states["Vermont"] = "VT"
        states["Virgin Islands"] = "VI"
        states["Virginia"] = "VA"
        states["Washington"] = "WA"
        states["West Virginia"] = "WV"
        states["Wisconsin"] = "WI"
        states["Wyoming"] = "WY"
        states["Yukon Territory"] = "YT"

        if (states.containsKey(state)) {
            return states[state]?.toLowerCase(Locale.ROOT)
        }
        return null
    }
}