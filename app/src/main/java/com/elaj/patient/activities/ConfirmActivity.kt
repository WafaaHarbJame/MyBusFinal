package com.elaj.patient.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import com.elaj.patient.MainActivityBottomNav
import com.elaj.patient.R
import com.elaj.patient.Utils.NumberHandler
import com.elaj.patient.apiHandlers.ApiUrl
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.GlobalData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_confirm.*
import java.util.concurrent.TimeUnit


class ConfirmActivity : ActivityBase() {
    private lateinit var auth: FirebaseAuth
    val TAG: String? = "ConfirmActivity"
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String=""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    var mobile: String = ""
    lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        auth= FirebaseAuth.getInstance()
        title = ""
        db = FirebaseFirestore.getInstance()

        val bundle = intent.extras
        if (bundle != null) {
            mobile = bundle.getString(Constants.KEY_MOBILE)!!
            Log.i(TAG, "Log mobile " + mobile)
            sendVerificationCode(mobile)

        }

        confirmBtn.setOnClickListener {
            if (TextUtils.isEmpty(codeTxt.text.toString())) {
                codeTxt.error = getString(R.string.please_enter_code_sent_mobile);
                codeTxt.requestFocus();
            }
            else {
                val code = NumberHandler.arabicToDecimal(codeTxt.text.toString());
                GlobalData.progressDialog(
                    getActiviy(),
                    R.string.confirm_code,
                    R.string.please_wait_sending,
                    true
                )
                val credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
                signInWithPhoneAuthCredential(credential)
            }
        }

        resendCodeBtn.setOnClickListener{
            sendVerificationCode(mobile)
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
                    val user = task.result?.user
                    db.collection(ApiUrl.Users.name).document(mobile).update("isVerified",true).addOnSuccessListener {
                        val intent = Intent(getActiviy(), MainActivityBottomNav::class.java)
                        startActivity(intent)
                    }.addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }


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


    private fun sendVerificationCode(phoneNumber:String){
        Log.d(TAG, "phoneNumber:$phoneNumber")

        object : CountDownTimer(60000, 1000) {
            override fun onTick(l: Long) {
                resendCodeBtn.text = "" .plus( l / 1000)
                resendCodeBtn.isEnabled = false
            }

            override fun onFinish() {
                resendCodeBtn.text = getString(R.string.resend)
                resendCodeBtn.isEnabled = true
            }
        }.start()


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //signInWithPhoneAuthCredential(credential)
            }
            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
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
            callbacks)
    }

}