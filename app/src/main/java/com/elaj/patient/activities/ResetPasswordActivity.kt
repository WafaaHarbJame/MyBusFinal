package com.elaj.patient.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.elaj.patient.R
import com.elaj.patient.Utils.NumberHandler
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.AESCrypt
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.GlobalData
import com.elaj.patient.classes.UtilityApp
import com.elaj.patient.models.MemberModel
import com.elaj.patient.models.ResponseEvent
import com.elaj.patient.models.ResultAPIModel
import com.github.dhaval2404.form_validation.rule.EqualRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_confirm.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit


class ResetPasswordActivity : ActivityBase() {

    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    val TAG: String? = "ResetPasswordActivity"
    var phoneNumber: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        title = ""
        auth = FirebaseAuth.getInstance()

        val bundle = intent.extras
        if (bundle != null) {
            phoneNumber = bundle.getString(Constants.KEY_MOBILE)!!
            Log.i(TAG, "Log mobile $phoneNumber")
            sendVerificationCode(phoneNumber)

        }

        resetPasswordBtn.setOnClickListener {

            if (isValidForm())
                resetPassword()
        }

    }

    private fun resetPassword() {

        try {
            val code = NumberHandler.arabicToDecimal(codeTV.text.toString());

            GlobalData.progressDialog(
                getActiviy(),
                R.string.reset_password,
                R.string.please_wait_sending
            )
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
            signInWithPhoneAuthCredential(credential)


        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(
                codeTV,
                NonEmptyRule(R.string.please_enter_code_sent_mobile),
            )
            .addField(
                passwordTxt,
                NonEmptyRule(R.string.enter_password)
            )
            .addField(
                confirmPasswordTxt,
                NonEmptyRule(R.string.enter_confirm_password)
            )
            .addField(
                confirmPasswordTxt,
                NonEmptyRule(R.string.enter_password),
                EqualRule(
                    passwordTxt.text.toString(),
                    R.string.password_confirm_not_match
                )
            )

            .validate()
    }

    private fun sendVerificationCode(phoneNumber: String) {
        Log.d(TAG, "phoneNumber:$phoneNumber")

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String, token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "Log onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            callbacks
        )
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val passwordStr = AESCrypt.encrypt(NumberHandler.arabicToDecimal(passwordTxt.text.toString()))
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    GlobalData.progressDialogHide()
                    DataFeacher(object : DataFetcherCallBack {
                        override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                            GlobalData.progressDialogHide()

                            if (func == Constants.SUCCESS) {
                                Log.d(TAG, "phoneNumber:${phoneNumber}")
                                val intent = Intent(getActiviy(), LoginActivity::class.java)
                                startActivity(intent)
                            } else {
                                GlobalData.errorDialog(
                                    getActiviy(),
                                    R.string.reset_password,
                                    getString(R.string.fail_to_reset_password)
                                )
                            }


                        }
                    }).resetPassword(phoneNumber,passwordStr)

                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        GlobalData.progressDialogHide()
                    }

                }
            }
    }



}
