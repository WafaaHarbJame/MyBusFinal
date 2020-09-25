package com.elaj.patient.activities

import android.content.Intent
import android.os.Bundle
import com.elaj.patient.R
import kotlinx.android.synthetic.main.activity_forget_password.*


class ForgetPasswordActivity : ActivityBase() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forget_password)

        title = ""

        resetPasswordBtn.setOnClickListener {

            val intent = Intent(getActiviy(), ResetPasswordActivity::class.java)
            startActivity(intent)

        }

    }


}
