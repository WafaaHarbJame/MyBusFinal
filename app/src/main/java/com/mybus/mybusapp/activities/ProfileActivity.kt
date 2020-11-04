package com.mybus.mybusapp.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.View
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.DateHandler
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.models.MemberModel
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.ageTxt
import kotlinx.android.synthetic.main.activity_register.fullNameTxt
import java.util.*

class ProfileActivity : ActivityBase() {
    var activity: Activity? = null
    var user: MemberModel? = null
    private var userType: Int = 0;
    var yearStr: Int? = 0
    var monthStr: Int? = 0
    var dayStr: Int? = 0
    private var ageNumber: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        title = getString(R.string.profile)
        activity = getActiviy()


        homeBtn.setOnClickListener {
            onBackPressed()
        }

        user = UtilityApp.userData
        userType = user!!.type

        getData()

        if (userType == 1) {
            busLayout.visibility = View.GONE

        } else if (userType == 2) {
            busLayout.visibility = View.VISIBLE

        }


        ageTxt.setOnClickListener {

            val dpd = DatePickerDialog(this, { view2, thisYear, thisMonth, thisDay ->
                monthStr = thisMonth + 1
                dayStr = thisDay
                yearStr = thisYear
                ageTxt.setText(" "+ monthStr + "/" + dayStr + "/" + yearStr)

                ageNumber = DateHandler.getAge(yearStr.toString().toInt(), monthStr.toString().toInt(), dayStr.toString().toInt()).toInt()
                val newDate: Calendar = Calendar.getInstance()
                newDate.set(thisYear, thisMonth, thisDay)
            }, yearStr!!, monthStr!!, dayStr!!)
            dpd.show()


        }

        updateBtn.setOnClickListener {

        }




    }


    private fun getData() {
        val mobile = UtilityApp.userData?.mobileWithCountry
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                if (func == Constants.SUCCESS) {
                    val user = obj as MemberModel
                    fullNameTxt.text= Editable.Factory.getInstance().newEditable(user.fullName)
                    ageTxt.text= Editable.Factory.getInstance().newEditable(user.age.toString())
                    addressTxt.text= Editable.Factory.getInstance().newEditable(user.address)
                    busModeTxt.text= Editable.Factory.getInstance().newEditable(user.busModel)
                    busColoTxt.text= Editable.Factory.getInstance().newEditable(user.busColor)
                    busCapacityTv.text= Editable.Factory.getInstance().newEditable(user.busLoading.toString())
                    busNumbTxt.text= Editable.Factory.getInstance().newEditable(user.busNumber.toString())
                }


            }
        }).getMyAccount(mobile!!)
    }

    private fun updateProfile() {

        try {

            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()

                    if (func == Constants.SUCCESS) {
                        GlobalData.successDialog(
                            getActiviy(),
                            R.string.update_profile,
                            getString(R.string.success_edit)
                        )

                    } else {
                        GlobalData.errorDialog(
                            getActiviy(),
                            R.string.update_profile,
                            getString(R.string.fail_to_edit)
                        )
                    }


                }
            }).updateUserData("","")

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }



}

