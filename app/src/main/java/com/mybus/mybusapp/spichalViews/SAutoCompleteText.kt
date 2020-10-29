package com.mybus.mybusapp.spichalViews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.mybus.mybusapp.classes.Constants

class SAutoCompleteText : AppCompatAutoCompleteTextView {
    private var contextt: Context

    constructor(context: Context) : super(context) {
        this.contextt = context
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        this.contextt = context
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        this.contextt = context
        init()
    }

    private fun init() { //        String name = context.getResources().getString(R.s)
        val typeface =
            Typeface.createFromAsset(context.assets, Constants.NORMAL_FONT)
        setTypeface(typeface)
    }
}