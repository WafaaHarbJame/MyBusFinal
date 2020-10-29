package com.mybus.mybusapp.fragments

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

open class FragmentBase : Fragment() {

    val visible = View.VISIBLE
    val gone = View.GONE


    fun setupUI(view: View) { // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v: View?, event: MotionEvent ->
                //                    System.out.println("Log event Action " + event.getAction());
                if (event.action != MotionEvent.ACTION_SCROLL) {
                    hideSoftKeyboard(activity)
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

    fun Toast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    fun Toast(resId: Int) {
        Toast.makeText(
            activity,
            requireActivity().getString(resId),
            Toast.LENGTH_SHORT
        ).show()
    } //    public void saveCart() {

    //        SharedPManger sharedPManger = new SharedPManger(getActivity());
//        String cartJson = new Gson().toJson(GlobalData.CartList);
//
//        sharedPManger.SetData(GlobalData.KEY_CART, cartJson);
//    }
    companion object {
        fun hideSoftKeyboard(activity: Activity?) {
            try {
                val inputMethodManager =
                    activity!!.getSystemService(
                        Activity.INPUT_METHOD_SERVICE
                    ) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken, 0
                )
            } catch (e: Exception) { //            e.printStackTrace();
            }
        }
    }
}