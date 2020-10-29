package com.mybus.mybusapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ameer on 1/9/2018.
 */

public class RegisterUserModel implements Serializable {

    public int countryCode;
    public String mobile;
    public String mobileWithCountry;
    public String password;
    public String password_confirm;
    public boolean isVerified;
    public String fullName;
    public int age;
    public int type;
    public double lat;
    public double lng;
    public String address;
    public boolean isDriverActive;
    public  int busLoading;
    public int emptySeat;
    public int fillySeat;
    public boolean isSelectLocation;
    public int busNumber;
    public String busName;
    public String busColor;
    public String busModel;




}
