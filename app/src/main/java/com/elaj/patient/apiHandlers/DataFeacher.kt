package com.elaj.patient.apiHandlers

import android.util.Log
import com.elaj.patient.RootApplication
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.DBFunction
import com.elaj.patient.models.*
import com.google.gson.Gson
import okhttp3.MultipartBody


class DataFeacher(callBack: DataFetcherCallBack?) {
    var dataFetcherCallBack: DataFetcherCallBack? = callBack
//    var activity: Activity? = Activity()

    var fireStoreDB = RootApplication.fireStoreDB


    val TAG: String? = "Log"

    //    var accessToken: String?
//    var lang: String?
    var headerMap: MutableMap<String, Any?> = HashMap()

    /*********************************** POST Fetcher  **********************************/
    fun loginHandle(memberModel: RegisterUserModel?) {

        Log.i(TAG, "Log loginHandle")
        Log.i(TAG, "Log mobile " + memberModel?.mobile)
        Log.i(TAG, "Log password " + memberModel?.password)
        val phoneNumber = memberModel?.mobileWithCountry
        fireStoreDB?.collection(ApiUrl.Users.name)?.document(phoneNumber!!)?.get()
            ?.addOnSuccessListener { document ->
                if (document.exists()) {
                    dataFetcherCallBack?.Result(document, Constants.SUCCESS, true)

                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                }

            }


    }

    fun registerHandle(memberModel: RegisterUserModel) {

        Log.i(TAG, "Log countryCode ${memberModel.countryCode}")
        Log.i(TAG, "Log mobile ${memberModel.mobile}")

        val phoneNumber = memberModel.mobileWithCountry.toString()
//        this.activity = activity

        fireStoreDB!!.collection(ApiUrl.Users.name).document(phoneNumber).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    dataFetcherCallBack?.Result(null, Constants.USER_EXIST, false)

                } else {
                    fireStoreDB!!.collection(ApiUrl.Users.name).document(phoneNumber)
                        .set(memberModel)
                        .addOnSuccessListener {
                            dataFetcherCallBack?.Result(memberModel, Constants.SUCCESS, true)
                        }.addOnFailureListener {
                            dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, true)
                        }

                }

            }

    }


    fun confirmAccount(mobile: String) {

        Log.i(TAG, "Log confirmAccount")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log mobile $mobile")

        RootApplication.fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile)
            ?.update("isVerified", true)?.addOnSuccessListener {

                dataFetcherCallBack?.Result("", Constants.SUCCESS, true)

            }?.addOnFailureListener { e ->
                dataFetcherCallBack?.Result("", Constants.FAIL_DATA, true)
            }

    }

    fun getMyAccount(mobile: String) {

        Log.i(TAG, "Log confirmAccount")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log mobile $mobile")

        RootApplication.fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile)
            ?.get()?.addOnSuccessListener {

                val user = it.toObject(MemberModel::class.java)

                dataFetcherCallBack?.Result(user, Constants.SUCCESS, true)

            }?.addOnFailureListener { e ->
                dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, true)
            }

    }


    fun resendConfirmRegister(countryCode: Int, mobile: String) {

        val params: MutableMap<String?, Any?> = HashMap()

        params["country_code"] = countryCode
        params["mobile"] = mobile

        Log.i(TAG, "Log resendConfirmRegister")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log country_code $countryCode")
        Log.i(TAG, "Log mobile $mobile")

    }

    fun forgetPassword(mobile: String) {
        Log.i(TAG, "Log forgetPassword")
        Log.i(TAG, "Log mobile $mobile")

        fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile)?.get()
            ?.addOnSuccessListener { document ->
                if (document.exists()) {
                    dataFetcherCallBack?.Result(document, Constants.SUCCESS, true)

                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, true)

                }
            }
            ?.addOnFailureListener {
                dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, true)

            }


    }

    fun resetPassword(mobile: String, newPassword: String) {

        Log.i(TAG, "Log resetPassword")
        Log.i(TAG, "Log mobile $mobile")
        Log.i(TAG, "Log new_password $newPassword")
        Log.i(TAG, "Log confirm_password $newPassword")

        RootApplication.fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile)
            ?.update("password", newPassword, "password_confirm", newPassword)
            ?.addOnSuccessListener {

                dataFetcherCallBack?.Result("", Constants.SUCCESS, true)

            }?.addOnFailureListener { e ->
                dataFetcherCallBack?.Result("", Constants.FAIL_DATA, true)
            }


    }

    fun sendSupport(supportModel: SupportModel) {

        Log.i(TAG, "Log sendSupport")
        Log.i(TAG, "Log email ${supportModel.email}")
        Log.i(TAG, "Log messageTitle ${supportModel.messageTitle}")
        Log.i(TAG, "Log messageText ${supportModel.messageText}")

        fireStoreDB!!.collection(ApiUrl.Supports.name).document()
            .set(supportModel)
            .addOnSuccessListener {
                dataFetcherCallBack?.Result(supportModel, Constants.SUCCESS, true)
            }.addOnFailureListener {
                dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, true)
            }


    }

    fun sendQuestion(questionModel: QuestionModel) {

        Log.i(TAG, "Log sendQuestion")
        Log.i(TAG, "Log userMobile ${questionModel.userMobile}")
        Log.i(TAG, "Log title ${questionModel.title}")
        Log.i(TAG, "Log details ${questionModel.details}")
        Log.i(TAG, "Log type ${questionModel.type}")
        if (questionModel.type == QuestionModel.CALL) {
            Log.i(TAG, "Log whatsAppNumber ${questionModel.whatsAppNumber}")
            Log.i(TAG, "Log skypeNumber ${questionModel.skypeNumber}")
        } else {
            Log.i(TAG, "Log age ${questionModel.age}")
            Log.i(TAG, "Log gender ${questionModel.gender}")
        }

        fireStoreDB!!.collection(ApiUrl.Questions.name).document()
            .set(questionModel)
            .addOnSuccessListener {
                dataFetcherCallBack?.Result("", Constants.SUCCESS, true)
            }.addOnFailureListener {
                dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, true)
            }


    }

    fun changePassword(mobile: String?, oldPassword: String, newPassword: String) {

        Log.i(TAG, "Log changePassword")
        Log.i(TAG, "Log mobile changePassword $mobile")
        Log.i(TAG, "Log oldPassword $oldPassword")
        Log.i(TAG, "Log newPassword $newPassword")


        fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile!!)?.get()
            ?.addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(MemberModel::class.java)
                    val password = user?.password
                    Log.i(TAG, "Log oldPassword from fire $password")

                    if (oldPassword == password) {
                        RootApplication.fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile)
                            ?.update("password", newPassword, "password_confirm", newPassword)
                            ?.addOnSuccessListener {

                                dataFetcherCallBack?.Result("", Constants.SUCCESS, true)

                            }?.addOnFailureListener { e ->
                                dataFetcherCallBack?.Result("", Constants.FAIL_DATA, true)
                            }
                    }
                    else{
                        dataFetcherCallBack?.Result("", Constants.PASSWORD_WRONG, true)

                    }

                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                }

            }


    }


    fun editProviderProfile(
        editProviderModel: EditProviderModel
    ) {

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        builder.addFormDataPart("full_name", editProviderModel.fullName)
        builder.addFormDataPart("country_code", editProviderModel.countryCode.toString())
        builder.addFormDataPart("mobile", editProviderModel.mobile)
        builder.addFormDataPart("email", editProviderModel.email)
        builder.addFormDataPart("city_id", editProviderModel.cityId.toString())
        builder.addFormDataPart("brief_summary_ar", editProviderModel.briefDetailsAr)
        builder.addFormDataPart("brief_summary_en", editProviderModel.briefDetailsEn)
        builder.addFormDataPart("address_ar", editProviderModel.addressAr)
        builder.addFormDataPart("address_en", editProviderModel.addressEn)
        builder.addFormDataPart("lat", editProviderModel.lat.toString())
        builder.addFormDataPart("lng", editProviderModel.lat.toString())

        val requestBody = builder.build()

        Log.i(TAG, "Log editProviderProfile")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log full_name ${editProviderModel.fullName}")
        Log.i(TAG, "Log country_code ${editProviderModel.countryCode}")
        Log.i(TAG, "Log mobile ${editProviderModel.mobile}")
        Log.i(TAG, "Log email ${editProviderModel.email}")
        Log.i(TAG, "Log cityId ${editProviderModel.cityId}")
        Log.i(TAG, "Log brief_details_ar ${editProviderModel.briefDetailsAr}")
        Log.i(TAG, "Log brief_details_en ${editProviderModel.briefDetailsEn}")
        Log.i(TAG, "Log address_ar ${editProviderModel.addressAr}")
        Log.i(TAG, "Log address_en ${editProviderModel.addressEn}")
        Log.i(TAG, "Log lat ${editProviderModel.lat}")
        Log.i(TAG, "Log lng ${editProviderModel.lng}")
//        Log.i(TAG, "Log photosListIds ${editProviderModel.photosListIds}")
//        Log.i(TAG, "Log work_hour ${editProviderModel.workHourList.size}")
//        Log.i(TAG, "Log services ${editProviderModel.servicesList.size}")

    }

    /************************************  GET Feacher *******************************/

    fun logOut() {
        Log.i(TAG, "Log logOut")

    }

    fun getCategorySyndromes(catId: String) {

        Log.i(TAG, "Log getCategorySyndromes")

        fireStoreDB?.collection(ApiUrl.Categories.name)?.document(catId)
            ?.collection(ApiUrl.Syndromes.name)?.get()
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    val query = it.result

                    val syndromesList = mutableListOf<SyndromeModel>()
                    for (document in query!!) {
                        val syndrome = document?.toObject(SyndromeModel::class.java)
                        syndrome?.id = document?.id
                        syndromesList.add(syndrome!!)
//                    Log.d(TAG, "${document.id} => ${document.data}")
                    }
//
                    dataFetcherCallBack?.Result(syndromesList, Constants.SUCCESS, true)
//
                } else {
                    it.exception?.printStackTrace()

                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)
                }

            }
//        val json = Gson().toJson(result!!.data)
//        DBFunction.setCategories(json)
//
//        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)
//
//        EventBus.getDefault()
//            .post(ResponseEvent("getCategories", Constants.SUCCESS, result))

    }


    fun getSettings() {
        Log.i(TAG, "Log getSettings")

        fireStoreDB?.collection(ApiUrl.Settings.name)?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                val query = it.result

                val settingsModel = SettingsModel()
                for (document in query!!) {
                    when (document?.id) {
                        "welcome" -> {
                            settingsModel.welcome = document.toObject(WelcomeModel::class.java)
                        }
                    }
//                    Log.d(TAG, "${document.id} => ${document.data}")
                }

                val json = Gson().toJson(settingsModel)
                DBFunction.setSettings(json)
//
                dataFetcherCallBack?.Result(settingsModel, Constants.SUCCESS, true)
//
            } else {
                it.exception?.printStackTrace()
                dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)
            }

        }

    }

    fun getCountries() {
        Log.i(TAG, "Log getCountries")

        fireStoreDB?.collection(ApiUrl.Countries.name)?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                val query = it.result

                val list = mutableListOf<CountryModel>()
                for (document in query!!) {
                    val country = document?.toObject(CountryModel::class.java)
                    country?.id = document?.id
                    list.add(country!!)
//                    Log.d(TAG, "${document.id} => ${document.data}")
                }

                val json = Gson().toJson(list)
                DBFunction.setCountries(json)
//
                dataFetcherCallBack?.Result(list, Constants.SUCCESS, true)
//
            } else {
                it.exception?.printStackTrace()
            }

        }
//        val json = Gson().toJson(result!!.data)
//        DBFunction.setCategories(json)
//
//        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)
//
//        EventBus.getDefault()
//            .post(ResponseEvent("getCategories", Constants.SUCCESS, result))

    }

    fun getCategories() {
        Log.i(TAG, "Log getCategories")

        fireStoreDB?.collection(ApiUrl.Categories.name)?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                val query = it.result

                val catList = mutableListOf<CategoryModel>()
                for (document in query!!) {
                    val category = document?.toObject(CategoryModel::class.java)
                    category?.id = document?.id
                    catList.add(category!!)
//                    Log.d(TAG, "${document.id} => ${document.data}")
                }

                val json = Gson().toJson(catList)
                DBFunction.setCategories(json)
//
                dataFetcherCallBack?.Result(catList, Constants.SUCCESS, true)
//
            } else {
                it.exception?.printStackTrace()
            }

        }
//        val json = Gson().toJson(result!!.data)
//        DBFunction.setCategories(json)
//
//        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)
//
//        EventBus.getDefault()
//            .post(ResponseEvent("getCategories", Constants.SUCCESS, result))

    }

    fun getSliders() {
        Log.i(TAG, "Log getSliders")

        fireStoreDB?.collection(ApiUrl.Sliders.name)?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                val query = it.result

                val sliderList = mutableListOf<SliderModel>()
                for (document in query!!) {
                    val slider = document?.toObject(SliderModel::class.java)
                    sliderList.add(slider!!)
//                    Log.d(TAG, "${document.id} => ${document.data}")
                }

                val json = Gson().toJson(sliderList)
                DBFunction.setSliders(json)
//
                dataFetcherCallBack?.Result(sliderList, Constants.SUCCESS, true)
//
            } else {
                it.exception?.printStackTrace()
            }

        }
//        val json = Gson().toJson(result!!.data)
//        DBFunction.setCategories(json)
//
//        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)
//
//        EventBus.getDefault()
//            .post(ResponseEvent("getCategories", Constants.SUCCESS, result))

    }

    fun getPlans() {
        Log.i(TAG, "Log getPlans")

        fireStoreDB?.collection(ApiUrl.Plans.name)?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                val query = it.result

                val objectList = mutableListOf<PlansModel>()
                for (document in query!!) {
                    val obj = document?.toObject(PlansModel::class.java)
                    objectList.add(obj!!)
//                    Log.d(TAG, "${document.id} => ${document.data}")
                }

                val json = Gson().toJson(objectList)
                DBFunction.setPlans(json)
//
                dataFetcherCallBack?.Result(objectList, Constants.SUCCESS, true)
//
            } else {
                it.exception?.printStackTrace()
            }

        }
//        val json = Gson().toJson(result!!.data)
//        DBFunction.setCategories(json)
//
//        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)
//
//        EventBus.getDefault()
//            .post(ResponseEvent("getCategories", Constants.SUCCESS, result))

    }

    private fun printLog(o: Any?) {
        Log.v("Log", "Log " + o.toString())
    }
}