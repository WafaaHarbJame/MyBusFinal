package com.elaj.patient.Model;

import com.elaj.patient.classes.Constants;
import com.elaj.patient.classes.UtilityApp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wokman on 5/7/2017.
 */

public class ProviderModel implements Serializable {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("country_code")
    @Expose
    public String countryCode;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile_verified_at")
    @Expose
    public Object mobileVerifiedAt;
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
    public int active;
    @SerializedName("country_id")
    @Expose
    public Object countryId;
    @SerializedName("city_id")
    @Expose
    public int cityId;
    @SerializedName("lat")
    @Expose
    public double lat;
    @SerializedName("lng")
    @Expose
    public double lng;
    @SerializedName("address_ar")
    @Expose
    public String addressAr;
    @SerializedName("address_en")
    @Expose
    public String addressEn;
    @SerializedName("lang")
    @Expose
    public String lang;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("orders_count")
    @Expose
    public int ordersCount;
    @SerializedName("is_favored")
    @Expose
    public int isFavored;
    @SerializedName("reviews_count")
    @Expose
    public int reviewsCount;
    @SerializedName("reviews_avg")
    @Expose
    public int reviewsAvg;
    @SerializedName("brief_summary_ar")
    @Expose
    public String briefSummaryAr;
    @SerializedName("brief_summary_en")
    @Expose
    public String briefSummaryEn;
    @SerializedName("services")
    @Expose
    private List<ServiceModel> services = null;
    @SerializedName("shift_hours")
    @Expose
    private List<WorkingHoursModel> shiftHours = null;
    //    @SerializedName("gallery")
//    @Expose
//    public List<String> gallery = null;
    @SerializedName("city_name")
    @Expose
    public String cityName;
    @SerializedName("gallery")
    @Expose
    private List<GalleryModel> gallery;

    public int getId() {

        return id;
    }

    public String getName() {
        return fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCover() {
        return cover;
    }

    public String getCity() {
        return cityName;
    }

    public String getNotes() {
        return UtilityApp.INSTANCE.getLanguage() == Constants.Arabic ? briefSummaryAr : briefSummaryEn;
    }

    public String getAddress() {
        return UtilityApp.INSTANCE.getLanguage() == Constants.Arabic ? addressAr : addressEn;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getCompletedRequest() {
        return ordersCount;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

//    public String getPriceRatingDollars() {
//        switch (priceRating) {
//            case 1:
//                return "$";
//            case 2:
//                return "$$";
//            case 3:
//                return "$$$";
//            case 4:
//                return "$$$$";
//            case 5:
//                return "$$$$$";
//            default:
//                return "-";
//        }
//    }

    public float getRating() {
        return reviewsAvg;
    }

    public boolean getIsFavored() {
        return isFavored == 1;
    }

    public List<ServiceModel> getServices() {
        return services;
    }

    public List<GalleryModel> getGallery() {
        return gallery;
    }

//    public List<String> getGallery() {
//        return gallery;
//    }

    public List<WorkingHoursModel> getWorkHours() {
        return shiftHours;
    }
}
