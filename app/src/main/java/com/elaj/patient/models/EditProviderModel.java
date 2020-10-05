package com.elaj.patient.models;

import java.util.List;

/**
 * Created by wokman on 5/7/2017.
 */

public class EditProviderModel {

    public int id;
    public String fullName;
    public int countryCode;
    public String mobile;
    public String email;
    public int cityId;
    public String briefDetailsAr;
    public String briefDetailsEn;
    public String addressAr;
    public String addressEn;
    public double lat;
    public double lng;
    public String photosListIds;
    public List<WorkingHoursModel> workHourList;
    public List<ServiceModel> servicesList;


}
