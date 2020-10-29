package com.mybus.mybusapp.spichalViews

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class STabLayout : TabLayout {
    //    var tabTxtColor: Int
    var tabTxtSize = 15f

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    fun setTabTextColor(tabTextColor: Int) {
//        tabTxtColor = tabTextColor
    }

    fun setTabTextSize(tabTextSize: Float) {
        tabTxtSize = tabTextSize
    }

    override fun setupWithViewPager(viewPager: ViewPager?) {
        super.setupWithViewPager(viewPager)

    }


}