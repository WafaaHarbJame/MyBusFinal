package com.elaj.patient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("slug")
    @Expose
    public String slug;
    @SerializedName("photo")
    @Expose
    public String photo;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("story")
    @Expose
    public String story;

}
