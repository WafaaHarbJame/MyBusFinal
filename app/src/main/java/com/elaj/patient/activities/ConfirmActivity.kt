package com.elaj.patient.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.elaj.patient.MainActivityBottomNav
import com.elaj.patient.R
import com.elaj.patient.Utils.NumberHandler
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.GlobalData
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : ActivityBase() {
    private lateinit var auth: FirebaseAuth
    val TAG: String? = "Log"
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    var countryCode: Int = Constants.COUNTRY_CODE
    var mobile: String = ""
    var codeSent: String = ""
    var code: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        auth= FirebaseAuth.getInstance()
        title = ""

        val bundle = intent.extras
        if (bundle != null) {
            countryCode = bundle.getInt(Constants.KEY_COUNTRY_CODE)
            mobile = bundle.getString(Constants.KEY_MOBILE)!!
            codeSent== bundle.getString(Constants.KEY_CODE_SENT)!!
            Log.i(TAG, "Log countryCode " + countryCode)
            Log.i(TAG, "Log mobile " + mobile)
            Log.i(TAG, "Log codeSent " + codeSent)

        }

        confirmBtn.setOnClickListener {
            val code = NumberHandler.arabicToDecimal(codeTxt.text.toString());
            GlobalData.progressDialog(
                getActiviy(),
                R.string.confirm_code,
                R.string.please_wait_sending,
                true
            )
            val credential = PhoneAuthProvider.getCredential(codeSent, code)
            signInWithPhoneAuthCredential(credential)

        }



    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    GlobalData.progressDialog(
                        getActiviy(),
                        R.string.confirm_code,
                        R.string.please_wait_sending,
                        false
                    )
                    val intent = Intent(getActiviy(), MainActivityBottomNav::class.java)
                    startActivity(intent)


                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        GlobalData.progressDialog(
                            getActiviy(),
                            R.string.confirm_code,
                            R.string.please_wait_sending,
                            false
                        )
                    }

                }
            }
    }

    private  fun verificationCode(){
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                verificationInProgress = false
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Log.w(TAG, "onVerificationFailed", e)
                verificationInProgress = false

                if (e is FirebaseAuthInvalidCredentialsException) {

                } else if (e is FirebaseTooManyRequestsException) {

                }

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d(TAG, "onCodeSent:$verificationId")

                storedVerificationId = verificationId
                resendToken = token

            }
        }

    }



}