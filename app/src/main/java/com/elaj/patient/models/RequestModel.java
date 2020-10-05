package com.elaj.patient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wokman on 5/7/2017.
 */

public class RequestModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("salon_id")
    @Expose
    public int salonId;
    @SerializedName("client_id")
    @Expose
    public int clientId;
    @SerializedName("payment_id")
    @Expose
    public int paymentId;
    @SerializedName("lat")
    @Expose
    public double lat;
    @SerializedName("lng")
    @Expose
    public double lng;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("scheduled_at")
    @Expose
    public String scheduledAt;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("review_at")
    @Expose
    public String reviewAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("salon_name")
    @Expose
    public String salonName;
    @SerializedName("client_name")
    @Expose
    public String clientName;
    @SerializedName("salon_avatar")
    @Expose
    public String salonAvatar;
    @SerializedName("client_avatar")
    @Expose
    public String clientAvatar;
    @SerializedName("city_name")
    @Expose
    public String cityName;
    @SerializedName("day_name")
    @Expose
    public String dayName;
    @SerializedName("scheduled_date")
    @Expose
    public String scheduledDate;
    @SerializedName("scheduled_time")
    @Expose
    public String scheduledTime;
    @SerializedName("status_name")
    @Expose
    public String statusName;
    @SerializedName("services")
    @Expose
    public List<ServiceModel> services = null;

    public int getId() {

        return id;
    }

    public String getServicesNames() {

        String res = "";
        for (int i = 0; i < services.size(); i++) {
            if (i == services.size() - 1)
                res += services.get(i).getServiceName();
            else
                res += services.get(i).getServiceName().concat(",");
        }

        return res;
    }


    public double getServicesTotalPrice() {

        double res = 0;
        for (int i = 0; i < services.size(); i++) {

            res += services.get(i).getAmount();
        }

        return res;
    }

}
