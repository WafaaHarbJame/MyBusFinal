package com.elaj.patient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wokman on 4/27/2017.
 */

public class ConfigModel {

    @SerializedName("client_order_fee")
    @Expose
    public double appRatio;
    @SerializedName("about_app_page")
    @Expose
    public PageModel aboutAppPage;
//    @SerializedName("social_media")
//    @Expose
//    public SocialMedia socialMedia;
//    @SerializedName("app_link")
//    @Expose
//    public AppLink appLink;
//    @SerializedName("cities")
//    @Expose
//    public List<CityModel> cities = null;
    @SerializedName("countries")
    @Expose
    public List<CountryModel> countries = null;
}
