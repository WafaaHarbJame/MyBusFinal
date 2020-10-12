package com.elaj.patient.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MemberModel {
    @SerializedName("id")
    @Expose
    var id = 0

    //    public String getName() {
    //        return firstName.concat(" " + lastName);
    //    }
    @SerializedName("full_name")
    @Expose
    var name: String? = null

    @SerializedName("country_code")
    @Expose
    var countryCode = 0

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("mobile_verified_at")
    @Expose
    var mobileVerifiedAt: String? = null

    @SerializedName("is_completed_profile")
    @Expose
    private var isCompleteProfile = 0

    @SerializedName("api_token")
    @Expose
    var apiToken: String? = null

    @SerializedName("user_type")
    @Expose
    var type = 0

    @SerializedName("platform")
    @Expose
    var platform: Any? = null

    @SerializedName("avatar")
    @Expose
    var avatar: String? = null

    @SerializedName("cover")
    @Expose
    var cover: String? = null

    @SerializedName("active")
    @Expose
    private val active = false

    @SerializedName("country_id")
    @Expose
    var countryId = 0

    @SerializedName("city_id")
    @Expose
    var cityId = 0

    @SerializedName("country_name")
    @Expose
    var countryName: String? = null

    @SerializedName("city_name")
    @Expose
    var cityName: String? = null

    @SerializedName("gender")
    @Expose
    var gender: Any? = null

    @SerializedName("lang")
    @Expose
    var lang: String? = null

    @SerializedName("receive_notifications")
    @Expose
    var receiveNotifications = false

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("full_mobile")
    @Expose
    var fullMobile: String? = null

    @SerializedName("fcm_token")
    @Expose
    var fcmToken: String? = null
    var password: String? = null

    val isConfirmed: Boolean
        get() = mobileVerifiedAt != null

    fun isCompleteProfile(): Boolean {
        return isCompleteProfile == 1
    }

    fun setIsCompleteProfile() {
        isCompleteProfile = 1
    }
}