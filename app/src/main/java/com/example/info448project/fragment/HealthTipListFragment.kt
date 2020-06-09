package com.example.info448project.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.info448project.R
import com.example.info448project.activity.HealthTipDetailActivity
import com.example.info448project.activity.HealthTipDetailFragment
import com.example.info448project.activity.MainActivity
import com.example.info448project.activity.dummy.DummyContent
import kotlinx.android.synthetic.main.healthtip_list.*
import kotlinx.android.synthetic.main.healthtip_list_content.view.*


class HealthTipListFragment: Fragment() {

    companion object {
        val TAG = HealthTipListFragment::class.java.simpleName
    }

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_healthtip_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Health Tips"

        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        (activity as AppCompatActivity?)?.supportActionBar?.title = (activity as AppCompatActivity?)?.title

        if (healthtip_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        val healthtip_list = view.findViewById<RecyclerView>(R.id.healthtip_list)
        if (healthtip_list != null) {
            setupRecyclerView(healthtip_list)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val mainActivity =  MainActivity()
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(mainActivity, DummyContent.ITEMS, twoPane)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: MainActivity,
        private val values: List<DummyContent.DummyItem>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as DummyContent.DummyItem
                if (twoPane) {
                    val fragment = HealthTipDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(HealthTipDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    val mainActivity = MainActivity()
                    mainActivity.supportFragmentManager
                        .beginTransaction()
                        .add(R.id.healthtip_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, HealthTipDetailActivity::class.java).apply {
                        putExtra(HealthTipDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.healthtip_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.iconView.setImageResource(item.icon)
            holder.contentView.text = item.content

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val iconView: ImageView = view.ivIcon
            val contentView: TextView = view.content
        }
    }
}