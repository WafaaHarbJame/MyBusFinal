package com.elaj.patient.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import com.elaj.patient.MainActivityBottomNav
import com.elaj.patient.R
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : ActivityBase() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }

        setContentView(R.layout.activity_welcome)


        continueBtn.setOnClickListener {

//            val intent = Intent(getActiviy(), MainActivityBottomNav::class.java)
            val intent = Intent(getActiviy(), LoginActivity::class.java)
            startActivity(intent)

        }

    }


}
