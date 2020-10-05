package com.elaj.patient.models;

import com.elaj.patient.classes.GlobalData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("code")
    @Expose
    public int countryCode;
    @SerializedName("lat")
    @Expose
    public double lat;
    @SerializedName("lng")
    @Expose
    public double lng;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("photo")
    @Expose
    public String photo;

    public String getPhoto() {

        return photo.startsWith("http") ? photo : GlobalData.ImageURL + photo;
    }
}
