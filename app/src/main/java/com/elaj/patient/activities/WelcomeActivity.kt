package com.elaj.patient.activities

import android.content.Intent
import android.os.Bundle
import com.elaj.patient.MainActivityBottomNav
import com.elaj.patient.R
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : ActivityBase() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        continueBtn.setOnClickListener {

            val intent = Intent(getActiviy(), MainActivityBottomNav::class.java)
            startActivity(intent)

        }

    }


}
