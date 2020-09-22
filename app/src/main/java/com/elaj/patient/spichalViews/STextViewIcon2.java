package com.elaj.patient.spichalViews;

import android.content.Context;
import android.graphics.Typeface;

import androidx.appcompat.widget.AppCompatTextView;

import android.util.AttributeSet;

import com.elaj.patient.classes.Constants;


/**
 * Created by ahmed barakat on 8/20/14.
 */
public class STextViewIcon2 extends AppCompatTextView {
    public STextViewIcon2(Context context) {
        super(context);
        init();
    }

    public STextViewIcon2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public STextViewIcon2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), Constants.ICON_OLD_AWSM_FONT);

        setTypeface(typeface);
    }
}
