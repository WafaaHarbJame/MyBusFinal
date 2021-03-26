package com.mybus.mybusapp.models

import java.io.Serializable

class MemberModel : Serializable {

    var id = 0
    var countryCode = 0
    var mobile: String? = null
    var type = 0
    var mobileWithCountry: String? = null
    var fullName: String? = null
    var password: String? = null
    var password_confirm: String? = null
    var isVerified: Any? = null
    var birthDate :String = ""
    var age :Int = 0
    var lat = 0.0
    var lng = 0.0
    var address: String? = null
    var isDriverActive: Any? = null
    var busLoading :Int = 0
    var emptySeat :Int = 0
    var fillySeat :Int = 0
    var isSelectLocation: Any? = ""
    var busNumber = 0
    var busName: String? = ""
    var busColor: String? = ""
    var busModel: String? = ""
    var email: String? = ""


    fun getIsVerified(): Boolean {

        if (isVerified is Boolean)
            return isVerified as Boolean
        else if (isVerified is Double) {
            val value = isVerified as Double
            return value.equals(1.0)
        }
        return false
    }


    fun getIsSelectLocation(): Boolean {

        if (isSelectLocation is Boolean)
            return isSelectLocation as Boolean
        else if (isSelectLocation is Double) {
            val value = isSelectLocation as Double
            return value.equals(1.0)
        }
        return false
    }

    fun getIsDriverActive(): Boolean {

        if (isDriverActive is Boolean)
            return isDriverActive as Boolean
        else if (isDriverActive is Double) {
            val value = isDriverActive as Double
            return value.equals(1.0)
        }
        return false
    }

}