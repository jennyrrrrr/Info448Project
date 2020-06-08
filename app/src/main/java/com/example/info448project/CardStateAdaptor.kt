package com.example.info448project

import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class CardStateAdaptor(listOfState: List<String>): RecyclerView.Adapter<CardStateAdaptor.StateViewHolder>() {

    private val listOfStates: List<String> = listOfState

    var onStateClicked: ((state: String) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item_state, parent, false)
        return StateViewHolder(view)
    }

    override fun getItemCount(): Int = listOfStates.size;

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.bind(listOfStates[position])
    }

    inner class StateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameOfState by lazy { itemView.findViewById<TextView>(R.id.btState)}

        fun bind(state: String) {
            nameOfState.text = state

            nameOfState.setOnClickListener {
                onStateClicked?.invoke(state)
            }
        }
    }
}