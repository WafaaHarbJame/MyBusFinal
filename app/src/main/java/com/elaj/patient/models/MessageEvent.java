package com.elaj.patient.models;

/**
 * Created by ameer on 6/6/2017.
 */

public class MessageEvent {

    public static final String TYPE_PAGER = "pager";
    public static final String TYPE_DATA = "data";
    public static final String TYPE_POSITION = "position";
    public static final String TYPE_CHAT = "chat";
    public static final String TYPE_COUNTER = "counter";
    public static final String TYPE_VISIBILITY = "visibility";

    //    public int PagerPosition;
    public Object data;
    public String type;

    public MessageEvent(String type, Object msgData) {
        this.data = msgData;
        this.type = type;
    }

    public MessageEvent(String type) {
        this.type = type;
    }

    public MessageEvent() {
    }

}
