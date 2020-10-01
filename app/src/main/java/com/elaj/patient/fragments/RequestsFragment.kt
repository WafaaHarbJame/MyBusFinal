package com.elaj.patient.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.elaj.patient.R
import com.elaj.patient.adapters.RequestsAdapter
import kotlinx.android.synthetic.main.fragment_requests.*
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

        mainTitleTxt.text = getString(R.string.my_questions)
        homeBtn.visibility = gone

        rv.layoutManager = LinearLayoutManager(requireActivity())

        dataLY.visibility = visible
        noDataLY.visibility = gone
        rv.visibility = visible

        initAdapter()

    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }

    fun initAdapter() {

        val adapter = RequestsAdapter(requireActivity(), rv, null)
        rv.adapter = adapter

    }

}