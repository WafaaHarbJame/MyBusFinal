package com.mybus.mybusapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllDriversModel {
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

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileWithCountry() {
        return mobileWithCountry;
    }

    public void setMobileWithCountry(String mobileWithCountry) {
        this.mobileWithCountry = mobileWithCountry;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirm() {
        return password_confirm;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDriverActive() {
        return isDriverActive;
    }

    public void setDriverActive(boolean driverActive) {
        isDriverActive = driverActive;
    }

    public int getBusLoading() {
        return busLoading;
    }

    public void setBusLoading(int busLoading) {
        this.busLoading = busLoading;
    }

    public int getEmptySeat() {
        return emptySeat;
    }

    public void setEmptySeat(int emptySeat) {
        this.emptySeat = emptySeat;
    }

    public int getFillySeat() {
        return fillySeat;
    }

    public void setFillySeat(int fillySeat) {
        this.fillySeat = fillySeat;
    }

    public boolean isSelectLocation() {
        return isSelectLocation;
    }

    public void setSelectLocation(boolean selectLocation) {
        isSelectLocation = selectLocation;
    }
}
