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

//    var user: MemberModel? = null

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

//        user = UtilityApp.userData

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
                titleInput,
                NonEmptyRule(R.string.enter_message_title)
            )
            .addField(
                emailInput,
                NonEmptyRule(R.string.enter_valid_email)
            )
            .addField(
                messageInput,
                NonEmptyRule(R.string.enter_message_text),
            )
            .validate()
    }

    private fun contactSupport() {

        try {

            val emailStr = emailET.text.toString()
            val messageTitleStr = titleET.text.toString()
            val messageDescStr = messageET.text.toString()


            val supportModel = SupportModel()
            supportModel.email = emailStr
            supportModel.messageTitle = messageTitleStr
            supportModel.messageText = messageDescStr

            GlobalData.progressDialog(
                activity,
                R.string.contact_us,
                R.string.please_wait_sending
            )

            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()

                    if (func == Constants.SUCCESS) {

                        emailET.setText("")
                        messageET.setText("")
                        titleET.setText("")

                        GlobalData.successDialog(
                            requireActivity(),
                            R.string.contact_us,
                            getString(R.string.success_send_message_will_contact_soon)
                        )
                    } else {

                        GlobalData.errorDialog(
                            requireActivity(),
                            R.string.contact_us,
                            getString(R.string.fail_to_contact_withsupport)
                        )
                    }


                }
            }).sendSupport(supportModel)

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

}