//package com.elaj.patient.fragments
//
//import android.app.Activity
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.elaj.patient.R
//
//class TermsFragment : FragmentBase() {
//    var activity: Activity? = null
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view: View = inflater.inflate(R.layout.fragment_terms, container, false)
//        activity = getActivity()
//        return view
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        activity = getActivity()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        activity = getActivity()
//    }
//
//    companion object {
//        const val DElAY_TIME = 300
//    }
//}