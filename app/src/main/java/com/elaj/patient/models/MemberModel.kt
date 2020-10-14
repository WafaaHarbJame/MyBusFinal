package com.elaj.patient.models

import java.io.Serializable

class MemberModel : Serializable {

    var id = 0
    var countryCode = 0
    var mobile: String? = null
    var type = 0
    var avatar: String? = null
    var countryId = 0
    var lang: String? = null
    var createdAt: String? = null
    var mobileWithPlus: String? = null
    var fcm_token: String? = null
    var password: String? = null
    var isVerified: Boolean = false


}