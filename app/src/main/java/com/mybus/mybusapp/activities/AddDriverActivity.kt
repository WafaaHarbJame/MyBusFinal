package com.mybus.mybusapp.activities

import android.R.attr.maxDate
import android.app.DatePickerDialog
import android.os.Bundle
import com.github.dhaval2404.form_validation.rule.EmailRule
import com.github.dhaval2404.form_validation.rule.EqualRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.mybus.mybusapp.R
import com.mybus.mybusapp.Utils.DateHandler
import com.mybus.mybusapp.Utils.NumberHandler
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.AESCrypt
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.DBFunction
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.dialogs.CountryCodeDialog
import com.mybus.mybusapp.models.CountryModel
import com.mybus.mybusapp.models.DriverModel
import kotlinx.android.synthetic.main.activity_add_driver.*
import kotlinx.android.synthetic.main.activity_add_driver.busColorInput
import kotlinx.android.synthetic.main.activity_add_driver.busModelInput
import kotlinx.android.synthetic.main.activity_add_driver.busNumberInput
import kotlinx.android.synthetic.main.activity_add_driver.confirmPasswordInput
import kotlinx.android.synthetic.main.activity_add_driver.countryCodeTxt
import kotlinx.android.synthetic.main.activity_add_driver.emailInput
import kotlinx.android.synthetic.main.activity_add_driver.fullNameInput
import kotlinx.android.synthetic.main.activity_add_driver.mobileInput
import kotlinx.android.synthetic.main.activity_add_driver.numSeatInput
import kotlinx.android.synthetic.main.activity_add_driver.passwordInput
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class AddDriverActivity : ActivityBase() {

    var selectedCountryCode = 966
    var countryCodeDialog: CountryCodeDialog? = null
    private var countryModels: MutableList<CountryModel>? = mutableListOf()

//    var pickImageDialog: PickImageDialog? = null
//    val REQUEST_PICK_IMAGE = 11
//    var choosePhotoHelper: ChoosePhotoHelper? = null
//    private var selectedPhotoUri: Uri? = null
//    private var imagePath: String? = null

    //    var selectedPhotoFile: File? = null
//    var storage: FirebaseStorage? = null
//    var storageRef: StorageReference? = null


    var driverModel: DriverModel? = null
    var isEdit: Boolean? = false
//    var dataMap = mutableMapOf<String, Any?>()

    var selectedYear: Int? = 0
    var selectedMonth: Int? = 0
    var selectedDay: Int? = 0
    var maxDate: Long? = 0
    private var ageNumber: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_driver)

        setupUI(parentLY)

//        storage = FirebaseStorage.getInstance()
//        storageRef = storage?.reference

//        val bundle = intent.extras
        var titleAct = getString(R.string.manage_drivers)
        if (intent.hasExtra(Constants.KEY_DRIVER_MODEL)) {
            driverModel = intent.getSerializableExtra(Constants.KEY_DRIVER_MODEL) as DriverModel
            titleAct = getString(R.string.edit_driver)
            isEdit = true
        }
        title = titleAct

//        initLocalCountryCode()

        val countryCodeStr = "+$selectedCountryCode"
        countryCodeTxt.text = countryCodeStr

        initListeners()

        getCountries()

        initDates()

        if (driverModel != null)
            initData()

    }

    private fun initListeners() {

        ageTV.setOnClickListener {

            val datePickerDialog = DatePickerDialog(this, { view2, thisYear, thisMonth, thisDay ->
                selectedYear = thisYear
                selectedMonth = thisMonth + 1
                selectedDay = thisDay
                ageTV.text = DateHandler.FormatDate4(
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

            datePickerDialog.datePicker.maxDate = maxDate!!
            datePickerDialog.show()
            datePickerDialog.datePicker.touchables[0].performClick();

        }

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

        sendBtn.setOnClickListener {
            if (isValidForm()) {
                val mobileStr = NumberHandler.arabicToDecimal(mobileET.text.toString())
                if (mobileStr.length <= 6) {
                    mobileInput.error = getString(R.string.invalid_mobile)
                    return@setOnClickListener
                }
                addDriver()
            }

        }
//        userImg.setOnClickListener {
//            pickImage()
//
//        }

    }

    private fun initData() {

        fullNameET.setText(driverModel?.fullName)
        emailET.setText(driverModel?.email)
        mobileET.setText(driverModel?.mobile)
        selectedCountryCode = driverModel?.countryCode!!
        countryCodeTxt.text = driverModel?.countryCode.toString()
        ageTV.text = driverModel?.birthDate
        if (driverModel?.birthDate?.isNotEmpty() == true) {
            val dateArr = driverModel?.birthDate?.split("-")
            ageNumber = DateHandler.getAge(
                dateArr?.get(0)?.toInt() ?: 0,
                dateArr?.get(1)?.toInt() ?: 0,
                dateArr?.get(2)?.toInt() ?: 0
            ).toInt()
        }


        busNameET.setText(driverModel?.busName)
        busNumberET.setText(driverModel?.busNumber.toString())
        busModelET.setText(driverModel?.busModel)
        busColorET.setText(driverModel?.busColor)
        numSeatET.setText(driverModel?.busLoading.toString())

        activeCB.isChecked = driverModel?.isDriverActive == true

//        Glide.with(getActiviy())
//            .asBitmap()
//            .load(driverModel?.)
//            .placeholder(R.drawable.error_logo)
//            .into(userImg)

    }

    private fun addDriver() {

        try {
            val fullNameStr = NumberHandler.arabicToDecimal(fullNameET.text.toString())
            val emailStr = NumberHandler.arabicToDecimal(emailET.text.toString())
            val mobileStr = NumberHandler.arabicToDecimal(mobileET.text.toString())
            val passwordStr = NumberHandler.arabicToDecimal(passwordET.text.toString())
            val birthDateStr = NumberHandler.arabicToDecimal(ageTV.text.toString())

            val busNameStr = NumberHandler.arabicToDecimal(busNameET.text.toString())
            val busNumberStr = NumberHandler.arabicToDecimal(busNumberET.text.toString())
            val busModelStr = NumberHandler.arabicToDecimal(busModelET.text.toString())
            val busColorStr = NumberHandler.arabicToDecimal(busColorET.text.toString())
            val numSeatStr = NumberHandler.arabicToDecimal(numSeatET.text.toString())


            val mobile = if (mobileStr.startsWith("0")) mobileStr.replaceFirst(
                "0",
                ""
            ) else mobileStr

            if (driverModel == null)
                driverModel = DriverModel()

            driverModel?.fullName = fullNameStr
            driverModel?.email = emailStr
            driverModel?.birthDate = birthDateStr
            driverModel?.age = ageNumber ?: 0
            driverModel?.countryCode = selectedCountryCode
            driverModel?.mobile = mobile
            driverModel?.mobileWithCountry = selectedCountryCode.toString().plus(mobile)
            driverModel?.isDriverActive = activeCB.isChecked
            driverModel?.password = AESCrypt.encrypt(passwordStr)
            driverModel?.password_confirm = AESCrypt.encrypt(passwordStr)
            driverModel?.type = 2

            driverModel?.busName = busNameStr
            driverModel?.busNumber = busNumberStr.toInt()
            driverModel?.busModel = busModelStr
            driverModel?.busColor = busColorStr
            driverModel?.busLoading = numSeatStr.toInt()

//            dataMap.apply {
//
//                this["name"] = fullNameStr
//                this["email"] = emailStr
//                this["countryCode"] = selectedCountryCode
//                this["mobile"] = mobile
//                this["active"] = activeCB.isChecked
//                if (passwordStr.isNotEmpty()) {
//                    this["password"] = AESCrypt.encrypt(passwordStr)
//                    this["password_confirm"] = AESCrypt.encrypt(passwordStr)
//                }
//                this["mobileWithCountry"] = selectedCountryCode.toString().plus(mobile)
//
//                this["busName"] = busNumberStr.toInt()
//                this["busNumber"] = busNumberStr.toInt()
//                this["busColor"] = busNumberStr.toInt()
//
//            }

//            if (selectedPhotoUri != null)
//                uploadImage()
//            else
            sendDoctorData()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendDoctorData() {

        GlobalData.progressDialog(
            getActiviy(),
            if (isEdit == true) R.string.edit_driver else R.string.add_driver,
            R.string.please_wait_sending
        )

        val dataFeacher = DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                GlobalData.progressDialogHide()

                if (func == Constants.SUCCESS) {

                    GlobalData.REFRESH_DRIVERS = true

                    GlobalData.successDialog(getActiviy(),
                        if (isEdit == true) R.string.edit_driver else R.string.add_driver,
                        getString(if (isEdit == true) R.string.success_edit_driver else R.string.success_add_driver),
                        object : DataFetcherCallBack {
                            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                                finish()
                            }
                        })

//                    AwesomeSuccessDialog(getActiviy())
//                        .setTitle(R.string.add_doctor)
//                        .setMessage(getString(R.string.success_add_doctor))
//                        .setColoredCircle(R.color.dialogSuccessBackgroundColor)
//                        .setDialogIconAndColor(R.drawable.ic_check, R.color.white)
//                        .setCancelable(true)
//                        .show()
//                        .setOnDismissListener {
//                            finish()
//                        }


                } else {

                    GlobalData.errorDialog(
                        getActiviy(),
                        if (isEdit == true) R.string.edit_driver else R.string.add_driver,
                        getString(if (func == Constants.USER_EXIST) R.string.mobile_number_used_before else R.string.fail_to_send)
                    )
                }


            }
        })

        if (isEdit == true)
            dataFeacher.editDriver(driverModel!!)
        else
            dataFeacher.addDriver(driverModel!!)

    }

    private fun getCountries() {
        countryModels = DBFunction.getCountries()
        if (countryModels == null || countryModels?.isEmpty()!!) {
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                    if (func == Constants.SUCCESS)
                        getCountries()
                }
            }).getCountries()

        }
    }

    private fun isValidForm(): Boolean {
        if (isEdit == true)
            return FormValidator.getInstance()
                .addField(ageTV, NonEmptyRule(R.string.enter_age))
                .addField(busNameInput, NonEmptyRule(R.string.enter_bus_name))
                .addField(busNumberInput, NonEmptyRule(R.string.enter_bus_number))
                .addField(busModelInput, NonEmptyRule(R.string.enter_bus_model))
                .addField(busColorInput, NonEmptyRule(R.string.enter_bus_color))
                .addField(numSeatInput, NonEmptyRule(R.string.enter_number_passenger))
                .addField(mobileInput, NonEmptyRule(R.string.enter_phone_number))
                .addField(fullNameInput, NonEmptyRule(R.string.enter_fill_name))
                .addField(emailInput, NonEmptyRule(R.string.invalid_email))
                .addField(mobileInput, NonEmptyRule(R.string.invalid_mobile))
                .validate()
        else
            return FormValidator.getInstance()
                .addField(ageTV, NonEmptyRule(R.string.enter_age))
                .addField(busNameInput, NonEmptyRule(R.string.enter_bus_name))
                .addField(busNumberInput, NonEmptyRule(R.string.enter_bus_number))
                .addField(busModelInput, NonEmptyRule(R.string.enter_bus_model))
                .addField(busColorInput, NonEmptyRule(R.string.enter_bus_color))
                .addField(numSeatInput, NonEmptyRule(R.string.enter_number_passenger))
                .addField(mobileInput, NonEmptyRule(R.string.enter_phone_number))
                .addField(fullNameInput, NonEmptyRule(R.string.enter_fill_name))
                .addField(emailInput, NonEmptyRule(R.string.invalid_email))
                .addField(mobileInput, NonEmptyRule(R.string.invalid_mobile))
                .addField(passwordInput, NonEmptyRule(R.string.enter_password))
                .addField(
                    confirmPasswordInput,
                    NonEmptyRule(R.string.enter_password),
                    EqualRule(
                        passwordET.text.toString(),
                        R.string.password_confirm_not_match
                    )
                )
                .addField(
                    mobileInput,
                    NonEmptyRule(R.string.enter_phone_number),
                )
                .validate()
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

}


