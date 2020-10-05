package com.elaj.patient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wokman on 5/7/2017.
 */

public class SectionModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("count_view")
    @Expose
    private Object countView;
    @SerializedName("stage_id")
    @Expose
    private int stageId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status_msg")
    @Expose
    private int statusMsg;
    @SerializedName("status_msg_text")
    @Expose
    private Object statusMsgText;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("count_book")
    @Expose
    private int countBooks;

    public int getId() {
        return id;
    }

    public Object getCountView() {
        return countView;
    }


    public int getStageId() {
        return stageId;
    }


    public String getName() {
        return name;
    }


    public int getStatusMsg() {
        return statusMsg;
    }



    public Object getStatusMsgText() {
        return statusMsgText;
    }


    public int getStatus() {
        return status;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public int getCountBooks() {
        return countBooks;
    }

}
