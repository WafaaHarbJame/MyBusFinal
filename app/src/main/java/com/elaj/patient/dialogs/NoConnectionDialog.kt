package com.elaj.patient.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.LinearLayout
import com.elaj.patient.R

object NoConnectionDialog {
    //        Activity activity;
    var connectionDialog: ConnectionDialog? = null

    fun with(activity: Activity?): ConnectionDialog? {
        return if (connectionDialog == null) {
            connectionDialog = ConnectionDialog(activity)
            connectionDialog
        } else {
            connectionDialog
        }
    }

    class ConnectionDialog(activity: Activity?) :
        Dialog(activity!!) {
        private val okBtn: LinearLayout
        var activity: Activity? = null
        private val dialog: ConnectionDialog
            private get() = this

        init {
            //        this.activity = activity;
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE) //before
            setContentView(R.layout.dialog_no_connection)
            okBtn = findViewById(R.id.okBtn)
            okBtn.setOnClickListener { dismiss() }
            try {
                if (activity != null && !activity.isFinishing) show()
            } catch (e: Exception) {
                dismiss()
            }
        }
    }
}
