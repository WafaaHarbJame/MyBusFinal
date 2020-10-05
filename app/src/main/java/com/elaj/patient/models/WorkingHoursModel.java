package com.elaj.patient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WorkingHoursModel implements Serializable {

    //    private boolean isCheck;
    private int firstSeating;
    private int lastSeating;
    @SerializedName("day_number")
    @Expose
    private int dayNumber;
    @SerializedName("day_name")
    @Expose
    private String dayName;
    @SerializedName("time_start_at")
    @Expose
    private String fromTime;
    @SerializedName("time_end_at")
    @Expose
    private String toTime;
    private int active;


    public WorkingHoursModel(int dayNumber, String dayName, String fromTime, String toTime) {
        this.dayNumber = dayNumber;
        this.dayName = dayName;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }
//
//    public boolean isCheck() {
//        return isCheck;
//    }

//    public void setCheck(boolean check) {
//        isCheck = check;
//    }

    public String getDayName() {
        return dayName;
    }

    public boolean getStatus() {
        return active == 1;
    }

    public void setStatus(boolean status) {
        active = status ? 1 : 0;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setFromTime(String time) {
        fromTime = time;
    }

    public void setToTime(String time) {
        toTime = time;
    }

    @Override
    public String toString() {

        return "dayName: " + dayName + " - fromTime: " + fromTime + " - toTime: " + toTime + " - status: " + active;
    }

}
