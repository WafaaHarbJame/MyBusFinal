package com.mybus.mybusapp.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.MapHandler
import com.mybus.mybusapp.activities.DriversMapActivity
import com.mybus.mybusapp.activities.MapActivity
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.models.MemberModel
import kotlinx.android.synthetic.main.fragment_main_screen.*


class HomeClientFragment : FragmentBase() {
    var activity: Activity? = null
    private var selectdLat = 0.0
    private var selectdLng = 0.0

    private var selectedDestinationLat = 0.0
    private var selectedDestinationLng = 0.0

    private var address=""
    private var destinationAddress=""

    var isSelectLocation = false
    var isSelectDestination= false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main_screen, container, false)
        activity = getActivity()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()

            getData()

        selectLocation.setOnClickListener {
            val intent = Intent(requireActivity(), MapActivity::class.java)
            intent.putExtra(Constants.KEY_MAP_TYPE, 1)
            startActivityForResult(intent, MapActivity.REQUEST_SELECT_LOCATION)

        }

        editMyLocationBtn.setOnClickListener {
            val intent = Intent(requireActivity(), MapActivity::class.java)
            intent.putExtra(Constants.KEY_MAP_TYPE, 2)
            intent.putExtra(Constants.KEY_LAT,selectdLat)
            intent.putExtra(Constants.KEY_LNG,selectdLng)
            startActivityForResult(intent, MapActivity.REQUEST_SELECT_LOCATION)
        }

        selectDistinationButton.setOnClickListener {
            val intent = Intent(requireActivity(), MapActivity::class.java)
            intent.putExtra(Constants.KEY_MAP_TYPE, 3)
            startActivityForResult(intent, MapActivity.REQUEST_SELECT_DESTINATION_LOCATION)
        }


        selectDriverBut.setOnClickListener {
            val intent = Intent(requireActivity(), DriversMapActivity::class.java)
            intent.putExtra(Constants.KEY_DESTINATION_LAT,selectedDestinationLat)
            intent.putExtra(Constants.KEY_DESTINATION_LNG,selectedDestinationLng)
            Log.i("TAG", "Log HomeClientFragment destinationLat  $selectedDestinationLat")
            Log.i("TAG", "Log HomeClientFragment destinationLng  $selectedDestinationLat")
            startActivity(intent)

        }



    }

    private fun getData() {
        val mobile = UtilityApp.userData?.mobileWithCountry
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                if (func == Constants.SUCCESS) {
                    UtilityApp.userData = obj as MemberModel?
                    usernameTV.text= UtilityApp.userData!!.fullName;
                    val user = UtilityApp.userData
                    selectdLat= UtilityApp.userData!!.lat
                    selectdLng= UtilityApp.userData!!.lng
                    address= UtilityApp.userData!!.address!!
                    val isSelectLocation = user?.getIsSelectLocation()

                    Log.d("User type", "user isSelectLocation${UtilityApp.userData?.isSelectLocation}");

                    if(isSelectLocation!!){
                        selectLocation.text= UtilityApp.userData!!.address
                        editMyLocationBtn.visibility=View.VISIBLE
                        myLocationTv.text=getString(R.string.my_location)
                        selectLocation.text=address
                        selectLocation.isEnabled=false
                        selectLocation.isClickable=false


                    }




                }


            }
        }).getMyAccount(mobile!!)
    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == MapActivity.REQUEST_SELECT_LOCATION){
                val bundle = data?.extras
                selectdLat = bundle?.getDouble(Constants.KEY_LAT)!!
                selectdLng = bundle.getDouble(Constants.KEY_LNG)
                address=MapHandler.getGpsAddress(context,selectdLat,selectdLng);
                selectLocation.text=address
                myLocationTv.text=getString(R.string.my_location)
                editMyLocationBtn.visibility=View.VISIBLE
                isSelectLocation=true
                updateData()
            }
            else if (requestCode == MapActivity.REQUEST_SELECT_DESTINATION_LOCATION){
                val bundle = data?.extras
                selectedDestinationLat = bundle?.getDouble(Constants.KEY_DESTINATION_LAT)!!
                selectedDestinationLng = bundle.getDouble(Constants.KEY_DESTINATION_LNG)
                destinationAddress=MapHandler.getGpsAddress(context,selectedDestinationLat,selectedDestinationLng);
                selectDestinationBut.text=destinationAddress
                isSelectDestination=true
                searchBtn.visibility=View.GONE
            }

        }

    }


    private fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }




    private fun updateData() {
        try {
            var mobileStr = UtilityApp.userData?.mobileWithCountry;
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()
                    if (func == Constants.SUCCESS) {

                        UtilityApp.userData?.address = address
                        UtilityApp.userData?.lat=selectdLat
                        UtilityApp.userData?.lat =selectdLng
                        GlobalData.successDialog(
                            activity,
                            R.string.select_location,
                            activity?.getString(R.string.sucess_choose_location)

                        )
                    } else {
                        var message = activity?.getString(R.string.fail_to_save_location)
                        GlobalData.errorDialog(
                            activity,
                            R.string.change_password,
                            message
                        )
                    }
                    
                }
            }).updateData(mobileStr,selectdLat,selectdLng,address,isSelectLocation);

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }




}