package com.elaj.patient.activities

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.elaj.patient.R
import kotlinx.android.synthetic.main.activity_new_question.*


class NewQuestionActivity : ActivityBase() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_question)

        title = ""


        askQuestionLY.setOnClickListener {
            selectButton(true)

        }

        callDoctorLY.setOnClickListener {
            selectButton(false)

        }

    }

    fun selectButton(isWriteQuestion: Boolean) {

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

    }

}
