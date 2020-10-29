package com.mybus.mybusapp.spichalViews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.mybus.mybusapp.classes.Constants

class STextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        val typeface = Typeface.createFromAsset(
            context.assets,
            Constants.NORMAL_FONT
        )
        setTypeface(typeface)
    }
}