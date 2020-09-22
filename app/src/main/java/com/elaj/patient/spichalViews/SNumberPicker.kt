package com.elaj.patient.spichalViews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import com.elaj.patient.classes.Constants
import com.elaj.patient.R

class SNumberPicker : NumberPicker {
    var type: Typeface? = null

    constructor(context: Context?) : super(context) { //        init();
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) { //        init();
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) { //        init();
    }

    //    private void init() {
//        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "a_jannat_lt.otf");
//
////        setTypeface(typeface);
//    }
    override fun addView(child: View) {
        super.addView(child)
        updateView(child)
    }

    override fun addView(
        child: View, index: Int,
        params: ViewGroup.LayoutParams
    ) {
        super.addView(child, index, params)
        type = Typeface.createFromAsset(context.assets, Constants.NORMAL_FONT)
        updateView(child)
    }

    override fun addView(
        child: View,
        params: ViewGroup.LayoutParams
    ) {
        super.addView(child, params)
        type = Typeface.createFromAsset(context.assets, Constants.NORMAL_FONT)
        updateView(child)
    }

    private fun updateView(view: View) {
        if (view is EditText) {
            view.typeface = type
            //            ((EditText) view).setTextSize(25);
            view.setTextColor(ContextCompat.getColor(context, R.color.header2))
        }
    }
}
