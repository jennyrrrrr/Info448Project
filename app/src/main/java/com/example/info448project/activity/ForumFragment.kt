package com.example.info448project.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.info448project.R

/* Documentation:
Overall Fragment architecture - This fragment fetches data in JSON format,
then displays it in a recyclerview

To do:
- set up json
- fetch json
- store json in variable
- set up one tab
- display json in recyclerview
- set up multiple tabs
- set up ability to modify data
 */
class ForumFragment : Fragment() {
    companion object {
        val TAG: String = ForumFragment::class.java.simpleName
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum, container, false)
    }

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
     */
}
