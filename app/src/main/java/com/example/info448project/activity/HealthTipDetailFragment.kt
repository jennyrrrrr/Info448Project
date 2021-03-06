package com.example.info448project.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.info448project.R
import com.example.info448project.activity.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_healthtip_detail.*
import kotlinx.android.synthetic.main.healthtip_detail.view.*

class HealthTipDetailFragment : Fragment() {

    private var item: DummyContent.DummyItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments.
                item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = " "
                item?.background?.let { it1 -> activity?.toolbar_layout?.setBackgroundResource(it1) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.healthtip_detail, container, false)

        item?.let {
            rootView.healthtip_detail.text = it.details
            rootView.healthtip_maintext.text = it.description
            rootView.healthtip_subhead.text = it.subheading
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
