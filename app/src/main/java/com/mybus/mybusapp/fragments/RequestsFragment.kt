package com.mybus.mybusapp.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mybus.mybusapp.R
import kotlinx.android.synthetic.main.tool_bar.*

/**
 * A simple [Fragment] subclass.
 */
class RequestsFragment : FragmentBase() {

    var activity: Activity? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_requests, container, false)
        activity = getActivity()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()

        mainTitleTxt.text = getString(R.string.orders)
        homeBtn.visibility = gone

    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }



}