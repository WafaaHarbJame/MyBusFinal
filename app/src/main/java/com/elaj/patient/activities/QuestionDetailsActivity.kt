package com.elaj.patient.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.elaj.patient.R
import com.elaj.patient.adapters.AnswersAdapter
import com.elaj.patient.adapters.SyndromeDetailsAdapter
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager
import kotlinx.android.synthetic.main.activity_question_details.*


class QuestionDetailsActivity : ActivityBase() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_question_details)

        title = getString(R.string.question_details)

        val flowLayoutManager = FlowLayoutManager()
        flowLayoutManager.isAutoMeasureEnabled = true
        syndromeRV.layoutManager = flowLayoutManager

        answersRV.isNestedScrollingEnabled = false
        answersRV.layoutManager = LinearLayoutManager(getActiviy())

        initSyndromeAdapter()
        initAnswersAdapter()

    }

    private fun initSyndromeAdapter() {

        val adapter = SyndromeDetailsAdapter(getActiviy(), null)
        syndromeRV.adapter = adapter
    }


    private fun initAnswersAdapter() {

        val adapter = AnswersAdapter(getActiviy(), null)
        answersRV.adapter = adapter
    }


}
