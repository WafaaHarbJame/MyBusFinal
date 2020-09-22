//package com.elaj.patient.dialogs
//
//import android.app.Activity
//import android.app.Dialog
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.view.Window
//import com.elaj.patient.R
//
//class SuccessDialog(
//    var activity: Activity?
//) :
//    Dialog(activity!!) {
//    private val dialog: SuccessDialog
//        get() = this
//
//    init {
//        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        requestWindowFeature(Window.FEATURE_NO_TITLE) //before
//        //        setTitle(title);
//        setContentView(R.layout.dialog_my_success)
//
//
//        try {
//            if (activity != null && !activity!!.isFinishing) show()
//        } catch (e: Exception) {
//            dismiss()
//        }
//    }
//
//}