package com.elaj.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.ArrayAdapter
import com.elaj.patient.R
import com.elaj.patient.Utils.NumberHandler
import com.elaj.patient.Utils.PhoneHandler
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.*
import com.elaj.patient.dialogs.CountryCodeDialog
import com.elaj.patient.models.*
import com.github.dhaval2404.form_validation.rule.EqualRule
import com.github.dhaval2404.form_validation.rule.LengthRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class RegisterActivity : ActivityBase() {

    private var FCMToken: String? = ""
    val TAG: String? = "Log"

    //    lateinit var db: FirebaseFirestore
    private var countryModels: MutableList<CountryModel>? = mutableListOf()
    private var countryVal = 0

    private var cityAdapter: ArrayAdapter<String>? = null
    private var cityModels: MutableList<CityModel>? = mutableListOf()
    private var cityData: MutableList<String> = ArrayList()
    private var cityVal = 0

    var selectedCountryCode = 0
    var countryCodeDialog: CountryCodeDialog? = null
//    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = ""


//        countrySpinner.onItemClickListener =
//            AdapterView.OnItemClickListener { parent, view, position, id ->
//
//                countryVal = countryModels!![position].id
//
////                println("Log countryVal $countryVal")
//            }

//        countrySpinner.setOnClickListener {
//
//            if (countryCodeDialog == null) {
//                countryCodeDialog =
//                    CountryCodeDialog(getActiviy(), selectedCountryCode,
//                        object : DataFetcherCallBack {
//                            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
//                                val countryModel = obj as CountryModel
//                                countryVal = countryModel.id
//                                countrySpinner.setText(countryModel.name)
//
//                                getCountryCities()
//                            }
//                        })
//                countryCodeDialog?.setOnDismissListener { countryCodeDialog = null }
//            }
//
//        }

        registerBtn.setOnClickListener {

            val intent = Intent(getActiviy(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        loginBtn.setOnClickListener {

            if (isValidForm())
                registerUser()
        }

        getCountries()
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
                countryCodeDialog?.setOnDismissListener { countryCodeDialog = null }
            }

        }

    }

    private fun getCountries() {
        countryModels = DBFunction.getCountries()
        if (countryModels == null || countryModels?.isEmpty()!!) {
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                    if (IsSuccess)
                        getCountries()
                }
            }).getSettings()

        } else {
//            countryData.clear()
////            countryData.add(getString(R.string.select_country))
//            for (i in 0 until countryModels?.size!!) {
//                countryData.add(countryModels?.get(i)?.name!!)
//            }
//            countryAdapter = ArrayAdapter(
//                getActiviy(), R.layout.row_spinner_item, R.id.spinnerRowTxt, countryData
//            )
////            countrySpinner.setDropDownViewResource(R.layout.row_popup_spinner_item)
//            countrySpinner.setAdapter(countryAdapter)
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

    private fun registerUser() {

        try {

            var mobileStr = NumberHandler.arabicToDecimal(mobileTxt.text.toString())
            val passwordStr = NumberHandler.arabicToDecimal(passwordTxt.text.toString())

//            if (!PhoneHandler.isValidPhoneNumber(mobileStr)) {
//                throw  Exception("phone")
//            }

            if (countryVal == -1)
                throw Exception("country")

            val registerUserModel = RegisterUserModel()
            registerUserModel.countryCode = selectedCountryCode
            registerUserModel.mobile =
                if (mobileStr.startsWith("0")) mobileStr.replaceFirst(
                    "0",
                    ""
                ) else mobileStr

//            phoneNumber = countryCodeTxt.text.toString()
//                .plus(NumberHandler.arabicToDecimal(mobileStr))
            registerUserModel.fcm_token = FCMToken
            registerUserModel.isVerified = false
            registerUserModel.password = AESCrypt.encrypt(passwordStr);
            registerUserModel.password_confirm = AESCrypt.encrypt(passwordStr)
            registerUserModel.mobileWithPlus =
                countryCodeTxt.text.toString().plus(registerUserModel.mobile)

            GlobalData.progressDialog(
                getActiviy(),
                R.string.register,
                R.string.please_wait_register
            )

            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()

                    if (func == Constants.SUCCESS) {
                        Log.d(TAG, "phoneNumber:${registerUserModel.mobileWithPlus}")

                        val user = MemberModel().apply {
                            countryCode = registerUserModel.countryCode
                            mobile = registerUserModel.mobile
                            mobileWithPlus = registerUserModel.mobileWithPlus
                            fcm_token = registerUserModel.fcm_token
                            password = registerUserModel.password
                            isVerified = true
                        }
                        val intent = Intent(getActiviy(), ConfirmActivity::class.java)
                        intent.putExtra(Constants.KEY_MEMBER, user)
                        intent.putExtra(Constants.KEY_MOBILE, registerUserModel.mobileWithPlus)
                        startActivity(intent)
                    } else {
                        GlobalData.errorDialog(
                            getActiviy(),
                            R.string.register,
                            getString(R.string.fail_to_register)
                        )
                    }


                }
            }).registerHandle(getActiviy(), registerUserModel)

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(responseEvent: ResponseEvent) {

        if (responseEvent.api == "registerHandle") {
//            GlobalData.progressDialog(
//                getActiviy(),
//                R.string.register,
//                R.string.please_wait_register,
//                false
//            )
            if (responseEvent.type == Constants.ERROR_DATA) {
                val result = responseEvent.data as ResultAPIModel<Any>?
                var message = getString(R.string.fail_to_register)
                if (result?.error != null) {
                    val errors = result.error.details
                    for (error in errors) {
                        message += ("\n---\n $error")
                    }
                }

                GlobalData.errorDialog(getActiviy(), R.string.register, message)

            } else if (responseEvent.type == Constants.FAIL_DATA) {
                Toast(R.string.fail_to_register)
            } else if (responseEvent.type == Constants.NO_CONNECTION) {
                Toast(R.string.no_internet_connection)
            } else {
                val result: ResultAPIModel<MemberModel> =
                    responseEvent.data as ResultAPIModel<MemberModel>
                if (responseEvent.type == Constants.SUCCESS) {
                    val user: MemberModel = result.data
                    UtilityApp.userData = user

                    val intent = Intent(getActiviy(), Constants.MAIN_ACTIVITY_CLASS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                }

            }
        }

    }

    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(
                passwordTxt,
                NonEmptyRule(R.string.enter_password)
//                PasswordRule(R.string.please_provide_strong_password)
            )
            .addField(
                confirmPasswordTxt,
                NonEmptyRule(R.string.enter_password),
                EqualRule(
                    passwordTxt.text.toString(),
                    R.string.password_confirm_not_match
                )
            )
            .addField(
                mobileTxt,
                NonEmptyRule(R.string.enter_phone_number),
                LengthRule(10, R.string.valid_phone_number)
            )

            .validate()
    }

    private fun getFCMToken() {
        FCMToken = UtilityApp.fCMToken
        if (FCMToken == null) {
            FirebaseInstanceId.getInstance().instanceId
                .addOnSuccessListener { instanceIdResult: InstanceIdResult ->
                    FCMToken = instanceIdResult.token
                    UtilityApp.fCMToken = FCMToken
                }
        }
    }

}
