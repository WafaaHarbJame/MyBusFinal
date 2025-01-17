package com.mybus.mybusapp.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.NumberHandler
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.AESCrypt
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.classes.UtilityApp
import com.github.dhaval2404.form_validation.rule.EqualRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import kotlinx.android.synthetic.main.dialog_change_password.*
import kotlinx.android.synthetic.main.dialog_change_password.confirmPasswordTxt

class ChangePasswordDialog(
    var activity: Activity?
) :
    Dialog(activity!!) {

    val dialog: ChangePasswordDialog
        get() = this
    val TAG: String? = "ChangePasswordDialog"


    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.dialog_change_password)

        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;


        saveBtn.setOnClickListener {

            if (isValidForm())
                changePassword()
        }


        try {
            if (activity != null && !activity!!.isFinishing) show()
        } catch (e: Exception) {
            dismiss()
        }


    }

    private fun changePassword() {
        try {

            var mobileStr = UtilityApp.userData?.mobileWithCountry;
            val currentPasswordStr = NumberHandler.arabicToDecimal(currPasswordTV.text.toString())
            val newPasswordStr = NumberHandler.arabicToDecimal(newPasswordTV.text.toString());

            GlobalData.progressDialog(
                activity,
                R.string.change_password,
                R.string.please_wait_to_change_password
            )

            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()
                    if (func == Constants.SUCCESS) {
                        dialog.dismiss()
                        GlobalData.successDialog(
                            activity,
                            R.string.change_password,
                            activity?.getString(R.string.success_change_password), null
                        )


                    } else {
                        var message = activity?.getString(R.string.fail_to_change_password)
                        if (func == Constants.PASSWORD_WRONG)
                            message = activity?.getString(R.string.current_password_wrong)

                        GlobalData.errorDialog(
                            activity,
                            R.string.change_password,
                            message
                        )
                    }


                }
            }).changePassword(
                mobileStr,
                AESCrypt.encrypt(currentPasswordStr),
                AESCrypt.encrypt(newPasswordStr)
            );

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(
                currPasswordTV,
                NonEmptyRule(R.string.enter_password)
            )
            .addField(
                newPasswordTV,
                NonEmptyRule(R.string.enter_confirm_password)
            )
            .addField(
                confirmPasswordTxt,
                NonEmptyRule(R.string.enter_password),
                EqualRule(
                    newPasswordTV.text.toString(),
                    R.string.password_confirm_not_match
                )
            )

            .validate()
    }


}