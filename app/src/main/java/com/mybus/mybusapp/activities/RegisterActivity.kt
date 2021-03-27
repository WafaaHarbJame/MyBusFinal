package com.mybus.mybusapp.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.github.dhaval2404.form_validation.rule.EmailRule
import com.github.dhaval2404.form_validation.rule.EqualRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.DateHandler
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
import kotlinx.android.synthetic.main.activity_add_driver.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.busColorInput
import kotlinx.android.synthetic.main.activity_register.busModelInput
import kotlinx.android.synthetic.main.activity_register.busNumberInput
import kotlinx.android.synthetic.main.activity_register.confirmPasswordInput
import kotlinx.android.synthetic.main.activity_register.countryCodeTxt
import kotlinx.android.synthetic.main.activity_register.emailInput
import kotlinx.android.synthetic.main.activity_register.fullNameInput
import kotlinx.android.synthetic.main.activity_register.mobileInput
import kotlinx.android.synthetic.main.activity_register.numSeatInput
import kotlinx.android.synthetic.main.activity_register.passwordInput
import java.util.*


class RegisterActivity : ActivityBase() {

    private var FCMToken: String? = ""
    private var address: String? = ""
    val TAG: String? = "Log"
    private var ageNumber: Int? = 0
    var selectedCountryCode = 966
    var countryCodeDialog: CountryCodeDialog? = null
    private var isUser: Boolean = false
    private var isDriverActive: Boolean = false

    private var numSeats: Int = 0
    var isSelectLocation = false
    var busNumber = 0
    var busName: String? = ""
    var busColor: String? = ""
    var busModel: String? = ""

    var selectedYear: Int? = 0
    var selectedMonth: Int? = 0
    var selectedDay: Int? = 0
    var maxDate: Long = 0
    var email: String? = ""

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
            busLy.visibility = View.VISIBLE
            isDriverActive = true

        }

        homeBtn.setOnClickListener {
            onBackPressed()
        }

        ageTxt.setOnClickListener {

            val datePickerDialog = DatePickerDialog(this, { view2, thisYear, thisMonth, thisDay ->
                selectedYear = thisYear
                selectedMonth = thisMonth + 1
                selectedDay = thisDay
                ageTxt.text = DateHandler.FormatDate4(
                    "$selectedYear-$selectedMonth-$selectedDay",
                    "yyyy-MM-dd",
                    "yyyy-MM-dd"
                )

                ageNumber = DateHandler.getAge(
                    selectedYear ?: 0,
                    selectedMonth ?: 0,
                    selectedDay ?: 0
                ).toInt()

            }, selectedYear!!, selectedMonth!!, selectedDay!!)

            datePickerDialog.datePicker.maxDate = maxDate
            datePickerDialog.show()
            datePickerDialog.datePicker.touchables[0].performClick()


        }



        loginBtn.setOnClickListener {

            val intent = Intent(getActiviy(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        registerBtn.setOnClickListener {

            if (isValidForm() && ageNumber!!.toInt() >= 18) {
                registerUser()
            } else {
                ageTxt?.error = getString(R.string.age_must_be_morethan)
                Toast(R.string.age_must_be_morethan)
            }

        }

        initDates()

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
        // selectedCountryCode = phoneUtils.getCountryCodeForRegion(isoCode.toUpperCase())
    }

    private fun registerUser() {

        try {

            val mobileStr = NumberHandler.arabicToDecimal(mobileTxt.text.toString())
            val passwordStr = NumberHandler.arabicToDecimal(passwordTxt.text.toString())
            val fullNameStr = NumberHandler.arabicToDecimal(NameTxt.text.toString())
            val birthDateStr = NumberHandler.arabicToDecimal(ageTxt.text.toString().trim())
            val emailStr = NumberHandler.arabicToDecimal(emailTxt.text.toString())

            if (!isUser) {
                numSeats = NumberHandler.arabicToDecimal(numSeatTxt.text.toString().trim()).toInt()
                busNumber =
                    NumberHandler.arabicToDecimal(busNumberTxt.text.toString().trim()).toInt()
                busColor = NumberHandler.arabicToDecimal(busColorTxt.text.toString())
                busModel = NumberHandler.arabicToDecimal(busModelTxt.text.toString())
            }

            val registerUserModel = MemberModel()
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
            registerUserModel.birthDate = birthDateStr
            registerUserModel.age = ageNumber!!
            registerUserModel.fullName = fullNameStr
            registerUserModel.busLoading = numSeats
            registerUserModel.emptySeat = numSeats
            registerUserModel.type = if (isUser) 1 else 2
            registerUserModel.isDriverActive = isDriverActive
            registerUserModel.address = address
            registerUserModel.fillySeat = 0
            registerUserModel.isSelectLocation = false
            registerUserModel.busModel = busModel
            registerUserModel.busColor = busColor
            registerUserModel.busName = busName
            registerUserModel.busNumber = busNumber
            registerUserModel.email = emailStr


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

                        val intent = Intent(getActiviy(), ConfirmActivity::class.java)
                        intent.putExtra(Constants.KEY_MEMBER, registerUserModel)
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
        if (isUser) {
            return FormValidator.getInstance()

                .addField(
                    fullNameInput,
                    NonEmptyRule(R.string.enter_fill_name),
                )

                .addField(
                    emailInput,
                    NonEmptyRule(R.string.ENTER_EMAIL),
                    EmailRule(R.string.enter_vaild_email)

                )

                .addField(
                    mobileInput,
                    NonEmptyRule(R.string.enter_phone_number),
//                    LengthRule(10, R.string.valid_phone_number)
                )

                .addField(
                    passwordInput,
                    NonEmptyRule(R.string.enter_password)
                )
                .addField(
                    confirmPasswordInput,
                    NonEmptyRule(R.string.enter_password),
                    EqualRule(
                        passwordTxt.text.toString(),
                        R.string.password_confirm_not_match
                    )
                )

                .validate()

        } else {
            return FormValidator.getInstance()
                .addField(
                    fullNameInput,
                    NonEmptyRule(R.string.enter_fill_name),
                )
                .addField(
                    ageTxt,
                    NonEmptyRule(R.string.enter_age),
                )

                .addField(
                    busNumberInput,
                    NonEmptyRule(R.string.enter_bus_number),

                    )

                .addField(
                    busModelInput,
                    NonEmptyRule(R.string.enter_bus_model),

                    )
                .addField(
                    busColorInput,
                    NonEmptyRule(R.string.enter_bus_color),

                    )


                .addField(
                    numSeatInput,
                    NonEmptyRule(R.string.enter_number_passenger),

                    )

                .addField(
                    mobileInput,
                    NonEmptyRule(R.string.enter_phone_number),
//                    LengthRule(10, R.string.valid_phone_number)

                )

                .addField(
                    emailInput,
                    NonEmptyRule(R.string.ENTER_EMAIL),
                    EmailRule(R.string.enter_vaild_email)

                )
                .addField(
                    passwordInput,
                    NonEmptyRule(R.string.enter_password)
                )
                .addField(
                    confirmPasswordInput,
                    NonEmptyRule(R.string.enter_password),
                    EqualRule(
                        passwordTxt.text.toString(),
                        R.string.password_confirm_not_match
                    )
                )

                .validate()
        }

    }


    private fun initDates() {

        val c = Calendar.getInstance()
        c.add(Calendar.HOUR_OF_DAY, 1)
        maxDate = c.time.time - 60000
        c[Calendar.YEAR] = 1990
        selectedYear = c[Calendar.YEAR]
        selectedMonth = c.get(Calendar.MONTH)
        selectedDay = c.get(Calendar.DAY_OF_MONTH)

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
