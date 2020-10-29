package com.mybus.mybusapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mybus.mybusapp.MainActivityBottomNav
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.DateHandler
import com.mybus.mybusapp.Utils.MapHandler
import com.mybus.mybusapp.Utils.NumberHandler
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.AESCrypt
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.models.MemberModel
import com.mybus.mybusapp.models.RegisterUserModel
import com.mybus.mybusapp.models.RequestModel
import kotlinx.android.synthetic.main.activity_complete_order.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.ArrayList

class CompleteOrderActivity : ActivityBase() {
    private var destinationLat = 0.0
    private var destinationLng = 0.0
    private var lat = 0.0
    private var lng = 0.0
    private var driverlat = 0.0
    private var driverlng = 0.0
    private var driverId:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_order)
        title =getString(R.string.complete_order)

        homeBtn.setOnClickListener {
            onBackPressed()
        }

        val bundle = intent.extras;
        if (bundle != null) {
            lat = bundle.getDouble(Constants.KEY_LAT)
            lng = bundle.getDouble(Constants.KEY_LNG)
            destinationLat = bundle.getDouble(Constants.KEY_DESTINATION_LAT)
            destinationLng = bundle.getDouble(Constants.KEY_DESTINATION_LNG)
            driverId=bundle.getString(Constants.KEY_DRIVER_ID)
            destinationTv.text=MapHandler.getGpsAddress(getActiviy(),destinationLat,destinationLng)
            locationTv.text=MapHandler.getGpsAddress(getActiviy(),UtilityApp.userData!!.lat,UtilityApp.userData!!.lng)

        }
        getData(driverId!!)


        confirmBtn.setOnClickListener {
            makeOrder();

        }
    }

    private fun getData(driverId: String) {
        Log.i("TAG", "Log driverId $driverId")

        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                if (func == Constants.SUCCESS) {
                    val user=obj as MemberModel?
                    Log.i("TAG", "Log getData ${user!!.mobileWithCountry}")
                    driverlat= user.lat
                    driverlng=user.lng

                }

            }
        }).getMyAccount(driverId)
    }

    private fun makeOrder() {

        try {

            val requestModel = RequestModel()
            requestModel.orderId=""
            requestModel.clientId= UtilityApp.userData!!.mobileWithCountry
            requestModel.address= UtilityApp.userData!!.address
            requestModel.client_name= UtilityApp.userData!!.fullName
            requestModel.destinationLat=destinationLat
            requestModel.destinationLng=destinationLng
            requestModel.lat= UtilityApp.userData!!.lat
            requestModel.lng= UtilityApp.userData!!.lng
            requestModel.requestDate=DateHandler.GetDateOnlyNowString()
            requestModel.requestStatus=0

            GlobalData.progressDialog(
                getActiviy(),
                R.string.make_order,
                R.string.please_wait_to_make_order
            )

            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()

                    if (func == Constants.SUCCESS) {
                       Toast(R.string.make_order_sucess)
                        val intent = Intent(getActiviy(), MainActivityBottomNav::class.java)
                        startActivity(intent)
                    } else {
                        var message = getString(R.string.fail_to_order)
                        GlobalData.errorDialog(
                            getActiviy(),
                            R.string.make_order,
                            message
                        )
                    }


                }
            }).orderHandler(requestModel)

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }




}