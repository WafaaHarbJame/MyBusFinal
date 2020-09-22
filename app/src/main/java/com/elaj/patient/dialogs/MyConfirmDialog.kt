package com.elaj.patient.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.elaj.patient.R

class MyConfirmDialog(
    context: Context?,
    message: String,
    okStr: Int,
    cancelStr: Int,
    okCall: Click?,
    cancelCall: Click?
) :
    Dialog(context!!) {

    var messageTxt: TextView
    var cancelBtn: TextView
    var okBtn: TextView
    var activity: Activity? = context as Activity?

    abstract class Click {
        abstract fun click()
    }

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.dialog_my_confirm)
        setCancelable(false)

        messageTxt = findViewById(R.id.messageTxt)
        cancelBtn = findViewById(R.id.cancelBtn)
        okBtn = findViewById(R.id.okBtn)

        messageTxt.text = message

        if (okStr != 0) okBtn.setText(okStr)
        if (cancelStr != 0) cancelBtn.setText(cancelStr)
        okBtn.setOnClickListener {
            okCall?.click()
            dismiss()
        }
        cancelBtn.setOnClickListener {
            cancelCall?.click()
            dismiss()
        }

        try {
            if (activity != null && !activity!!.isFinishing) show()
        } catch (e: Exception) {
            dismiss()
        }


    }


    fun setDismissListener(listener: DialogInterface.OnDismissListener?) {
        setOnDismissListener(listener)
    }


}