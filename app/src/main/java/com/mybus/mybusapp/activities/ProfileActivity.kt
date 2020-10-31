package com.mybus.mybusapp.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.MapHandler
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.models.MemberModel
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.myLocationTv
import kotlinx.android.synthetic.main.fragment_main_screen.editMyLocationBtn
import kotlinx.android.synthetic.main.fragment_main_screen.selectLocation

class ProfileActivity : ActivityBase() {
    var activity: Activity? = null
    private var selectdLat = 0.0
    private var selectdLng = 0.0
    private var address=""
    var isSelectLocation = false
    var user: MemberModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        title =getString(R.string.profile)
        activity=getActiviy()


        homeBtn.setOnClickListener {
            onBackPressed()
        }



        user = UtilityApp.userData

        initData()



        selectLocation.setOnClickListener {
            val intent = Intent(getActiviy(), MapActivity::class.java)
            intent.putExtra(Constants.KEY_MAP_TYPE, 1)
            startActivityForResult(intent, MapActivity.REQUEST_SELECT_LOCATION)

        }

        editMyLocationBtn.setOnClickListener {
            val intent = Intent(getActiviy(), MapActivity::class.java)
            intent.putExtra(Constants.KEY_MAP_TYPE, 2)
            intent.putExtra(Constants.KEY_LAT, selectdLat)
            intent.putExtra(Constants.KEY_LNG, selectdLng)
            startActivityForResult(intent, MapActivity.REQUEST_SELECT_LOCATION)
        }


        statusSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            var isDriverActive=false
            if(isChecked){
                isDriverActive=true
                updateBusStatus(isDriverActive)
            }
            else{
                isDriverActive=false
                updateBusStatus(isDriverActive)

            }

        }



    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MapActivity.REQUEST_SELECT_LOCATION && resultCode == Activity.RESULT_OK) {
            val bundle = data?.extras
            selectdLat = bundle?.getDouble(Constants.KEY_LAT)!!
            selectdLng = bundle.getDouble(Constants.KEY_LNG)
            address = MapHandler.getGpsAddress(getActiviy(), selectdLat, selectdLng);
            selectLocation.text = address
            myLocationTv.text = getString(R.string.my_location)
            editMyLocationBtn.visibility = View.VISIBLE
            isSelectLocation = true
            updateData()


        }
    }

    private fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }

    private fun getData() {
        val mobile = UtilityApp.userData?.mobileWithCountry
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                if (func == Constants.SUCCESS) {
                    UtilityApp.userData = obj as MemberModel?
                    val user = UtilityApp.userData
                    selectdLat= UtilityApp.userData!!.lat
                    selectdLng= UtilityApp.userData!!.lng
                    val isSelectLocation = user?.getIsSelectLocation()
                    val isDriverActive= user?.getIsDriverActive()
                    busLoadingTv.text= user?.busLoading.toString()
                    Log.d("User type", "user isSelectLocation${UtilityApp.userData?.isSelectLocation}" );

                    if(isSelectLocation!!){
                        selectLocation.text= UtilityApp.userData!!.address
                        editMyLocationBtn.visibility= View.VISIBLE
                      //  myLocationTv.text=R.string.my_location)

                    }

                    if(isDriverActive!!){
                        statusSwitch.isChecked=true

                    }


                }


            }
        }).getMyAccount(mobile!!)
    }

    private fun updateData() {
        try {
            var mobileStr = UtilityApp.userData?.mobileWithCountry;
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()
                    if (func == Constants.SUCCESS) {

                        UtilityApp.userData?.address = address
                        UtilityApp.userData?.lat = selectdLat
                        UtilityApp.userData?.lng = selectdLng
                        UtilityApp.userData?.isSelectLocation=isSelectLocation
                        GlobalData.successDialog(
                            getActiviy(),
                            R.string.select_location,
                            activity?.getString(R.string.sucess_choose_location)

                        )
                    } else {
                        var message = activity?.getString(R.string.fail_to_save_location)
                        GlobalData.errorDialog(
                            getActiviy(),
                            R.string.change_password,
                            message
                        )
                    }

                }
            }).updateData(mobileStr, selectdLat, selectdLng, address, isSelectLocation);

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

    private fun updateBusStatus(isDriverActive:Boolean) {
        try {
            var mobileStr = UtilityApp.userData?.mobileWithCountry;
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()
                    if (func == Constants.SUCCESS) {
//                        GlobalData.successDialog(
//                            activity,
//                            R.string.change_bus_status,
//                            activity?.getString(R.string.sucess_change_satus)
//                        )

                    } else {
                        var message = getActiviy()?.getString(R.string.fail_to_change_status)
                        GlobalData.errorDialog(
                            getActiviy(),
                            R.string.change_bus_status,
                            message
                        )
                    }

                }
            }).updateStatus(mobileStr,isDriverActive);

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

    private fun initData() {

        //usernameTV.text = user?.fullName
        selectdLat = user?.lat!!
        selectdLng = user?.lng!!
        address = user?.address!!
        val isSelectLocation = user?.getIsSelectLocation()

        if (isSelectLocation!!) {
            selectLocation.text = user?.address
            editMyLocationBtn.visibility = View.VISIBLE
            myLocationTv.text = getString(R.string.my_location)
            selectLocation.text = address
            selectLocation.isEnabled = false
            selectLocation.isClickable = false

        }

    }

}