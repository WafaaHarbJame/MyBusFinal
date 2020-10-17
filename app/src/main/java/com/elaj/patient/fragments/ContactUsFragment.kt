package com.elaj.patient.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.elaj.patient.MainActivityBottomNav
import com.elaj.patient.R
import com.elaj.patient.Utils.NumberHandler
import com.elaj.patient.activities.ConfirmActivity
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.AESCrypt
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.GlobalData
import com.elaj.patient.classes.UtilityApp
import com.elaj.patient.models.MemberModel
import com.elaj.patient.models.RegisterUserModel
import com.elaj.patient.models.SupportModel
import com.github.dhaval2404.form_validation.rule.EqualRule
import com.github.dhaval2404.form_validation.rule.LengthRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_contact_us.*
import kotlinx.android.synthetic.main.tool_bar.*

class ContactUsFragment : FragmentBase() {
    var activity: Activity? = null
    val TAG: String? = "Log"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_contact_us, container, false)
        activity = getActivity()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()

        mainTitleTxt.text = getString(R.string.contact_us)

        homeBtn.visibility = gone
        sendBtn.setOnClickListener {

            if (isValidForm())
                contactSupport()

        }

    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }

    companion object {
        const val DElAY_TIME = 300
    }

    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(
                titleET,
                NonEmptyRule(R.string.enter_message_title)
            )
            .addField(
                messageET,
                NonEmptyRule(R.string.enter_message_text),
            )
            .validate()
    }

    private fun contactSupport() {

        try {

            var messageTitleStr = titleET.text.toString()
            val messageTextStr = messageET.text.toString()
            val mobile = UtilityApp.userData?.mobileWithPlus


            val supportModel = SupportModel()
            supportModel.messageText = messageTextStr
            supportModel.messageTitle = messageTitleStr
            supportModel.mobile = mobile

            GlobalData.progressDialog(
                activity,
                R.string.contact_us,
                R.string.please_wait_sending
            )

            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()

                    if (func == Constants.SUCCESS) {
                        Toast(R.string.success_send)
                        val intent = Intent(activity, MainActivityBottomNav::class.java)
                        startActivity(intent)
                        val mainScreenFragment = MainScreenFragment()
                        (activity as AppCompatActivity).supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, mainScreenFragment, "mainScreenFragment")
                            .commit();

                    } else {
                        var message = getString(R.string.fail_to_contact_withsupport)
                        GlobalData.errorDialog(
                            activity,
                            R.string.contact_us,
                            message
                        )
                    }


                }
            }).sendSupport(supportModel)

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

}