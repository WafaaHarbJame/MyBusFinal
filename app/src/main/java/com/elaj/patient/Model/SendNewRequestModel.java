package com.elaj.patient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wokman on 5/7/2017.
 */

public class SendNewRequestModel {

    public int providerId;
//    public String requestLocation;
    public String address;
    public double lat;
    public double lng;
//    public String day;
//    public String time;
    public long scheduledAt;
    public String servicesIdList;
    public int paymentType;


}
