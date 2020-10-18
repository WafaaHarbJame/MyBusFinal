package com.elaj.patient.spichalViews

import android.content.Context
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ListView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.ContextCompat
import com.elaj.patient.R
import com.elaj.patient.classes.Constants

class AutoCompleteDropDown : AppCompatAutoCompleteTextView {
    //    implements AdapterView.OnItemClickListener
    val MAX_CLICK_DURATION = 200
    val startClickTime: Long = 0
    var isPopup = false
    val mPosition = ListView.INVALID_POSITION

    constructor(context: Context) : super(context) {

        init()
//        setOnItemClickListener(this);
    }

    constructor(
        arg0: Context,
        arg1: AttributeSet?
    ) : super(arg0, arg1) {

        init()
//        setOnItemClickListener(this);
    }

    constructor(
        arg0: Context,
        arg1: AttributeSet?,
        arg2: Int
    ) : super(arg0, arg1, arg2) {

        init()
//        setOnItemClickListener(this);
    }

    open fun init() {
        val typeface =
            Typeface.createFromAsset(context.assets, Constants.NORMAL_FONT)
        setTypeface(typeface)
    }

    override fun enoughToFilter(): Boolean {
        return true
    }

    override fun onFocusChanged(
        focused: Boolean, direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            try {
                performFiltering("", 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
            keyListener = null
            dismissDropDown()
        } else {
            isPopup = false
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                if (isPopup) {
                    dismissDropDown()
                } else {
                    try {
                        requestFocus()
                        showDropDown()
                    } catch (e: Exception) {
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun showDropDown() {
        super.showDropDown()
        isPopup = true
    }

    override fun dismissDropDown() {
        super.dismissDropDown()
        isPopup = false
    }


    fun getPosition(): Int {
        return mPosition
    }
}