package com.elaj.patient.activities

import android.content.Intent
import android.os.Bundle
import com.elaj.patient.R
import kotlinx.android.synthetic.main.activity_forget_password.*


class ResetPasswordActivity : ActivityBase() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reset_password)

        title = ""

        resetPasswordBtn.setOnClickListener {

            val intent = Intent(getActiviy(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }

    }


}
