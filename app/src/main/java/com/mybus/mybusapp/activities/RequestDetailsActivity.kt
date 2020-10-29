package com.mybus.mybusapp.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
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
import com.mybus.mybusapp.models.MemberModel
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_map.*
import java.util.*

class RequestDetailsActivity : ActivityBase(), OnMapReadyCallback {

    var map: GoogleMap? = null
    var fragment: SupportMapFragment? = null
    var zoomLevel = 12f
    var isGrantPermission = false
    private var destinationLat = 0.0
    private var destinationLng = 0.0
    private var lat = 0.0
    private var lng = 0.0
    private var driverlat = 0.0
    private var driverlng = 0.0
    private var driverId: String? = null
    var markers: MutableList<Marker>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_details)

        markers = ArrayList()

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

        }
        getData(driverId!!)

        myLocationBtn.setOnClickListener {
            checkLocationPermission()
        }



    }

    private fun getOrderTracking() {
        if(map!=null){

            markers!!.add(
                createMarker(
                    lat,
                    lng,
                    getString(R.string.my_location), MapHandler.getGpsAddress(getActiviy(), lat, lng),
                    R.drawable.ic_map_marker
                )!!
            )

            markers!!.add(
                createMarker(
                    destinationLat,
                    destinationLng,
                    getString(R.string.destination_location),
                    MapHandler.getGpsAddress(getActiviy(), destinationLat, destinationLng),
                    R.drawable.ic_map_destination_marker
                )!!
            )

            markers!!.add(
                createMarker(
                    driverlat,
                    driverlng,
                    getString(R.string.driver_location),
                    MapHandler.getGpsAddress(getActiviy(), driverlat, driverlng),
                    R.drawable.ic_map_driver
                )!!
            )
            val latLn = LatLng(driverlat, driverlng)
            val cameraUpdate =
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        latLn,
                        zoomLevel
                    )
                );
            map?.animateCamera(cameraUpdate)

        }

    }

    fun createMarker(
        latitude: Double,
        longitude: Double,
        title: String?,
        snippet: String?,
        iconResID: Int
    ): Marker? {
        return map?.addMarker(
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

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap

//        if (map != null) {
//
//            map?.addMarker(
//                MarkerOptions()
//                    .position(
//                        LatLng(lat, lng)
//                    )
//                    .icon(
//                        BitmapDescriptorFactory.fromBitmap(
//                            ImageHandler.getBitmap(
//                                getActiviy(), R.drawable.ic_map_marker
//                            )
//                        )
//                    )
//                    .title(getString(R.string.my_location))
//            )
//
//
//            map?.addMarker(
//                MarkerOptions()
//                    .position(
//                        LatLng(destinationLat, destinationLng)
//                    )
//                    .icon(
//                        BitmapDescriptorFactory.fromBitmap(
//                            ImageHandler.getBitmap(
//                                getActiviy(), R.drawable.ic_map_destination_marker
//                            )
//                        )
//                    )
//                    .title(getString(R.string.destination_location))
//            )
//
//            map?.addMarker(
//                MarkerOptions()
//                    .position(
//                        LatLng(driverlat, driverlng)
//                    )
//                    .icon(
//                        BitmapDescriptorFactory.fromBitmap(
//                            ImageHandler.getBitmap(
//                                getActiviy(), R.drawable.ic_map_driver
//                            )
//                        )
//                    )
//                    .title(getString(R.string.driver_location))
//            )
//
//
//            val latLn = LatLng(driverlat, driverlng)
//            val cameraUpdate =
//                CameraUpdateFactory.newCameraPosition(
//                    CameraPosition.fromLatLngZoom(
//                        latLn,
//                        zoomLevel
//                    )
//                );
//            map?.animateCamera(cameraUpdate)
//        }


    }

    private fun getData(driverId: String) {
        Log.i("TAG", "Log getData Driver")

        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                if (func == Constants.SUCCESS) {
                    val user: MemberModel = obj as MemberModel
                    driverlat = user.lat
                    driverlng = user.lng

                }

            }
        }).getMyAccount(driverId)

        getOrderTracking()
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




}