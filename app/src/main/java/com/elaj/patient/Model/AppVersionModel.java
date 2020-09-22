package com.elaj.patient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ameer on 12/19/2017.
 */

public class AppVersionModel {

    @SerializedName("status")
    @Expose
    public Object status;
    @SerializedName("msg")
    @Expose
    public String msg;

    public boolean getStatus() {
        if (status instanceof Boolean) {
            return (boolean) status;
        } else if (status instanceof Double) {
            return ((double) status) == 1;
        } else if (status instanceof Integer) {
            return ((int) status) == 1;
        }
        return false;
    }

}
