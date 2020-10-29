package com.mybus.mybusapp.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.mybus.mybusapp.R
import kotlinx.android.synthetic.main.dialog_confirm_send.*

class SuccessSendDialog(
    var activity: Activity?
) :
    Dialog(activity!!) {

    val dialog: SuccessSendDialog
        get() = this

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.dialog_confirm_send)

        dialog.window?.setGravity(Gravity.BOTTOM);
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;


        okBtn.setOnClickListener {

            dismiss()

        }


        try {
            if (activity != null && !activity!!.isFinishing) show()
        } catch (e: Exception) {
            dismiss()
        }


    }


}