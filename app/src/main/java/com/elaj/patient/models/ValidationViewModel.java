package com.elaj.patient.models;

import android.view.View;

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
