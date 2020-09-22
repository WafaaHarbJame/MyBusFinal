package com.elaj.patient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wokman on 5/7/2017.
 */

public class SearchProvidersModel {

    public int serviceId;
    public String serviceName;
    public int cityId;
    public String cityName;
    public int minPrice;
    public int maxPrice;
    public double lat;
    public double lng;

}
