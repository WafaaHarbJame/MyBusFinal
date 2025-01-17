package com.mybus.mybusapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.Editable
import android.util.Log
import android.widget.ArrayAdapter
import com.github.dhaval2404.form_validation.rule.EmailRule
import com.mybus.mybusapp.MainActivity
import com.mybus.mybusapp.models.*
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.NumberHandler
import com.mybus.mybusapp.Utils.PhoneHandler
import com.mybus.mybusapp.Utils.SharedPManger
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.AESCrypt
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.classes.UtilityApp.fCMToken
import com.mybus.mybusapp.dialogs.CountryCodeDialog
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.countryCodeTxt
import kotlinx.android.synthetic.main.activity_login.emailInput
import kotlinx.android.synthetic.main.activity_login.emailTxt
import kotlinx.android.synthetic.main.activity_login.loginBtn
import kotlinx.android.synthetic.main.activity_login.mobileInput
import kotlinx.android.synthetic.main.activity_login.mobileTxt
import kotlinx.android.synthetic.main.activity_login.passwordInput
import kotlinx.android.synthetic.main.activity_login.passwordTxt
import kotlinx.android.synthetic.main.activity_login.registerBtn
import kotlinx.android.synthetic.main.activity_register.*


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
    var selectedCountryCode = 966
    var countryCodeDialog: CountryCodeDialog? = null
    var emailLog: Boolean = false

    companion object {
        const val REQUEST_LOGIN = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = ""


        homeBtn.setOnClickListener {
            onBackPressed()
        }

        mobileRB.setOnClickListener {
            emailTxt.visibility = gone
            emailInput.visibility = gone
            mobileLy.visibility = visible
            emailLog = false

        }

        emailRB.setOnClickListener {
            emailTxt.visibility = visible
            emailInput.visibility = visible
            mobileLy.visibility = gone
            emailLog = true

        }

        if (emailLog)
            emailRB.isChecked = true
        else
            mobileRB.isChecked = true



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

            if (isValidForm()) {
                if (emailLog)
                    checkEmail()
                else
                    loginUser()
            }


        }

        forgetPasswordBtn.setOnClickListener {

            val intent = Intent(getActiviy(), ForgetPasswordActivity::class.java)
            startActivity(intent)

        }

        registerBtn.setOnClickListener {

            val intent = Intent(getActiviy(), RegisterTypeActivity::class.java)
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
                                selectedCountryCode = countryModel.code
                                val codeStr = "+$selectedCountryCode"
                                countryCodeTxt.text = codeStr
                            }
                        })
                countryCodeDialog?.setOnDismissListener { countryCodeDialog = null }
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
//        val phoneUtils = PhoneNumberUtil.createInstance(getActiviy())

//        val supportedRegions = phoneUtils.supportedRegions
        //  selectedCountryCode = phoneUtils.getCountryCodeForRegion(isoCode.toUpperCase())
    }


    private fun loginUser() {
        val memberModel = RegisterUserModel()

        try {

            val mobileStr = NumberHandler.arabicToDecimal(mobileTxt.text.toString())
            val passwordStr = NumberHandler.arabicToDecimal(passwordTxt.text.toString())

//            if (!PhoneHandler.isValidPhoneNumber(mobileStr)) {
//                throw  Exception("phone")
//            }

            memberModel.countryCode = selectedCountryCode
            memberModel.mobile =
                if (mobileStr.startsWith("0")) mobileStr.replaceFirst(
                    "0",
                    ""
                ) else mobileStr

//            memberModel.isVerified = false
            memberModel.password = passwordStr
            memberModel.mobileWithCountry = selectedCountryCode.toString().plus(memberModel.mobile)

//
            GlobalData.progressDialog(
                getActiviy(),
                R.string.sign_in,
                R.string.please_wait_login
            )
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()
                    if (func == Constants.USER_NOT_EXIST) {
                        GlobalData.errorDialog(
                            getActiviy(),
                            R.string.login,
                            getString(R.string.not_have_account_q)
                        )
                    } else if (func == Constants.SUCCESS) {
                        val document: DocumentSnapshot = obj as DocumentSnapshot
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                        val user = document.toObject(MemberModel::class.java)
                        val password = AESCrypt.decrypt(user?.password)

                        val isVerified = document.get("isVerified") as Boolean

                        val fullName = document.get("fullName")
                        Log.d(TAG, "DocumentSnapshot data1: $fullName")
                        Log.d(
                            TAG,
                            "DocumentSnapshot isVerified: ${document.getBoolean("isVerified")}"
                        )

                        if (password == memberModel.password) {
                            if (isVerified) {
                                UtilityApp.userData = user
                                val intent = Intent(getActiviy(), MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            } else {
                                val intent = Intent(getActiviy(), ConfirmActivity::class.java)
                                intent.putExtra(Constants.KEY_MEMBER, user)
                                intent.putExtra(
                                    Constants.KEY_MOBILE,
                                    memberModel.mobileWithCountry
                                )
                                startActivity(intent)

                            }

                        } else {
                            GlobalData.errorDialog(
                                getActiviy(),
                                R.string.login,
                                getString(R.string.mobile_password_not_match)
                            )
                        }

                    } else {
                        GlobalData.errorDialog(
                            getActiviy(),
                            R.string.login,
                            getString(R.string.fail_to_sign_in)
                        )

                    }

                }
            }).loginHandle(memberModel)

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

    private fun checkEmail() {

        val emailStr = NumberHandler.arabicToDecimal(emailTxt.text.toString())
        val passwordStr = NumberHandler.arabicToDecimal(passwordTxt.text.toString())
        GlobalData.progressDialog(
            getActiviy(),
            R.string.sign_in,
            R.string.please_wait_login
        )
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                GlobalData.progressDialogHide()
                if (func == Constants.SUCCESS) {

                    val user = obj as RegisterUserModel?

                    selectedCountryCode = user?.countryCode!!

                    mobileTxt.setText(user.mobile)
                    countryCodeTxt.text = user.countryCode.toString()

                    loginUser()

                } else {
                    GlobalData.errorDialog(
                        getActiviy(),
                        R.string.login,
                        getString(R.string.mobile_password_not_match)
                    )
                }

            }
        }).getAccountByEmail(emailStr)


//        }

    }

    private fun isValidForm(): Boolean {

        if (emailLog) {
            return FormValidator.getInstance()

                .addField(
                    emailInput,
                    NonEmptyRule(R.string.ENTER_EMAIL),
                    EmailRule(R.string.enter_vaild_email)

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
        } else {
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


//    private fun getData() {
//        var allDrivesList: MutableList<RegisterUserModel>? = null
//        DataFeacher(object : DataFetcherCallBack {
//            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
//
//                if (func == Constants.SUCCESS) {
//                    allDrivesList = obj as MutableList<RegisterUserModel>?
//                    Log.i("TAG", "Log getAllAccount" + allDrivesList!![0].mobileWithCountry)
//                    mobileTxt.text =
//                        Editable.Factory.getInstance().newEditable(allDrivesList!![0].mobile)
//                    countryCodeTxt.text = Editable.Factory.getInstance()
//                        .newEditable(allDrivesList!![0].countryCode.toString())
//                    selectedCountryCode = allDrivesList!![0].countryCode
//
//                }
//
//            }
//        }).getAllAccount()
//    }


}
