package com.elaj.patient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wokman on 5/7/2017.
 */

public class PayModel {

    @SerializedName("pay_url")
    @Expose
    public String payUrl;
    @SerializedName("successful_url")
    @Expose
    public String successUrl;
    @SerializedName("fail_url")
    @Expose
    public String failUrl;

}
