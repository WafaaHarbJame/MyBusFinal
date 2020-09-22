package com.elaj.patient.spichalViews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.elaj.patient.classes.Constants
import com.google.android.material.textfield.TextInputLayout

class STextInputLayout : TextInputLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() { //        String name = context.getResources().getString(R.s)
        val typeface = Typeface.createFromAsset(
            context.assets,
            Constants.NORMAL_FONT
        )
        setTypeface(typeface)
    }
}
