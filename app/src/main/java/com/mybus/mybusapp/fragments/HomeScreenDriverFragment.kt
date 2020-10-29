package com.mybus.mybusapp.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.google.android.gms.location.DetectedActivity
import com.kcode.permissionslib.main.OnRequestPermissionsCallBack
import com.kcode.permissionslib.main.PermissionCompat
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.MapHandler
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.models.GpsModel
import io.nlopez.smartlocation.OnActivityUpdatedListener
import io.nlopez.smartlocation.OnGeofencingTransitionListener
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.geofencing.utils.TransitionGeofence
import io.nlopez.smartlocation.location.config.LocationAccuracy
import io.nlopez.smartlocation.location.config.LocationParams
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider
import kotlinx.android.synthetic.main.fragment_home_driver.*
import java.util.*


class HomeScreenDriverFragment : FragmentBase(), OnLocationUpdatedListener,
    OnActivityUpdatedListener, OnGeofencingTransitionListener {


    var activity: Activity? = null
    var isGrantPermission = false
    private var selectedLat= 0.0
    private  var selectedLng=0.0
    var address:String = ""
    var isSelectLocation = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_driver, container, false)
        activity = getActivity()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()
        mainTitleTxt.text =getString(R.string.app_name)
        homeBtn.visibility = gone
        
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
        checkLocationRuntimePermission()
    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)

        val currentBundle = Bundle()
        currentBundle.putString(Constants.KEY_TYPE, Constants.CURRENT)
        val currentFragment: Fragment = AllOrderClientFragment()
        currentFragment.arguments = currentBundle
        currentFragment.retainInstance = true

        val completedBundle = Bundle()
        completedBundle.putString(Constants.KEY_TYPE, Constants.COMPLETED)
        val completedFragment: Fragment = FinishedDriveragment()
        completedFragment.arguments = completedBundle
        completedFragment.retainInstance = true


        val allOrderBundle = Bundle()
        allOrderBundle.putString(Constants.KEY_TYPE, Constants.ALL)
        val allRequestsFragment: Fragment = AllRequestsFragment()
        allRequestsFragment.arguments = allOrderBundle
        allRequestsFragment.retainInstance = true

        adapter.addFragment(allRequestsFragment, getString(R.string.all_orders))
        adapter.addFragment(currentFragment, getString(R.string.current_request))
        adapter.addFragment(completedFragment, getString(R.string.finshed_order))



        viewPager.adapter = adapter
    }

    class ViewPagerAdapter(manager: FragmentManager) :
        FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val mFragmentList: MutableList<Fragment> = ArrayList<Fragment>()
        private val mFragmentTitleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    private fun checkLocationRuntimePermission() {
        try {
            checkLocationPermission(context)
        } catch (e: Exception) {
        }
    }



    private fun checkLocationPermission(activity: Context?) {
        try {
            val builder = PermissionCompat.Builder(activity)
            builder.addPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            builder.addPermissionRationale(getString(R.string.about_app))
            builder.addRequestPermissionsCallBack(object : OnRequestPermissionsCallBack {
                override fun onGrant() {
                    isGrantPermission = true
                    if (SmartLocation.with(context).location().state().isGpsAvailable) {
                        startLocation()
                    } else {
                        if (selectedLat == 0.0 && selectedLng == 0.0) {
                            showSettingsAlert()
                        }
                    }

                }

                override fun onDenied(permission: String) {
                    Toast(R.string.some_permission_denied)
                }
            })
            builder.build().request()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private fun startLocation() {
        isSelectLocation=true
        val builder=LocationParams.Builder()
            .setAccuracy(LocationAccuracy.HIGH)
             .setDistance(12F)
            .setInterval(5000);

        val provider = LocationGooglePlayServicesWithFallbackProvider(context);

        SmartLocation.with(context)
            .location(provider)
            .continuous()
            .config(builder.build())
            .start(this)


    }


    fun showSettingsAlert() {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.setTitle("GPS Settings")
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?")
        alertDialog.setPositiveButton(
            "Settings"
        ) { dialog, which ->
            val intent =
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context!!.startActivity(intent)
        }

        // on pressing cancel button
        alertDialog.setNegativeButton(
            "Cancel"
        ) { dialog, which ->
            dialog.cancel()
            getLatAndLong()
        }

        alertDialog.show()
    }


    fun getLatAndLong() {
        AndroidNetworking.get("http://ip-api.com/json/").setTag("test")
            .setPriority(Priority.LOW).addQueryParameter("lang", "en")
            .build().getAsObject(
                GpsModel::class.java, object : ParsedRequestListener<GpsModel> {
                    override fun onResponse(apiLocationModel: GpsModel) {
                        if (apiLocationModel.getStatus().equals("success")) {
                            selectedLat = apiLocationModel.getLat()
                            selectedLng = apiLocationModel.getLon()
                            address = MapHandler.getGpsAddress(context, selectedLat, selectedLng)
                            Log.d(TAG, "getLatAndLong latitude $selectedLat"
                            )
                            Log.d(TAG,
                                "getLatAndLong longitude $selectedLng")
                        }
                    }

                    override fun onError(anError: ANError) {}
                })
    }

    override fun onLocationUpdated(p0: Location?) {
        showLocation(p0)

    }

    override fun onActivityUpdated(p0: DetectedActivity?) {

    }

    override fun onGeofenceTransition(p0: TransitionGeofence?) {
    }
    private fun showLocation(location: Location?) {
        if (location != null) {
            val text = String.format(
                "Latitude %.6f, Longitude %.6f",
                location.latitude,
                location.longitude)

            Log.d(TAG, "smart latitude " + location.latitude)
            Log.d(TAG, "smart longitude " + location.longitude)
            selectedLat=location.latitude
            selectedLng=location.longitude
            address = MapHandler.getGpsAddress(activity, selectedLat, selectedLng)
            updateData();
            SmartLocation.with(activity).geocoding()
                .reverse(
                    location
                ) { original, results ->
                    if (results.size > 0) {
                        val result = results[0]
                        val builder = StringBuilder(text)
                        builder.append("\n[Reverse Geocoding] ")
                        val addressElements: MutableList<String?> =
                            ArrayList()
                        for (i in 0..result.maxAddressLineIndex) {
                            addressElements.add(result.getAddressLine(i))
                        }
                        builder.append(TextUtils.join(", ", addressElements))
                        Log.d(TAG, "builder$builder"
                        )
                    }
                }
        }
    }


    private fun updateData() {
        try {
            var mobileStr = UtilityApp.userData?.mobileWithCountry;
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                }
            }).updateData(mobileStr,selectedLat,selectedLng,address,isSelectLocation);

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }





}