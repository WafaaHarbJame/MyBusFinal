package com.mybus.mybusapp.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.R

class MyInfoDialog(
    var activity: Activity?,
    message: String?,
    isHtml: Boolean,
    var dataFetcherCallBack: DataFetcherCallBack?
) :
    Dialog(activity!!) {
    var messageTxt: TextView
    private val okBtn: LinearLayout
    private val dialogMy: MyInfoDialog
        get() = this

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE) //before
        //        setTitle(title);
        setContentView(R.layout.dialog_my_info)
        messageTxt = findViewById(R.id.messageTxt)
        okBtn = findViewById(R.id.okBtn)
        if (isHtml) {
            messageTxt.text = Html.fromHtml(message)
        } else {
            if (message != null && message != "") {
                messageTxt.text = message
            }
        }
        okBtn.setOnClickListener {
            dismiss()
            dataFetcherCallBack?.Result(
                dialogMy,
                "InfoDialog",
                true
            )
        }
        try {
            if (activity != null && !activity!!.isFinishing) show()
        } catch (e: Exception) {
            dismiss()
        }
    }
}