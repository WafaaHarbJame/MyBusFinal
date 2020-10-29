package com.mybus.mybusapp.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.kcode.permissionslib.main.OnRequestPermissionsCallBack
import com.kcode.permissionslib.main.PermissionCompat
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.ImageHandler
import com.mybus.mybusapp.Utils.MapHandler
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.models.AllDriversModel
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_map.*
import java.util.*

class DriversMapActivity : ActivityBase(), OnMapReadyCallback {

    var map: GoogleMap? = null
    var fragment: SupportMapFragment? = null
    var zoomLevel = 10f
    private var selectedLat = 0.0
    private var selectedLng = 0.0

    var isGrantPermission = false

    private var selectedDestinationLat = 0.0
    private var selectedDestinationLng = 0.0
    private lateinit var  driverId:String

    var allDrivesList: MutableList<AllDriversModel>? = null

    var markers: MutableList<Marker>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drivers_map)

        markers = ArrayList()



        val bundle = intent.extras;

            if (bundle != null) {
                selectedDestinationLat = bundle?.getDouble(Constants.KEY_DESTINATION_LAT)
                selectedDestinationLng = bundle?.getDouble(Constants.KEY_DESTINATION_LNG)
                Log.i("TAG", "Log CompleteOrderActivity destinationLat  $selectedDestinationLat")
                Log.i("TAG", "Log CompleteOrderActivity destinationLng  $selectedDestinationLat")
            }

        getAllDrivers()

        backBtn.setOnClickListener {
            onBackPressed()
        }

        confirmBtn.setOnClickListener {
            val intent = Intent(getActiviy(), CompleteOrderActivity::class.java)
            intent.putExtra(Constants.KEY_DESTINATION_LAT, selectedDestinationLat)
            intent.putExtra(Constants.KEY_DESTINATION_LNG, selectedDestinationLng)
            intent.putExtra(Constants.KEY_DRIVER_ID, driverId)
            Log.i("TAG", "Log confirmBtn $driverId")
            Log.i("TAG", "Log CompleteOrderActivity destinationLat  $selectedDestinationLat")
            Log.i("TAG", "Log CompleteOrderActivity destinationLng  $selectedDestinationLat")
            startActivity(intent)
        }



        val fm: FragmentManager = supportFragmentManager
        fragment = fm.findFragmentById(R.id.map) as SupportMapFragment
        fragment?.getMapAsync(this)



    }


    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap

//        map?.setOnMarkerClickListener { marker ->
//            val position = markers!!.indexOf(marker)
//            if(allDrivesList!!.get(position).mobile!=null){
//                val selectedDriver: AllDriversModel = allDrivesList!!.get(position)
//                driverId = selectedDriver.getMobileWithCountry()
//                confirmBtn.visibility = View.VISIBLE
//            }
//
//            false
//        }

        map?.setOnMarkerClickListener { marker ->
            val position = markers!!.indexOf(marker)
            if (markers!!.get(position).title != getString(R.string.destination_location)) {
                val selectedDriver: AllDriversModel = allDrivesList!!.get(position)
                driverId = selectedDriver.getMobileWithCountry()
                Log.i("TAG", "Log driverId $driverId")
                Toast(driverId)

                confirmBtn.visibility = View.VISIBLE

            }
            false
        }

//        if (map != null)
//            map?.clear()
//        map?.addMarker(
//            MarkerOptions()
//                .position(
//                    LatLng(selectedDestinationLat, selectedDestinationLng)
//                )
//                .icon(
//                    BitmapDescriptorFactory.fromBitmap(
//                        ImageHandler.getBitmap(
//                            getActiviy(), R.drawable.ic_map_destination_marker
//                        )
//                    )
//                )
//                .title(getString(R.string.destination_location))
//        )
//
//        val latLn = LatLng(selectedDestinationLat, selectedDestinationLng)
//        val cameraUpdate =
//            CameraUpdateFactory.newCameraPosition(
//                CameraPosition.fromLatLngZoom(
//                    latLn,
//                    zoomLevel
//                )
//            );
//        map?.moveCamera(cameraUpdate)

        myLocationBtn.setOnClickListener {
            checkLocationPermission()
        }



    }

    private fun checkLocationPermission() {
        try {
            val builder = PermissionCompat.Builder(getActiviy())
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
                    if (map != null)
                        getMyLocation()
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

    private fun getMyLocation() {
        val locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            SmartLocation.with(getActiviy()).location()
                .oneFix()
                .start { location ->

                    val lat: Double = location.latitude
                    val lng: Double = location.longitude

                    map?.addMarker(
                        MarkerOptions()
                            .position(
                                LatLng(lat, lng)
                            )
                            .icon(
                                BitmapDescriptorFactory.fromBitmap(
                                    ImageHandler.getBitmap(
                                        getActiviy(), R.drawable.ic_map_marker
                                    )
                                )
                            )
                            .title(getString(R.string.my_location))
                    )

                    val latLn = LatLng(lat, lng)
                    val cameraUpdate =
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition.fromLatLngZoom(
                                latLn,
                                zoomLevel
                            )
                        );
                    map?.animateCamera(cameraUpdate)

                }

        } else {
            showGPSDisabledAlertToUser()
        }


    }

    private fun showGPSDisabledAlertToUser() {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(getActiviy())
        alertDialogBuilder.setMessage(getString(R.string.open_gps))
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.enable)
            ) { dialog, id ->
                dialog.cancel()
                val callGPSSettingIntent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                startActivity(callGPSSettingIntent)
                dialog.cancel()
            }
        alertDialogBuilder.setNegativeButton(
            getString(R.string.cancel)
        ) { dialog, id -> dialog.cancel() }
        val alert: AlertDialog = alertDialogBuilder.create()
        alert.show()
    }

    private fun getAllDrivers() {
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                allDrivesList = obj as MutableList<AllDriversModel>?
                Log.i("allDrivesList", "Log getAllDrivers" + allDrivesList?.get(0)!!.fullName)
                AddDriversToMap()

            }
        }).getAllDrivers()



    }
    private fun AddDriversToMap() {
        map?.clear()
        markers!!.clear()
        for (i in allDrivesList?.indices!!) {

            val allDriversModel: AllDriversModel = allDrivesList!!.get(i)

            markers!!.add(
                createMarker(
                    allDriversModel.getLat(),
                    allDriversModel.getLng(),
                    allDriversModel.getFullName(), allDriversModel.getAddress(),
                    R.drawable.ic_map_driver
                )!!
            )
        }
        markers!!.add(
            createMarker(
              selectedDestinationLat,
               selectedDestinationLng,
               getString(R.string.destination_location), MapHandler.getGpsAddress(getActiviy(),selectedDestinationLat,selectedDestinationLng),
                R.drawable.ic_map_destination_marker
            )!!
        )


    }

    fun createMarker(
        latitude: Double,
        longitude: Double,
        title: String?,
        snippet: String?,
        iconResID: Int
    ): Marker? {
        return map!!.addMarker(
            MarkerOptions().position(LatLng(latitude, longitude)).anchor(0.5f, 0.5f).title(title)
                .snippet(snippet).icon(
                    BitmapDescriptorFactory.fromBitmap(
                        ImageHandler.getBitmap(
                            getActiviy(), iconResID
                        )
                    )
                )
        )

    }


    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? {
        val background = ContextCompat.getDrawable(context, R.drawable.ic_map_marker)
        background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable!!.setBounds(
            40,
            20,
            vectorDrawable.intrinsicWidth + 40,
            vectorDrawable.intrinsicHeight + 20
        )
        val bitmap = Bitmap.createBitmap(
            background.intrinsicWidth,
            background.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        background.draw(canvas)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


}
