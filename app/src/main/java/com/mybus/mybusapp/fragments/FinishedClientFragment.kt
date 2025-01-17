package com.mybus.mybusapp.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.mybus.mybusapp.R
import com.mybus.mybusapp.adapters.RequestsAdapter
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.models.RequestModel
import kotlinx.android.synthetic.main.fragment_finish.*
import kotlinx.android.synthetic.main.layout_fail_get_data.*
import kotlinx.android.synthetic.main.layout_no_data.*
import kotlinx.android.synthetic.main.layout_pre_loading.*
import kotlinx.android.synthetic.main.tool_bar.*

class FinishedClientFragment : FragmentBase() {

    var activity: Activity? = null
    var finishRequestList: MutableList<RequestModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_finish, container, false)
        activity = getActivity()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()

        mainTitleTxt.text = getString(R.string.finshed_order)
        homeBtn.visibility = gone

        rv.layoutManager = GridLayoutManager(getActivity(), 1)


        swipeDataContainer.setOnRefreshListener {

            if (UtilityApp.isLogin)
                getFinishOrders(true)
            else
                swipeDataContainer.isRefreshing = false
        }

        getFinishOrders(true)




    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }

    private fun initAdapter() {

        val adapter = RequestsAdapter(getActivity(), finishRequestList)
        rv.adapter = adapter
    }



    private fun getFinishOrders(loading: Boolean) {
        if (loading) {
            loadingProgressLY.visibility = visible
            failGetDataLY.visibility = gone
            dataLY.visibility = gone
        }
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                loadingProgressLY.visibility = gone

                if (swipeDataContainer.isRefreshing)
                    swipeDataContainer.isRefreshing = false

                if (func == Constants.SUCCESS) {

                    dataLY.visibility = visible
                    finishRequestList = obj as MutableList<RequestModel>?

                    if (finishRequestList?.isNotEmpty() == true) {
                        noDataLY.visibility = gone
                        rv.visibility = visible
                        initAdapter()

                    } else {
                        dataLY.visibility=gone
                        noDataLY.visibility = visible
                        rv.visibility = gone
                    }
                }


                else {
                    failGetDataLY.visibility = visible
                    dataLY.visibility = gone
                }


            }
        }).getFinishedClientRequests(UtilityApp.userData?.mobileWithCountry)
    }


}

