package com.elaj.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.ArrayAdapter
import com.elaj.patient.MainActivityBottomNav
import com.elaj.patient.models.*
import com.elaj.patient.R
import com.elaj.patient.Utils.NumberHandler
import com.elaj.patient.Utils.PhoneHandler
import com.elaj.patient.Utils.SharedPManger
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.AESCrypt
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.GlobalData
import com.elaj.patient.classes.UtilityApp
import com.elaj.patient.classes.UtilityApp.fCMToken
import com.elaj.patient.dialogs.CountryCodeDialog
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.countryCodeTxt
import kotlinx.android.synthetic.main.activity_login.loginBtn
import kotlinx.android.synthetic.main.activity_login.mobileInput
import kotlinx.android.synthetic.main.activity_login.mobileTxt
import kotlinx.android.synthetic.main.activity_login.passwordInput
import kotlinx.android.synthetic.main.activity_login.passwordTxt
import kotlinx.android.synthetic.main.activity_login.registerBtn


class LoginActivity : ActivityBase() {

    private var historyModel: MutableList<LoginMemberModel>? = null
    private var history: MutableList<String> = ArrayList()
    private var FCMToken: String? = ""
    private var loginMemberModel: LoginMemberModel? = null
    private lateinit var phoneNumber: String
    var sharedPManger: SharedPManger? = null
    val TAG: String? = "ConfirmActivity"
    var historyPosition = -1

    var mobile = ""
    var selectedCountryCode = 0
    var countryCodeDialog: CountryCodeDialog? = null


    companion object {
        const val REQUEST_LOGIN = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = ""

        sharedPManger = SharedPManger(getActiviy())
        val savedData = sharedPManger?.getDataString(Constants.KEY_LOGIN_PREFERANCE)
        historyModel =
            Gson().fromJson(
                savedData,
                object : TypeToken<MutableList<LoginMemberModel>>() {}.type
            )
        if (historyModel != null) {
            setAutoCompleteSource();
        }

        loginBtn.setOnClickListener {

            if (isValidForm())
                loginUser()


        }

        forgetPasswordBtn.setOnClickListener {

            val intent = Intent(getActiviy(), ForgetPasswordActivity::class.java)
            startActivity(intent)

        }

        registerBtn.setOnClickListener {

            val intent = Intent(getActiviy(), RegisterActivity::class.java)
            startActivity(intent)

        }

        getFCMToken()
        initLocalCountryCode()

        val countryCodeStr = "+$selectedCountryCode"
        countryCodeTxt.text = countryCodeStr

        countryCodeTxt.setOnClickListener {

            if (countryCodeDialog == null) {
                countryCodeDialog =
                    CountryCodeDialog(getActiviy(), selectedCountryCode,
                        object : DataFetcherCallBack {
                            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                                val countryModel = obj as CountryModel
                                selectedCountryCode = countryModel.countryCode
                                val codeStr = "+$selectedCountryCode"
                                countryCodeTxt.text = codeStr
                            }
                        })
                countryCodeDialog?.setOnDismissListener { dialog -> countryCodeDialog = null }
            }

        }

    }

    private fun initLocalCountryCode() {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networkCountryIso = tm.networkCountryIso
        val simCountryIso = tm.simCountryIso
        var isoCode = "SR"
        if (networkCountryIso != null && !networkCountryIso.isEmpty()) {
            isoCode = networkCountryIso
        } else if (simCountryIso != null && !simCountryIso.isEmpty()) {
            isoCode = simCountryIso
        }
        val phoneUtils = PhoneNumberUtil.createInstance(getActiviy())

//        val supportedRegions = phoneUtils.supportedRegions
        selectedCountryCode = phoneUtils.getCountryCodeForRegion(isoCode.toUpperCase())
    }


    private fun loginUser() {

        try {

            val mobileStr = NumberHandler.arabicToDecimal(mobileTxt.text.toString())
            val passwordStr = NumberHandler.arabicToDecimal(passwordTxt.text.toString())

            if (!PhoneHandler.isValidPhoneNumber(mobileStr)) {
                throw  Exception("phone")
            }

            val memberModel = RegisterUserModel()
            memberModel.countryCode = selectedCountryCode
            memberModel.mobile =
                if (mobileStr.startsWith("0")) mobileStr.replaceFirst(
                    "0",
                    ""
                ) else mobileStr

            phoneNumber = countryCodeTxt.text.toString()
                .plus(NumberHandler.arabicToDecimal(mobileStr))
            memberModel.password = passwordStr
            memberModel.fcm_token = FCMToken
            memberModel.isVerified = false
            memberModel.password = AESCrypt.encrypt(passwordStr)
//            memberModel.password_confirm = AESCrypt.encrypt(passwordStr)
            memberModel.mobileWithPlus = phoneNumber
//
            GlobalData.progressDialog(
                getActiviy(),
                R.string.sign_in,
                R.string.please_wait_login
            )
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()

                    val document: DocumentSnapshot? = obj as DocumentSnapshot
                    if (document != null) {
//                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        val user = document.toObject(MemberModel::class.java)

                        val password = user?.password
                        val isVerified = user?.isVerified
                        if (password == memberModel.password) {
                            if (isVerified == true) {

                                UtilityApp.userData = user

                                val intent = Intent(getActiviy(), MainActivityBottomNav::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            } else {
                                val intent = Intent(getActiviy(), ConfirmActivity::class.java)
                                intent.putExtra(Constants.KEY_MEMBER, user)
                                intent.putExtra(Constants.KEY_MOBILE, phoneNumber)
                                startActivity(intent)

                            }

                        } else {
//                            Toast(R.string.fail_to_login)

                            GlobalData.errorDialog(
                                getActiviy(),
                                R.string.login,
                                getString(R.string.fail_to_login)
                            )
                        }

                    } else {
//                        Toast(R.string.not_have_account_q)

                        GlobalData.errorDialog(
                            getActiviy(),
                            R.string.login,
                            getString(R.string.not_have_account_q)
                        )

                    }

                }
            }).loginHandle(getActiviy(), memberModel)
//            DataFeacher().loginHandle(loginUserModel)

        } catch (e: Exception) {

            e.printStackTrace()

//            if (e.message == "phone")
//                Toast(R.string.invalid_phone)
        }
    }

    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(
                mobileInput,
                NonEmptyRule(R.string.enter_phone_number)
//                LengthRule(10, R.string.valid_phone_number)
            )
            .addField(
                passwordInput,
                NonEmptyRule(R.string.enter_password)
//                PasswordRule(R.string.please_provide_strong_password)
            )
            .setErrorListener {
                // Require only for CheckBox with Toast or Custom View Only

            }
            .validate()
    }

    private fun setAutoCompleteSource() {
        for (loginMemberModel in historyModel!!) {
            history.add(loginMemberModel.email)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            history
//            history.addAll(history)
        )
//        mobileTxt.setAdapter(adapter)
    }

    private fun addSearchInput(input: LoginMemberModel) {
        if (historyModel == null) {
            println("Log history null")
            historyModel = ArrayList()
        }
        if (!history.contains(input.email)) {
            historyModel?.add(input)
            //            setAutoCompleteSource();
        } else {
            if (historyPosition == -1) {
//                val str = mobileTxt.text.toString()
//                historyPosition = history.indexOf(str)
            }
            historyModel?.set(historyPosition, input)
        }
    }

    private fun savePrefs() {
        val dataList = Gson().toJson(historyModel)
        sharedPManger?.SetData(Constants.KEY_LOGIN_PREFERANCE, dataList)
    }

    private fun getFCMToken() {
        FCMToken = fCMToken
        if (FCMToken == null) {
            FirebaseInstanceId.getInstance().instanceId
                .addOnSuccessListener { instanceIdResult: InstanceIdResult ->
                    FCMToken = instanceIdResult.token
                    fCMToken = FCMToken
                }
        }
    }

}
