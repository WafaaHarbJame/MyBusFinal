package com.elaj.patient.spichalViews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.duolingo.open.rtlviewpager.RtlViewPager

class CustomViewPager : RtlViewPager {
    private var isPagingEnabled = true

    constructor(context: Context?) : super(context!!) { //        if (ViewCompat.getLayoutDirection(this.getRootView()) == ViewCompat.LAYOUT_DIRECTION_LTR) {
//            this.setScaleX(1);
//        } else {
//            this.setScaleX(-1);
//        }
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) { //        if (ViewCompat.getLayoutDirection(this.getRootView()) == ViewCompat.LAYOUT_DIRECTION_LTR) {
//            this.setScaleX(1);
//        } else {
//            this.setScaleX(-1);
//        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onInterceptTouchEvent(event)
    }

    fun setPagingEnabled(b: Boolean) {
        isPagingEnabled = b
    }
}