package com.elaj.patient.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elaj.patient.R
import kotlinx.android.synthetic.main.tool_bar.*

class ContactUsFragment : FragmentBase() {
    var activity: Activity? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_contact_us, container, false)
        activity = getActivity()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()

        mainTitleTxt.text = getString(R.string.contact_us)

        homeBtn.visibility = gone

    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }

    companion object {
        const val DElAY_TIME = 300
    }
}