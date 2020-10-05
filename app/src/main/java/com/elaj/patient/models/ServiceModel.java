package com.elaj.patient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    public String serviceName;
    @SerializedName("photo")
    @Expose
    public String servicePhoto;
    @SerializedName("price")
    @Expose
    public int servicePrice;
    @SerializedName("amount")
    @Expose
    public int amount;

    public ServiceModel(int id, String service_name, String service_photo, int service_price) {
        this.id = id;
        this.serviceName = service_name;
        this.servicePhoto = service_photo;
        this.servicePrice = service_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String service_name) {
        this.serviceName = service_name;
    }

    public String getServicePhoto() {
        return servicePhoto;
    }

    public void setServicePhoto(String service_photo) {
        this.servicePhoto = service_photo;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public double getAmount() {
        return amount;
    }

    public void setServicePrice(int service_price) {
        this.servicePrice = service_price;
    }

    @Override
    public String toString() {

        return "serviceId: " + id + " serviceName: " + serviceName + " price: " + servicePrice;
    }
}
