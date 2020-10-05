package com.elaj.patient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ameer on 3/28/2018.
 */

public class AllChatsModel {

    @SerializedName("current_page")
    @Expose
    public int currentPage;
    @SerializedName("items")
    @Expose
    public List<ChatModel> items = null;
    @SerializedName("first_page_url")
    @Expose
    public String firstPageUrl;
    @SerializedName("from")
    @Expose
    public int from;
    @SerializedName("last_page")
    @Expose
    public int lastPage;
    @SerializedName("last_page_url")
    @Expose
    public String lastPageUrl;
    @SerializedName("next_page_url")
    @Expose
    public Object nextPageUrl;
    @SerializedName("per_page")
    @Expose
    public int perPage;
    @SerializedName("prev_page_url")
    @Expose
    public Object prevPageUrl;
    @SerializedName("to")
    @Expose
    public int to;
    @SerializedName("total")
    @Expose
    public int total;

}
