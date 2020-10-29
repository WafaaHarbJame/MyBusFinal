package com.mybus.mybusapp.classes

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mybus.mybusapp.R
import com.mybus.mybusapp.RootApplication

object GlobalData {

    const val BetaBaseURL = "https://ajmal.monezsoft.ca/"
    const val ReleaseBaseURL = "https://www.tjmeat.com/"
    const val BaseURL = BetaBaseURL
    const val ApiURL = BaseURL + "api/"
    const val ImageURL = BaseURL

    var Position = 0
    var REFRESH_FAVORITE = false
    var SHOW_CHECK_VERSION = false
    var FAV_POS = -1
    var FAV_STATUS = false
    var IS_CUSTOMER = true

    //    var progressDialog: ProgressDialog? = null
    private var progressDialog: AwesomeProgressDialog? = null
    private var errorDialog: AwesomeErrorDialog? = null
    private var infoDialog: AwesomeInfoDialog? = null
    private var successDialog: AwesomeSuccessDialog? = null

    //============================================================================
//============================================================================
    fun GlideImg(
        image: Any?,
        placeholder: Int,
        imageView: ImageView
    ) { //        Log.i("Global", "Log url " + image);
        Glide.with(RootApplication.instance!!.applicationContext)
            .asBitmap()
            .load(image)
            .apply(
                RequestOptions()
                    .placeholder(placeholder)
            )
            .into(imageView)
    }

    fun progressDialog(
        c: Context?,
        title: Int?,
        msg: Int?
    ) { // to show dialog insert status = true to dismiss doialog status = false

        progressDialog = AwesomeProgressDialog(c)
            .setTitle(title!!)
            .setMessage(msg!!)
            .setColoredCircle(R.color.colorPrimaryDark)
            .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
            .setCancelable(false)

        progressDialog?.show()

    }

    fun progressDialogHide() {
        progressDialog?.hide()
    }


    fun errorDialog(
        c: Context?,
        title: Int?,
        msg: String?
    ) { // to show dialog insert status = true to dismiss doialog status = false

        errorDialog = AwesomeErrorDialog(c)
            .setTitle(title!!)
            .setMessage(msg!!)
            .setColoredCircle(R.color.dialogErrorBackgroundColor)
            .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
            .setCancelable(true)
//                    .setButtonText(getString(R.string.ok))
            .setButtonBackgroundColor(R.color.dialogErrorBackgroundColor)

        errorDialog?.show()

    }

    fun infoDialog(
        c: Context?,
        title: Int?,
        msg: String?
    ) { // to show dialog insert status = true to dismiss doialog status = false


        infoDialog = AwesomeInfoDialog(c)
            .setTitle(title!!)
            .setMessage(msg!!)
            .setColoredCircle(R.color.dialogInfoBackgroundColor)
            .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
            .setCancelable(true)
//                    .setButtonText(getString(R.string.ok))
//            .setButtonBackgroundColor(R.color.dialogErrorBackgroundColor)

        infoDialog?.show()
    }

    fun successDialog(
        c: Context?,
        title: Int?,
        msg: String?
    ) { // to show dialog insert status = true to dismiss doialog status = false


        successDialog = AwesomeSuccessDialog(c)
            .setTitle(title!!)
            .setMessage(msg!!)
            .setColoredCircle(R.color.dialogSuccessBackgroundColor)
            .setDialogIconAndColor(R.drawable.ic_check, R.color.white)
            .setCancelable(true)
//                    .setButtonText(getString(R.string.ok))
//            .setButtonBackgroundColor(R.color.dialogErrorBackgroundColor)

        successDialog?.show()
    }

    fun Toast(context: Context?, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun Toast(context: Context, resId: Int) {
        Toast.makeText(
            context,
            context.getString(resId),
            Toast.LENGTH_SHORT
        ).show()
    }


}


