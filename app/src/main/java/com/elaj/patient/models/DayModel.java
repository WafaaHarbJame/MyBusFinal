package com.elaj.patient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wokman on 5/7/2017.
 */

public class DayModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("date")
    @Expose
    public String date;

    public int getId() {

        return id;
    }

    public DayModel(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
