package com.elaj.patient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GalleryModel implements Serializable {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("photo")
    @Expose
    public String photo_url;
    @SerializedName("type")
    @Expose
    public String type;

//    public GalleryModel(int id, String photo_url) {
//        this.id = id;
//        this.photo_url = photo_url;
//    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getPhoto_url() {
//        return photo_url;
//    }
//
//    public void setPhoto_url(String photo_url) {
//        this.photo_url = photo_url;
//    }
}
