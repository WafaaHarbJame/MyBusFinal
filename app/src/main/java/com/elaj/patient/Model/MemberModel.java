package com.elaj.patient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("country_code")
    @Expose
    public int countryCode;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile_verified_at")
    @Expose
    public String mobileVerifiedAt;
    @SerializedName("is_completed_profile")
    @Expose
    private int isCompleteProfile = 0;
    @SerializedName("api_token")
    @Expose
    public String apiToken;
    @SerializedName("user_type")
    @Expose
    public int type;
    @SerializedName("platform")
    @Expose
    public Object platform;
    @SerializedName("avatar")
    @Expose
    public String avatar;
    @SerializedName("cover")
    @Expose
    public String cover;
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("country_id")
    @Expose
    public int countryId;
    @SerializedName("city_id")
    @Expose
    public int cityId;
    @SerializedName("country_name")
    @Expose
    public String countryName;
    @SerializedName("city_name")
    @Expose
    public String cityName;
    @SerializedName("gender")
    @Expose
    public Object gender;
    @SerializedName("lang")
    @Expose
    public String lang;
    @SerializedName("receive_notifications")
    @Expose
    public boolean receiveNotifications;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("full_mobile")
    @Expose
    public String fullMobile;
    @SerializedName("fcm_token")
    @Expose
    public String fcmToken;
    public String password;


//    public String getName() {
//        return firstName.concat(" " + lastName);
//    }

    public String getName() {
        return fullName;
    }

    public int getId() {
        return id;
    }

    public boolean isConfirmed() {
        return mobileVerifiedAt != null;
    }

    public boolean isCompleteProfile() {
        return isCompleteProfile == 1;
    }

    public void setIsCompleteProfile() {
        isCompleteProfile = 1;
    }


}
