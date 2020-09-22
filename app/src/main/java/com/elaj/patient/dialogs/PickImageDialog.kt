package com.elaj.patient.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.LinearLayout
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.Constants
import com.elaj.patient.R

class PickImageDialog(var activity: Activity?, var dataFetcherCallBack: DataFetcherCallBack?) :
    Dialog(activity!!) {
    private val captureImgBtn: LinearLayout
    private val pickImgBtn: LinearLayout
    private val dialog: PickImageDialog
        get() = this

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE) //before
        //        setTitle(title);
        setContentView(R.layout.dialog_pick_image)
        captureImgBtn = findViewById(R.id.captureImgBtn)
        pickImgBtn = findViewById(R.id.pickImgBtn)
        captureImgBtn.setOnClickListener {
            dataFetcherCallBack?.Result(
                dialog,
                Constants.CAPTURE,
                true
            )
            dismiss()
        }
        pickImgBtn.setOnClickListener {
            dataFetcherCallBack?.Result(
                dialog,
                Constants.PICK,
                true
            )
            dismiss()
        }
        try {
            if (activity != null && !activity!!.isFinishing) show()
        } catch (e: Exception) {
            dismiss()
        }
    }
}
