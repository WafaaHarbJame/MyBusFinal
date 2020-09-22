package com.elaj.patient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wokman on 5/7/2017.
 */

public class TimeModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("time")
    @Expose
    public String time;

    public int getId() {

        return id;
    }

}
