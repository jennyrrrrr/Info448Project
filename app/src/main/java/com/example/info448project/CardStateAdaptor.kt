package com.example.info448project

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CardStateAdaptor(listOfState: List<String>, val context: Context): RecyclerView.Adapter<CardStateAdaptor.StateViewHolder>() {

    private val listOfStates: List<String> = listOfState

    lateinit var onStateClickListener: (state: String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item_state, parent, false)
        return StateViewHolder(view)
    }

    override fun getItemCount(): Int = listOfStates.size;

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.bind(listOfStates[position])
    }

    inner class StateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameOfState by lazy {itemView.findViewById<TextView>(R.id.state)}

        fun bind(state: String) {
            nameOfState.text = state

            itemView.setOnClickListener {
                onStateClickListener?.invoke(state)
            }
        }
    }
}