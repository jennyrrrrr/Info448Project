package com.example.info448project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.info448project.CardStateAdptor
import com.example.info448project.R

class StateCardFragment: Fragment(){
    companion object {
        val TAG = StateCardFragment::class.java.simpleName
    }

    private lateinit var cardStateAdptor: CardStateAdptor

    private val listOfStates: List<String> = listOf("Alabama","Alaska", "American Samoa", "Arizona", "Arkansas", "California", "Colorado",
        "Connecticut", "Delaware", "District Of Columbia", "Federated States Of Micronesia", "Florida", "Georgia", "Guam", "Hawaii", "Idaho", "Illinois",
        "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Marshall Islands", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
        "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Northern Mariana Islands",
        "Ohio", "Oklahoma", "Oregon", "Palau", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
        "Virgin Islands", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.dont_know_location, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvSongs = view.findViewById<RecyclerView>(R.id.rvState)
        val txChoose = view.findViewById<TextView>(R.id.txChoose)

        cardStateAdptor = context?.let { CardStateAdptor(listOfStates, it) }!!
        rvSongs.adapter = cardStateAdptor

        if (cardStateAdptor != null) {
            cardStateAdptor.onStateClickListener = {state ->
                txChoose.text = state
            }
        }
    }
}