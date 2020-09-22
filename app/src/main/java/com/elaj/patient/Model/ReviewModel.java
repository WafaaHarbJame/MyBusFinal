package com.elaj.patient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wokman on 5/7/2017.
 */

public class ReviewModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("salon_id")
    @Expose
    public int salonId;
    @SerializedName("client_id")
    @Expose
    public int clientId;
    @SerializedName("order_id")
    @Expose
    public int orderId;
    @SerializedName("stars")
    @Expose
    public int stars;
    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("client_name")
    @Expose
    public String clientName;
    @SerializedName("client_avatar")
    @Expose
    public String clientAvatar;


    public int getId() {

        return id;
    }

}
