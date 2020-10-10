package com.elaj.patient.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.elaj.patient.R
import com.elaj.patient.adapters.PlansAdapter
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.DBFunction
import com.elaj.patient.models.CategoryModel
import com.elaj.patient.models.PlansModel
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_plans.*
import kotlinx.android.synthetic.main.fragment_requests.*
import kotlinx.android.synthetic.main.fragment_requests.rv


class PlansActivity : ActivityBase() {

    var plansList: MutableList<PlansModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_plans)

        title = getString(R.string.plans)

        subscribeBtn.setOnClickListener {

            finish()

        }

        rv.layoutManager = LinearLayoutManager(getActiviy())

        getPlans()

    }

    private fun initAdapter() {

        val adapter = PlansAdapter(getActiviy(), plansList, object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

            }
        })
        rv.adapter = adapter

    }

    private fun getPlans() {

        plansList = DBFunction.getPlans()
        if (plansList == null) {
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    plansList = obj as MutableList<PlansModel>?
                    initAdapter()
                }
            }).getPlans()
        } else {
            initAdapter()
        }
    }

}
