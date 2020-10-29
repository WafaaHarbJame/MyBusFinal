package com.mybus.mybusapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.mybus.mybusapp.R
import com.mybus.mybusapp.classes.Constants
import kotlinx.android.synthetic.main.activity_register_type.*

class RegisterTypeActivity : ActivityBase() {
    private var isUser = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_type)

        userSelectLY.setOnClickListener {

            selectType(true)
            isUser = true

        }

        driverSelectLY.setOnClickListener {

            selectType(false)
            isUser = false

        }

        nextBtn.setOnClickListener {

            val intent = Intent(getActiviy(), RegisterActivity::class.java)
            intent.putExtra(Constants.KEY_IS_CUSTOMER, isUser)
            startActivity(intent)

        }

        backBtn.setOnClickListener {

            onBackPressed()

        }

    }

    override fun onResume() {
        super.onResume()

    }

    fun selectType(isCustomer: Boolean) {

        if (isCustomer) {
            userSelectTxt.setTextColor(
                ContextCompat.getColor(
                    getActiviy(),
                    R.color.colorPrimaryDark
                )
            )
            userSelectLY.background = ContextCompat.getDrawable(
                getActiviy(),
                R.drawable.round_corner_white_fill_border_primary_dark
            )

            driverSelectTxt.setTextColor(
                ContextCompat.getColor(
                    getActiviy(),
                    R.color.colorAccent2
                )
            )
            driverSelectLY.background = ContextCompat.getDrawable(
                getActiviy(),
                R.drawable.round_corner_white_fill_border_gray
            )
        } else {
            driverSelectTxt.setTextColor(
                ContextCompat.getColor(
                    getActiviy(),
                    R.color.colorPrimaryDark
                )
            )
            driverSelectLY.background = ContextCompat.getDrawable(
                getActiviy(),
                R.drawable.round_corner_white_fill_border_primary_dark
            )

            userSelectTxt.setTextColor(
                ContextCompat.getColor(
                    getActiviy(),
                    R.color.colorAccent2
                )
            )
            userSelectLY.background = ContextCompat.getDrawable(
                getActiviy(),
                R.drawable.round_corner_white_fill_border_gray
            )
        }

    }
}