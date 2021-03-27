package com.mybus.mybusapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mybus.mybusapp.R
import com.mybus.mybusapp.activities.AddDriverActivity
import com.mybus.mybusapp.adapters.DriversAdapter
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.models.DriverModel
import kotlinx.android.synthetic.main.fragment_drivers.*
import kotlinx.android.synthetic.main.layout_no_data.*


class DriversFragment : FragmentBase() {

    var driversList: MutableList<DriverModel?>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_drivers, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupUI(parentLY)


        mainTitleTxt.text = getString(R.string.manage_drivers)

        rv.layoutManager = LinearLayoutManager(requireActivity())

        addDoctorBtn.setOnClickListener {

            val intent = Intent(requireActivity(), AddDriverActivity::class.java)
//            intent.putExtra(Constants.KEY_DOCTOR_MODEL, doctorModel)
            startActivity(intent)

        }

        getDrivers()

        swipeDataContainer.setColorSchemeColors(
            ContextCompat.getColor(
                requireActivity(),
                R.color.colorPrimary
            )
        )
        swipeDataContainer.setOnRefreshListener {
            getDrivers()

        }

    }

    override fun onResume() {
        super.onResume()
        if (GlobalData.REFRESH_DRIVERS) {
            GlobalData.REFRESH_DRIVERS = false
            getDrivers()
        }
    }


    private fun getDrivers() {

        swipeDataContainer.isRefreshing = true
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                swipeDataContainer.isRefreshing = false
                if (func == Constants.SUCCESS) {
                    driversList = obj as MutableList<DriverModel?>?
                    if (driversList?.isNotEmpty() == true) {
                        rv.visibility = visible
                        noDataLY.visibility = gone
                        initAdapter()
                    } else {
                        rv.visibility = gone
                        noDataLY.visibility = visible
                        noDataTxt.text = getString(R.string.no_drivers)
                    }
                } else {
                    rv.visibility = gone
                    noDataLY.visibility = visible
                    noDataTxt.text = getString(R.string.fail_to_get_data)
                }
            }
        }).getAllDrivers()

    }

    fun initAdapter() {
        val adapter = DriversAdapter(requireActivity(), driversList!!,
            object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    if (IsSuccess)
                        getDrivers()
                }
            })
        rv.adapter = adapter
    }
}