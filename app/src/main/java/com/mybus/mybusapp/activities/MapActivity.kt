package com.mybus.mybusapp.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
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
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.models.DriverModel
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_map.*
import java.util.*

class MapActivity : ActivityBase(), OnMapReadyCallback {

    var map: GoogleMap? = null
    var fragment: SupportMapFragment? = null
    var cameraUpdate: CameraUpdate? = null
    var zoomLevel = 12f

    private var latLng: LatLng? = null
    private var mapType: Int = 0
    private var selectedLat = 24.7096549
    private var selectedLng = 46.6748917

    var isGrantPermission = false

    var allDrivesList: MutableList<DriverModel>? = null

    var markers: MutableList<Marker>? = null


    companion object {
        const val REQUEST_SELECT_LOCATION = 122
        const val REQUEST_SELECT_DESTINATION_LOCATION = 123

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        markers = ArrayList()
        latLng = LatLng(selectedLat, selectedLng)

        val bundle = intent.extras;

        if (bundle != null) {
            mapType = bundle.getInt(Constants.KEY_MAP_TYPE)!!
            selectedLat = bundle.getDouble(Constants.KEY_LAT)
            selectedLng = bundle.getDouble(Constants.KEY_LNG)
        }

        if (mapType == 1) {
            selectBtn.visibility = visible
            checkLocationPermission()
        } else if (mapType == 2) {
            selectBtn.visibility = View.VISIBLE
            selectBtn.text = getString(R.string.edit_Location)

        } else if (mapType == 3) {
            selectDestinationBtn.visibility = View.VISIBLE
        }

        backBtn.setOnClickListener {
            onBackPressed()
        }


        val fm: FragmentManager = supportFragmentManager
        fragment = fm.findFragmentById(R.id.map) as SupportMapFragment
        fragment?.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap

        cameraUpdate =
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(
                    latLng,
                    zoomLevel
                )
            )
        map?.moveCamera(cameraUpdate)

        var mapMarker = 0
        var markerTitle = ""

        when (mapType) {
            1 -> {
                if (isGrantPermission)
                    getMyLocation()

                mapMarker = R.drawable.ic_map_marker
                markerTitle = getString(R.string.my_location)

            }
            2 -> {

                mapMarker = R.drawable.ic_map_marker
                markerTitle = getString(R.string.my_location)

            }
            3 -> {

                mapMarker = R.drawable.ic_map_destination_marker
                markerTitle = getString(R.string.destination_location)

            }
            else -> {
                mapMarker = R.drawable.ic_map_marker
                markerTitle = getString(R.string.my_location)
            }
        }

        map?.addMarker(
            MarkerOptions()
                .position(latLng!!)
                .icon(
                    BitmapDescriptorFactory.fromBitmap(
                        ImageHandler.getBitmap(
                            getActiviy(), mapMarker
                        )
                    )
                )
                .title(markerTitle)

        )

        map?.setOnMapClickListener {

            map?.clear()
            map?.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(it.latitude, it.longitude)
                    )
                    .icon(
                        BitmapDescriptorFactory.fromBitmap(
                            ImageHandler.getBitmap(
                                getActiviy(), mapMarker
                            )
                        )
                    )
                    .title(markerTitle)

            )
            selectedLat = it.latitude
            selectedLng = it.longitude

        }

        myLocationBtn.setOnClickListener {

            checkLocationPermission()

        }

        selectBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Constants.KEY_LAT, selectedLat)
            intent.putExtra(Constants.KEY_LNG, selectedLng)
            setResult(Activity.RESULT_OK, intent)
            finish()

        }


        selectDestinationBtn.setOnClickListener {

            val intent = Intent()
            intent.putExtra(Constants.KEY_DESTINATION_LAT, selectedLat)
            intent.putExtra(Constants.KEY_DESTINATION_LNG, selectedLng)
            setResult(Activity.RESULT_OK, intent)
            finish()

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

    private fun EditMyLocation(lat: Double, lng: Double) {
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
                        map?.clear()
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
                        )
                    map?.animateCamera(cameraUpdate)
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
            loadingLocationLY.visibility = View.VISIBLE
            SmartLocation.with(getActiviy()).location()
                .oneFix()
                .start { location ->
                    loadingLocationLY.visibility = View.GONE

                    val lat: Double = location.latitude
                    val lng: Double = location.longitude
                    map?.clear()
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
                allDrivesList = obj as MutableList<DriverModel>?
                initAdapter()
            }
        }).getAllDrivers()
    }

    private fun initAdapter() {
        map?.clear()
        markers!!.clear()
        for (i in allDrivesList?.indices!!) {

            val driverModel: DriverModel = allDrivesList!!.get(i)

            markers!!.add(
                createMarker(
                    driverModel.lat,
                    driverModel.lng,
                    driverModel.fullName, driverModel.address,
                    R.drawable.ic_map_driver
                )!!
            )
        }
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
                .snippet(snippet).icon(bitmapDescriptorFromVector(this, iconResID))
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
