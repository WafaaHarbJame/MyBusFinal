package com.mybus.mybusapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import com.github.dhaval2404.form_validation.rule.EqualRule
import com.github.dhaval2404.form_validation.rule.LengthRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.NumberHandler
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.AESCrypt
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.dialogs.CountryCodeDialog
import com.mybus.mybusapp.models.CountryModel
import com.mybus.mybusapp.models.MemberModel
import com.mybus.mybusapp.models.RegisterUserModel
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : ActivityBase() {

    private var FCMToken: String? = ""
    private var address: String? = ""
    val TAG: String? = "Log"
    private var ageNumber:Int?=0
    var selectedCountryCode = 966
    var countryCodeDialog: CountryCodeDialog? = null
    private var isUser: Boolean = false
    private  var isDriverActive :Boolean = false
    private var numSeats:Int=0
    var isSelectLocation = false
    var busNumber = 0
    var busName: String? = ""
    var busColor: String? = ""
    var busModel: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = ""


        val bundle = intent.extras;
        if (bundle != null) {
            isUser = bundle.getBoolean(Constants.KEY_IS_CUSTOMER)
        }

        if (isUser) {
            userTypeTxt.text = getString(R.string.user)
        } else {
            userTypeTxt.text = getString(R.string.driver)
            busLy.visibility=View.VISIBLE
            isDriverActive=true

        }

        homeBtn.setOnClickListener {
            onBackPressed()
        }



        loginBtn.setOnClickListener {

            val intent = Intent(getActiviy(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            ageNumber= NumberHandler.arabicToDecimal(ageTxt.text.toString().trim()).toInt()
            if (isUser) {
                if (isValidForm()&& ageNumber!! >18){
                    registerUser()
                }
                else{
                    ageTxt?.error =getString(R.string.age_must_be_morethan)
                    Toast(R.string.age_must_be_morethan)
                }

            }
            else {
                if (isValidFormForDriver()&& ageNumber!! >18){
                    registerUser()
                }
                else{
                    ageTxt?.error =getString(R.string.age_must_be_morethan)
                    Toast(R.string.age_must_be_morethan)
                }

            }




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
        val phoneUtils = PhoneNumberUtil.createInstance(getActiviy())

//        val supportedRegions = phoneUtils.supportedRegions
        selectedCountryCode = phoneUtils.getCountryCodeForRegion(isoCode.toUpperCase())
    }

    private fun registerUser() {

        try {

            var mobileStr = NumberHandler.arabicToDecimal(mobileTxt.text.toString())
            val passwordStr = NumberHandler.arabicToDecimal(passwordTxt.text.toString())
            val fullNameStr = NumberHandler.arabicToDecimal(fullNameTxt.text.toString())
            val  ageStr = NumberHandler.arabicToDecimal(ageTxt.text.toString().trim())
            if(!isUser){
                numSeats= NumberHandler.arabicToDecimal(numSeatTxt.text.toString().trim()).toInt()
                busName=NumberHandler.arabicToDecimal(busNameTxt.text.toString())
                busNumber=NumberHandler.arabicToDecimal(busNumberTxt.text.toString().trim()).toInt()
                busColor=NumberHandler.arabicToDecimal(busColorTxt.text.toString())
                busModel=NumberHandler.arabicToDecimal(busModelTxt.text.toString())
            }

            val registerUserModel = RegisterUserModel()
            registerUserModel.countryCode = selectedCountryCode
            registerUserModel.mobile =
                if (mobileStr.startsWith("0")) mobileStr.replaceFirst(
                    "0",
                    ""
                ) else mobileStr

            registerUserModel.isVerified = false
            registerUserModel.password = AESCrypt.encrypt(passwordStr);
            registerUserModel.password_confirm = AESCrypt.encrypt(passwordStr)
            registerUserModel.mobileWithCountry =
                selectedCountryCode.toString().plus(registerUserModel.mobile)
            registerUserModel.age=ageStr.toInt()
            registerUserModel.fullName=fullNameStr
            registerUserModel.busLoading=numSeats
            registerUserModel.type = if (isUser) 1 else 2
            registerUserModel.isDriverActive=isDriverActive
            registerUserModel.address=address
            registerUserModel.fillySeat=0
            registerUserModel.emptySeat=0
            registerUserModel.isSelectLocation=false
            registerUserModel.busModel=busModel
            registerUserModel.busColor=busColor
            registerUserModel.busName=busName
            registerUserModel.busNumber=busNumber






            GlobalData.progressDialog(
                getActiviy(),
                R.string.register,
                R.string.please_wait_register
            )

            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    GlobalData.progressDialogHide()

                    if (func == Constants.SUCCESS) {
                        Log.d(TAG, "phoneNumber:${registerUserModel.mobileWithCountry}")

                        val user = MemberModel().apply {
                            countryCode = registerUserModel.countryCode
                            mobile = registerUserModel.mobile
                            mobileWithCountry = registerUserModel.mobileWithCountry
                            password = registerUserModel.password
                            isVerified = true
                        }
                        val intent = Intent(getActiviy(), ConfirmActivity::class.java)
                        intent.putExtra(Constants.KEY_MEMBER, user)
                        intent.putExtra(Constants.KEY_MOBILE, registerUserModel.mobileWithCountry)
                        startActivity(intent)
                    } else {
                        var message = getString(R.string.fail_to_register)
                        if (func == Constants.USER_EXIST)
                            message = getString(R.string.mobile_number_is_exist)

                        GlobalData.errorDialog(
                            getActiviy(),
                            R.string.register,
                            message
                        )
                    }


                }
            }).registerHandle(registerUserModel)

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

    private fun isValidForm(): Boolean {

        return FormValidator.getInstance()

            .addField(
                fullNameTxt,
                NonEmptyRule(R.string.enter_fill_name),
            )
            .addField(
                ageTxt,
                NonEmptyRule(R.string.enter_age),


            )
            .addField(
                mobileTxt,
                NonEmptyRule(R.string.enter_phone_number),
                LengthRule(10, R.string.valid_phone_number)

            )

            .addField(
                passwordTxt,
                NonEmptyRule(R.string.enter_password)
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

    private fun isValidFormForDriver(): Boolean {

        return FormValidator.getInstance()

            .addField(
                fullNameTxt,
                NonEmptyRule(R.string.enter_fill_name),
            )
            .addField(
                ageTxt,
                NonEmptyRule(R.string.enter_age),
                )

            .addField(
                busNumberTxt,
                NonEmptyRule(R.string.enter_bus_number),

                )
            .addField(
                busNameTxt,
                NonEmptyRule(R.string.enter_bus_name),

                )
            .addField(
                busModelTxt,
                NonEmptyRule(R.string.enter_bus_model),

                )
            .addField(
                busColorTxt,
                NonEmptyRule(R.string.enter_bus_color),

                )


            .addField(
                numSeatTxt,
                NonEmptyRule(R.string.enter_number_passenger),

                )

            .addField(
                mobileTxt,
                NonEmptyRule(R.string.enter_phone_number),
                LengthRule(10, R.string.valid_phone_number)

            )

            .addField(
                passwordTxt,
                NonEmptyRule(R.string.enter_password)
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
