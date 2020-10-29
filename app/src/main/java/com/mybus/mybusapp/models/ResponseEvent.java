package com.mybus.mybusapp.models;

/**
 * Created by ameer on 6/6/2017.
 */

public class ResponseEvent {

    public Object data;
    public String type;
    public String api;

    public ResponseEvent(String api, String type, Object msgData) {
        this.api = api;
        this.type = type;
        this.data = msgData;
    }

    public ResponseEvent(String api, String type) {
        this.api = api;
        this.type = type;
    }

}
