package com.mybus.mybusapp.models

import java.io.Serializable

class DriverModel : Serializable {
    var countryCode = 0
    var mobile: String? = null
    var mobileWithCountry: String? = null
    var password: String? = null
    var password_confirm: String? = null
    var isVerified = false
    var fullName: String? = null
    var email: String? = null
    var age = 0
    var birthDate: String? = ""
    var type = 0
    var lat = 0.0
    var lng = 0.0
    var address: String? = null
    var isDriverActive = false
    var busLoading = 0
    var emptySeat = 0
    var fillySeat = 0
    var busNumber = 0
    var busName: String? = ""
    var busModel: String? = ""
    var busColor: String? = ""
    var isSelectLocation = false
}
