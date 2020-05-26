package com.example.info448project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.info448project.R

class  DataOutputFragment: Fragment() {
    companion object {
        val TAG = StateCardFragment::class.java.simpleName
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

        txLocation.setOnClickListener {
            activity!!
                .supportFragmentManager
                .beginTransaction()
//                .replace(R.id.fragContainer, stateCardFragment, stateCardFragment.tag)
                .add(R.id.fragContainer, stateCardFragment, stateCardFragment.tag)
                .addToBackStack(stateCardFragment.tag)
                .commit()
//                .commitNow()
        }

    }
}