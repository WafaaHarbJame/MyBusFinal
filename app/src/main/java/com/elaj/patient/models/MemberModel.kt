package com.elaj.patient.models

import java.io.Serializable

class MemberModel : Serializable {

    var id = 0
    var countryCode = 0
    var mobile: String? = null
    var type = 0
    var lang: String? = null
    var createdAt: String? = null
    var mobileWithCountry: String? = null
    var fcm_token: String? = null
    var password: String? = null
    var isVerified: Any? = null

    fun getIsVerified(): Boolean {

        if (isVerified is Boolean)
            return isVerified as Boolean
        else if (isVerified is Double) {
            val value = isVerified as Double
            return value.equals(1.0)
        }
        return false
    }


}