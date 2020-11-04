package com.mybus.mybusapp.models;

public class PayWayImage {

    private int imgResource;
    private String payName;

    public PayWayImage(int imgResource, String payName) {
        this.imgResource = imgResource;
        this.payName = payName;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }
}
