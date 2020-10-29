package com.mybus.mybusapp.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.mybus.mybusapp.R

object ErrorMessagesDialog {
    //        Activity activity;
    var errorDialog: ErrorDialog? = null

    fun with(activity: Activity?): ErrorDialog? {
        return if (errorDialog == null) {
            errorDialog = ErrorDialog(activity)
            errorDialog
        } else {
            errorDialog
        }
    }

    class ErrorDialog(activity: Activity?) : Dialog(activity!!) {
        private val messageTxt: TextView
        private val okBtn: LinearLayout
        private val dialog: ErrorDialog
            get() = this

        var activity: Activity? = null

        fun setMessages(vararg messages: String?): ErrorDialog {
            var msg = ""
            for (message in messages) {
                msg += message + "\n"
            }
            errorDialog!!.messageTxt.text = msg
            //            if (customeDialog == null) {
//                return customeDialog;
//            } else {
            return errorDialog!!
            //            }
        }

        fun build() {
            if (errorDialog != null && !activity!!.isFinishing) {
                errorDialog!!.show()
                errorDialog!!.setOnDismissListener { errorDialog = null }
            }
        }

//        companion object {
//
//
//        }

        init {
//            Companion.activity = activity
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE)

            setContentView(R.layout.dialog_my_info)
            messageTxt = findViewById(R.id.messageTxt)
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