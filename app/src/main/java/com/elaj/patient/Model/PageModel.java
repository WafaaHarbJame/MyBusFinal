package com.elaj.patient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
