package com.elaj.patient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name_ar")
    @Expose
    public String nameAr;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("photo")
    @Expose
    public String photo;


}
