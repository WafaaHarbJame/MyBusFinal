package com.mybus.mybusapp.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mybus.mybusapp.R
import com.mybus.mybusapp.adapters.RequestsAdapter
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.models.RequestModel
import kotlinx.android.synthetic.main.fragment_current.*
import kotlinx.android.synthetic.main.tool_bar.*

/**
 * A simple [Fragment] subclass.
 */
class AllRequestsFragment : FragmentBase() {

    var activity: Activity? = null
    var allRequeststList: MutableList<RequestModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_all_request, container, false)
        activity = getActivity()


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()

        rv.layoutManager = GridLayoutManager(getActivity(), 1)

        getAllRequests()




    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }

    private fun initAdapter() {

        val adapter = RequestsAdapter(getActivity(), allRequeststList)
        rv.adapter = adapter
    }

    private fun getAllRequests() {
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    allRequeststList = obj as MutableList<RequestModel>?
                    initAdapter()
                }
            }).getAllRequests(UtilityApp.userData?.mobileWithCountry)
        }


    }

