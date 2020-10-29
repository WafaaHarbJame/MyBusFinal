package com.mybus.mybusapp.activities

import android.os.Bundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.ImageHandler
import com.mybus.mybusapp.Utils.MapHandler
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.models.MemberModel
import java.util.*

class RequestDetailsActivity : ActivityBase(), OnMapReadyCallback {

    var map: GoogleMap? = null
    var fragment: SupportMapFragment? = null
    var cameraUpdate: CameraUpdate? = null
    var zoomLevel = 12f
    private var latLng: LatLng? = null
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
        title = getString(R.string.order_details)

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


    }

    private fun getOrderTracking() {

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
                lat,
                lng,
                getString(R.string.driver_location),
                MapHandler.getGpsAddress(getActiviy(), driverlat, driverlng),
                R.drawable.ic_map_driver
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

        if (map != null) {
            val latLn = LatLng(driverlat, driverlng)
            val cameraUpdate =
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        latLn,
                        zoomLevel
                    )
                );
            map?.moveCamera(cameraUpdate)
        }


    }

    private fun getData(driverId: String) {
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                if (func == Constants.SUCCESS) {
                    val user: MemberModel = obj as MemberModel
                    driverlat = user.lat
                    driverlng = user.lng

                }

            }
        }).getMyAccount(driverId)

    }

}