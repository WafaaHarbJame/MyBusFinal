package com.elaj.patient.Model;

import com.elaj.patient.Model.ErrorModel;

/**
 * Created by ameer on 2016-11-03.
 */
public class ResultAPIModel<T> {

//    public boolean success;
//    public List<String> message = new ArrayList<>();


//    @SerializedName("items")
//    @Expose
    public T data;
    public ErrorModel error;
    public String message;

}
