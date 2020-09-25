package com.elaj.patient.activities

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elaj.patient.R
import com.franmontiel.localechanger.LocaleChanger
import com.jaeger.library.StatusBarUtil


open class ActivityBase : AppCompatActivity() {

    protected var isMainActivityBottomNav = false

    val visible = View.VISIBLE
    val gone = View.GONE
    val invisible = View.INVISIBLE

    protected lateinit var homeBtn: View

    //    lateinit var searchBtn: View
//    lateinit var shareBtn: View
//    protected lateinit var aTitle: TextView
//    protected lateinit var starBtn: TextView
    protected lateinit var mainTitle: TextView
    lateinit var mToolbar: RelativeLayout

    override fun attachBaseContext(base: Context?) {
//        var base: Context? = base
//       var base = LocaleChanger.configureBaseContext(base)
        super.attachBaseContext(LocaleChanger.configureBaseContext(base))
    }

    override fun onStart() {
        super.onStart()

//        if (!isProviderDetailsActivity)
//            StatusBarUtil.setTranslucent(this, 0)

    }

    override fun setTitle(title: CharSequence?) {
        mToolbar = findViewById(R.id.tool_bar)
        homeBtn = mToolbar.findViewById(R.id.homeBtn)
//        starBtn = mToolbar.findViewById(R.id.starBtn)
//        aTitle = mToolbar.findViewById(R.id.title)
        mainTitle = mToolbar.findViewById(R.id.mainTitleTxt)
//        searchBtn = mToolbar.findViewById(R.id.searchBtn)
//        shareBtn = mToolbar.findViewById(R.id.shareBtn)

//        aTitle.text = title
        mainTitle.text = title

        if (isMainActivityBottomNav) {
            mainTitle.visibility = View.VISIBLE
//            aTitle.visibility = View.GONE
            homeBtn.visibility = View.GONE
        } else {
            mainTitle.visibility = View.GONE
//            aTitle.visibility = View.VISIBLE
            homeBtn.visibility = View.VISIBLE
        }

        homeBtn.setOnClickListener { onBackPressed() }

        super.setTitle(title)
    }


    protected fun getActiviy(): Activity {
        return this
    }

    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager: InputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        } catch (e: Exception) { //            e.printStackTrace();
        }
    }

    fun showSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager: InputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        } catch (e: Exception) { //            e.printStackTrace();
        }
    }


    fun setupUI(view: View) { // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                //                    System.out.println("Log event Action " + event.getAction());
                if (event.action != MotionEvent.ACTION_SCROLL) {
                    hideSoftKeyboard(getActiviy())
                }
                false
            }
        }
        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    fun Toast(vararg msg: String?) {
        var msgs: String? = ""
        for (s in msg) {
            msgs += s
        }
        Toast.makeText(getActiviy(), msgs, Toast.LENGTH_SHORT).show()
    }


    fun Toast(resId: Int) {
        Toast.makeText(getActiviy(), getString(resId), Toast.LENGTH_SHORT).show()
    }


}