package com.elaj.patient.spichalViews

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class STabLayout : TabLayout {
    //    var tabTxtColor: Int
    var tabTxtSize = 15f

    constructor(context: Context) : super(context) {
//        tabTxtColor = ContextCompat.getColor(context!!, R.color.colorPrimary)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
//        tabTxtColor = ContextCompat.getColor(context!!, R.color.colorPrimary)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
//        tabTxtColor = ContextCompat.getColor(context, R.color.colorPrimary)
    }

    fun setTabTextColor(tabTextColor: Int) {
//        tabTxtColor = tabTextColor
    }

    fun setTabTextSize(tabTextSize: Float) {
        tabTxtSize = tabTextSize
    }

    override fun setupWithViewPager(viewPager: ViewPager?) {
        super.setupWithViewPager(viewPager)
//        val typeface =
//            Typeface.createFromAsset(context.assets, Constants.NORMAL_FONT)
//        removeAllTabs()
//        val slidingTabStrip = getChildAt(0) as ViewGroup
//        var i = 0
//        val count = viewPager!!.adapter!!.count
//        while (i < count) {
//            val tab = newTab()
//            val customTabLY =
//                LayoutInflater.from(context).inflate(R.layout.tab_item, null) as LinearLayout
//            val sTextViewBold = customTabLY.findViewById<View>(android.R.id.text1) as TextView
////            sTextViewBold.setTextColor(tabTxtColor)
//            sTextViewBold.textSize = tabTxtSize
//            tab.customView = customTabLY
//            this.addTab(tab.setText(viewPager.adapter!!.getPageTitle(i)))
//            val view =
//                (slidingTabStrip.getChildAt(i) as ViewGroup).getChildAt(1) as AppCompatTextView
//            view.typeface = typeface
//            i++
//        }
    }

//    override fun setupWithViewPager(viewPager: ViewPager?) {
//        super.setupWithViewPager(viewPager)
//        val typeface =
//            Typeface.createFromAsset(context.assets, Constants.NORMAL_FONT)
//        removeAllTabs()
//        val slidingTabStrip = getChildAt(0) as ViewGroup
//        var i = 0
//        val count = viewPager!!.adapter!!.count
//        while (i < count) {
//            val tab = newTab()
//            val customTabLY =
//                LayoutInflater.from(context).inflate(R.layout.tab_item, null) as LinearLayout
//            val sTextViewBold = customTabLY.findViewById<View>(android.R.id.text1) as TextView
//            sTextViewBold.setTextColor(tabTxtColor)
//            sTextViewBold.textSize = tabTxtSize
//            tab.customView = customTabLY
//            this.addTab(tab.setText(viewPager.adapter!!.getPageTitle(i)))
//            val view =
//                (slidingTabStrip.getChildAt(i) as ViewGroup).getChildAt(1) as AppCompatTextView
//            view.typeface = typeface
//            i++
//        }
//    }
}