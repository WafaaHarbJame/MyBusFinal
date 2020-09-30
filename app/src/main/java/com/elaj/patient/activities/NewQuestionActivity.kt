package com.elaj.patient.activities

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.elaj.patient.R
import com.elaj.patient.adapters.SyndromeAdapter
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.dialogs.SuccessSendDialog
import kotlinx.android.synthetic.main.activity_new_question.*


class NewQuestionActivity : ActivityBase() {

    var successSendDialog: SuccessSendDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_question)

        title = ""

        rv.layoutManager = LinearLayoutManager(getActiviy())

        askQuestionLY.setOnClickListener {
            selectButton(true)

        }

        callDoctorLY.setOnClickListener {
            selectButton(false)

        }


        sendBtn.setOnClickListener {

            if (successSendDialog == null) {
                successSendDialog = SuccessSendDialog(getActiviy())
                successSendDialog!!.setOnDismissListener {
                    successSendDialog = null

                    finish()
                }
            }
        }

        askQuestionLY.performClick()

        initAdapter()

    }

    private fun initAdapter() {

        val adapter = SyndromeAdapter(getActiviy(), null, object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

            }
        })
        rv.adapter = adapter
    }

    private fun selectButton(isWriteQuestion: Boolean) {

        askQuestionLY.background =
            ContextCompat.getDrawable(
                getActiviy(),
                if (isWriteQuestion) R.drawable.round_corner_accent_fill else R.drawable.round_corner_primary_light_fill
            )
        askQuestionIcon.setImageDrawable(
            ContextCompat.getDrawable(
                getActiviy(),
                if (isWriteQuestion)
                    R.drawable.write_question_active
                else
                    R.drawable.write_question_inactive
            )
        )
        askQuestionTV.setTextColor(
            ContextCompat.getColor(
                getActiviy(),
                if (isWriteQuestion) R.color.white else R.color.colorAccent
            )
        )

        callDoctorLY.background =
            ContextCompat.getDrawable(
                getActiviy(),
                if (isWriteQuestion) R.drawable.round_corner_primary_light_fill else R.drawable.round_corner_accent_fill
            )
        callDoctorIcon.setImageDrawable(
            ContextCompat.getDrawable(
                getActiviy(),
                if (isWriteQuestion)
                    R.drawable.call_doctor_inactive
                else
                    R.drawable.call_doctor_active
            )
        )
        callDoctorTV.setTextColor(
            ContextCompat.getColor(
                getActiviy(),
                if (isWriteQuestion) R.color.colorAccent else R.color.white
            )
        )

        if (isWriteQuestion) {
            patientDetailsLabel.visibility = visible
            ageInput.visibility = visible
            genderInput.visibility = visible
            rv.visibility = visible

            contactInfoLabel.visibility = gone
            contactInfoHintLabel.visibility = gone
            whatsAppInput.visibility = gone
            skypeInput.visibility = gone
        } else {
            patientDetailsLabel.visibility = gone
            ageInput.visibility = gone
            genderInput.visibility = gone
            rv.visibility = gone

            contactInfoLabel.visibility = visible
            contactInfoHintLabel.visibility = visible
            whatsAppInput.visibility = visible
            skypeInput.visibility = visible
        }

    }

}
