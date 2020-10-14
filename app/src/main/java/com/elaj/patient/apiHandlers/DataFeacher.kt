package com.elaj.patient.apiHandlers

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.elaj.patient.MainActivityBottomNav
import com.elaj.patient.R
import com.elaj.patient.models.*
import com.elaj.patient.RootApplication
import com.elaj.patient.activities.ConfirmActivity
import com.elaj.patient.classes.AESCrypt
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.DBFunction
import com.elaj.patient.classes.GlobalData
import com.elaj.patient.classes.GlobalData.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.w3c.dom.Document
import java.io.File


class DataFeacher(callBack: DataFetcherCallBack?) {
    var dataFetcherCallBack: DataFetcherCallBack? = callBack
        var activity: Activity? = Activity()

    var fireStoreDB = RootApplication.fireStoreDB


    val TAG: String? = "Log"

    //    var accessToken: String?
//    var lang: String?
    var headerMap: MutableMap<String, Any?> = HashMap()

    /*********************************** POST Fetcher  **********************************/
    fun loginHandle(activity: Activity,memberModel: RegisterUserModel?) {

        val params: MutableMap<String?, Any?> = HashMap()

        Log.i(TAG, "Log loginHandle")
        Log.i(TAG, "Log mobile " + memberModel?.mobile)
        Log.i(TAG, "Log password " + memberModel?.password)
        val phoneNumber= memberModel?.mobileWithPlus.toString()
        this.activity=activity
        fireStoreDB?.collection(ApiUrl.Users.name)?.document(phoneNumber)?.get()
            ?.addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    val password=document.getString(Constants.PASSWORD)
                    val passStr= memberModel!!.password.toString();
                    val isVerified=document.getBoolean(Constants.isVerified)
                    if(password.equals(passStr)){
                        if(isVerified==true){
                            val intent = Intent(activity, MainActivityBottomNav::class.java)
                            activity.startActivity(intent)
                        }
                        else{

                            val intent = Intent(activity, ConfirmActivity::class.java)
                            intent.putExtra(Constants.KEY_MOBILE, phoneNumber)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            activity?.startActivity(intent)

                        }



                    } else{
                        Toast(activity,R.string.fail_to_login)
                        GlobalData.progressDialog(
                            activity,
                            R.string.sign_in,
                            R.string.please_wait_login,
                            false)
                    }

                } else {
                    Log.d(TAG, "No such document")
                    Toast(activity,R.string.not_have_account_q)
                }
            }


    }

    fun registerHandle(activity: Activity,memberModel: RegisterUserModel?) {
        val phoneNumber= memberModel?.mobileWithPlus.toString()
        this.activity=activity
        if (memberModel != null) {
            fireStoreDB!!.collection(ApiUrl.Users.name).document(phoneNumber).set(memberModel)
                .addOnSuccessListener {
                goToConfirmPage(phoneNumber)
            }.addOnFailureListener {
                Toast(activity,R.string.fail_to_register)

            }
        }

    }



    fun confirmRegister(countryCode: Int, mobile: String?, confirmCode: String) {

        val params: MutableMap<String?, Any?> = HashMap()

        params["country_code"] = countryCode
        params["mobile"] = mobile
        params["confirm_register"] = confirmCode

        Log.i(TAG, "Log confirmRegister")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log country_code $countryCode")
        Log.i(TAG, "Log mobile $mobile")
        Log.i(TAG, "Log confirm_register $confirmCode")

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

    fun forgetPassword(countryCode: Int, mobile: String?) {

        val params: MutableMap<String?, Any?> = HashMap()

        params["country_code"] = countryCode
        params["mobile"] = mobile

        Log.i(TAG, "Log forgetPassword")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log country_code $countryCode")
        Log.i(TAG, "Log mobile $mobile")

    }

    fun resetPassword(countryCode: Int, mobile: String, code: String, newPassword: String) {

        val params: MutableMap<String?, Any?> = HashMap()

        params["country_code"] = countryCode
        params["mobile"] = mobile
        params["confirmation_code"] = code
        params["new_password"] = newPassword
        params["confirm_password"] = newPassword

        Log.i(TAG, "Log resetPassword")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log country_code $countryCode")
        Log.i(TAG, "Log mobile $mobile")
        Log.i(TAG, "Log confirmation_code $code")
        Log.i(TAG, "Log new_password $newPassword")
        Log.i(TAG, "Log confirm_password $newPassword")

    }

    fun sendSupport(title: String, details: String, file: File?) {

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        builder.addFormDataPart("title", title)
        builder.addFormDataPart("message", details)

        if (file != null) {
            val requestImage: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            builder.addFormDataPart("photo", file.lastModified().toString() + ".png", requestImage)
        }

        val requestBody = builder.build()

        Log.i(TAG, "Log sendSupport")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log title $title")
        Log.i(TAG, "Log message $details")

//        if (dataFetcherCallBack != null)
//            dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)
//
//        EventBus.getDefault()
//            .post(ResponseEvent("sendSupport", Constants.NO_CONNECTION))

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
            }

        }

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
    private fun goToConfirmPage(phoneNumber:String ) {

        Log.d(TAG, "phoneNumber:$phoneNumber")
        GlobalData.progressDialog(
           activity,
            R.string.register,
            R.string.please_wait_register,
            false
        )
        val intent = Intent(activity, ConfirmActivity::class.java)
        intent.putExtra(Constants.KEY_MOBILE, phoneNumber)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity?.startActivity(intent)


    }
}