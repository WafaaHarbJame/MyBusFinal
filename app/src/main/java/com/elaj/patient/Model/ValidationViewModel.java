package com.elaj.patient.Model;

import android.view.View;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wokman on 5/7/2017.
 */

public class ValidationViewModel {


    public View view;
    public int errorResMsg;

    public ValidationViewModel(View view, int errorResMsg) {
        this.view = view;
        this.errorResMsg = errorResMsg;
    }

}
