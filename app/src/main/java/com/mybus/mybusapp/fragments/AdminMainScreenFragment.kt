package com.mybus.mybusapp.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mybus.mybusapp.R
import com.mybus.mybusapp.adapters.MainAdminAdapter
import com.mybus.mybusapp.models.MainAdminModel
import kotlinx.android.synthetic.main.fragment_main_screen.rv
import kotlinx.android.synthetic.main.fragment_requests.*
import kotlinx.android.synthetic.main.tool_bar.*
import java.util.*


class AdminMainScreenFragment : FragmentBase() {
    var activity: Activity? = null
    var mainList: MutableList<MainAdminModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_admin_main_screen, container, false)
        activity = getActivity()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()

        mainTitleTxt.text = getString(R.string.home)

        homeBtn.visibility = gone
        mainList = ArrayList<MainAdminModel>()


        rv.layoutManager = LinearLayoutManager(requireActivity())
        rv.hasFixedSize()
        getMainContent()
    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }


    private fun initAdapter() {

        val adapter = MainAdminAdapter(requireActivity(), mainList)
        rv.adapter = adapter

    }

    private fun getMainContent() {
        mainList?.add(
            MainAdminModel(
                MainAdminModel.MANAGE_DRIVERS,
                getString(R.string.manage_drivers)
            )
        )

        initAdapter()


    }

}