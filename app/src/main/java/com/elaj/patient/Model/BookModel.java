package com.elaj.patient.Model;

import com.elaj.patient.Utils.NumberHandler;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wokman on 5/7/2017.
 */

public class BookModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("category_id")
    @Expose
    public int categoryId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("file")
    @Expose
    public String file;
    @SerializedName("photo")
    @Expose
    public String photo;
    @SerializedName("specialtie_name")
    @Expose
    public String sectionName;
    @SerializedName("rating")
    @Expose
    public Object rating;
    @SerializedName("sort")
    @Expose
    public int sort;
    @SerializedName("in_favorite")
    @Expose
    private Object isFavorite;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("share_url")
    @Expose
    public String shareUrl;

    public boolean getIsFavorite() {

        if (isFavorite instanceof Double)
            return (double) isFavorite == 1;
        else if (isFavorite instanceof Integer)
            return (int) isFavorite == 1;
        else if (isFavorite instanceof Boolean)
            return (boolean) isFavorite;
        else
            return false;

    }


}
