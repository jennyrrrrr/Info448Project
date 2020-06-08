package com.example.info448project.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.info448project.CardStateAdaptor
import com.example.info448project.R
import kotlinx.android.synthetic.main.cardview_item_state.*

class StateCardFragment: Fragment(){
    companion object {
        val TAG = StateCardFragment::class.java.simpleName
    }

    private var onStateSelectListener: OnStateSelectListener? = null

    private lateinit var cardStateAdaptor: CardStateAdaptor

    private val listOfStates: List<String> = listOf("Alabama","Alaska", "American Samoa", "Arizona", "Arkansas", "California", "Colorado",
        "Connecticut", "Delaware", "District Of Columbia", "Federated States Of Micronesia", "Florida", "Georgia", "Guam", "Hawaii", "Idaho", "Illinois",
        "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Marshall Islands", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
        "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
        "Ohio", "Oklahoma", "Oregon", "Palau", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
        "Virgin Islands", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming")

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnStateSelectListener) {
            onStateSelectListener = context
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.dont_know_location, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Select State"

        val rvState = view.findViewById<RecyclerView>(R.id.rvState)

        cardStateAdaptor = context?.let { CardStateAdaptor(listOfStates) }!!
        rvState.adapter = cardStateAdaptor

        cardStateAdaptor.onStateClicked = {
            onStateSelectListener?.onStateClick(it)
        }
    }
}

    interface OnStateSelectListener {
        fun onStateClick(state: String)
    }