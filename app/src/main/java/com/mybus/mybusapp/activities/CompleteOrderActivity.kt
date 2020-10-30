package com.mybus.mybusapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.mybus.mybusapp.MainActivity
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.DateHandler
import com.mybus.mybusapp.Utils.MapHandler
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.models.MemberModel
import com.mybus.mybusapp.models.RequestModel
import kotlinx.android.synthetic.main.activity_complete_order.*

class CompleteOrderActivity : ActivityBase() {
    private var destinationLat = 0.0
    private var destinationLng = 0.0
    private var lat = 0.0
    private var lng = 0.0
    private var driverlat = 0.0
    private var driverlng = 0.0
    private var driverId: String? = null
//    private var toOrder: Boolean = true

    var user: MemberModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_order)
        title = getString(R.string.complete_order)

        user = UtilityApp.userData

        homeBtn.setOnClickListener {
            onBackPressed()
        }

        val bundle = intent.extras;
        if (bundle != null) {
            lat = bundle.getDouble(Constants.KEY_LAT)
            lng = bundle.getDouble(Constants.KEY_LNG)
            destinationLat = bundle.getDouble(Constants.KEY_DESTINATION_LAT)
            destinationLng = bundle.getDouble(Constants.KEY_DESTINATION_LNG)
            driverId = bundle.getString(Constants.KEY_DRIVER_ID)

//            Log.i("TAG", "Log CompleteOrderActivity destinationLat  $destinationLat")
//            Log.i("TAG", "Log CompleteOrderActivity destinationLng  $destinationLng")

            destinationTv.text =
                MapHandler.getGpsAddress(getActiviy(), destinationLat, destinationLng)
            locationTv.text = MapHandler.getGpsAddress(
                getActiviy(),
                lat,
                lng
            )

        }
//        getData(driverId!!)


        confirmBtn.setOnClickListener {
            makeOrder()

        }
    }

//    private fun getData(driverId: String) {
//        Log.i("TAG", "Log driverId $driverId")
//
//        DataFeacher(object : DataFetcherCallBack {
//            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
//
//                if (func == Constants.SUCCESS) {
//                    val user = obj as MemberModel?
//                    Log.i("TAG", "Log getData ${user!!.mobileWithCountry}")
//                    driverlat = user.lat
//                    driverlng = user.lng
//
//                }
//
//            }
//        }).getMyAccount(driverId)
//    }

    private fun makeOrder() {

        try {

            val requestMap = mutableMapOf<String, Any?>().apply {

                this["orderId"] = ""
                this["clientId"] = user?.mobileWithCountry
                this["address"] = user?.address
                this["client_name"] = user?.fullName
                this["destinationLat"] = destinationLat
                this["destinationLng"] = destinationLng
                this["lat"] = lat
                this["lng"] = lng
                this["driver_id"] = driverId
                this["requestDate"] = DateHandler.GetDateOnlyNowString()
                this["requestStatus"] = 0
                this["createdAt"] = FieldValue.serverTimestamp()
            }

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
                        val intent = Intent(getActiviy(), MainActivity::class.java)
                        intent.putExtra(Constants.KEY_TO_ORDERS, true)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    } else {
                        val message = getString(R.string.fail_to_order)
                        GlobalData.errorDialog(
                            getActiviy(),
                            R.string.make_order,
                            message
                        )
                    }


                }
            }).orderHandler(requestMap)

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }


}