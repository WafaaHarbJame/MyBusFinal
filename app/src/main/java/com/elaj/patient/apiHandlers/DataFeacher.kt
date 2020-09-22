package com.elaj.patient.apiHandlers

import android.util.Log
import com.elaj.patient.Model.*
import com.elaj.patient.apiHandlers.ApiClient.client
import com.elaj.patient.apiHandlers.ApiClient.longClient
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.DBFunction
import com.elaj.patient.classes.UtilityApp
import com.elaj.patient.classes.UtilityApp.appVersion
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.net.NoRouteToHostException
import java.net.UnknownHostException


class DataFeacher {
    //    var activity: Activity?
    var dataFetcherCallBack: DataFetcherCallBack? = null
    private var apiService: ApiInterface? = null

    //    Realm realm;
//    var sharedPManger: SharedPManger?

    //    int city;
    val TAG: String? = "Log"
    var accessToken: String?
    var lang: String?
    var headerMap: MutableMap<String, Any?> = HashMap()

    constructor(
//        activity: Activity,
        callBack: DataFetcherCallBack?,
        longTime: Boolean
    ) {
//        this.activity = activity
        this.dataFetcherCallBack = callBack
        apiService = if (longTime) {
            longClient!!.create(ApiInterface::class.java)
        } else {
            client!!.create(ApiInterface::class.java)
        }
        //        realm = Realm.getDefaultInstance();
//        sharedPManger = SharedPManger(activity.baseContext)

        headerMap["Accept"] = "application/json,*/*"
//        headerMap["Auth-Role"] = "user"
//        accessToken = UtilityApp.userToken
        accessToken = UtilityApp.userToken
        if (accessToken != null) {
            headerMap["Authorization"] = accessToken
        }
        lang = UtilityApp.language
        if (lang != null) {
            headerMap["Accept-Language"] = lang
        }
    }

    constructor(/*activity: Activity,*/ callBack: DataFetcherCallBack?) {
//        this.activity = activity
        this.dataFetcherCallBack = callBack
        apiService = client!!.create(ApiInterface::class.java)
        //        realm = Realm.getDefaultInstance();
//        sharedPManger = SharedPManger(activity.baseContext)

        headerMap["Accept"] = "application/json,*/*"
        headerMap["Auth-Role"] = "user"
//        accessToken = UtilityApp.userToken
        accessToken = UtilityApp.userToken
        if (accessToken != null) {
            headerMap["Authorization"] = accessToken
        }
        lang = UtilityApp.language
        if (lang != null) {
            headerMap["Accept-Language"] = lang
        }
    }

    constructor(/*activity: Activity*/) {
//        this.activity = activity
        apiService = client!!.create(ApiInterface::class.java)
//        sharedPManger = SharedPManger(activity.baseContext)
        headerMap["Accept"] = "application/json,*/*"
        headerMap["Auth-Role"] = "user"
//        accessToken = UtilityApp.userToken
        accessToken = UtilityApp.userToken
        if (accessToken != null) {
            headerMap["Authorization"] = accessToken
        }
        lang = UtilityApp.language
        if (lang != null) {
            headerMap["Accept-Language"] = lang
        }
    }

    /*********************************** POST Fetcher  **********************************/
    fun loginHandle(memberModel: MemberModel?) {

        val params: MutableMap<String?, Any?> = HashMap()

        params["country_code"] = memberModel?.countryCode
        params["mobile"] = memberModel?.mobile
        params["password"] = memberModel?.password
        params["device_token"] = memberModel?.fcmToken

        Log.i(TAG, "Log loginHandle")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log country_code " + memberModel?.countryCode)
        Log.i(TAG, "Log mobile " + memberModel?.mobile)
        Log.i(TAG, "Log password " + memberModel?.password)
        Log.i(TAG, "Log device_token " + memberModel?.fcmToken)
        val call: Call<ResultAPIModel<MemberModel?>?>? =
            apiService!!.loginHandle(headerMap, params)

        call!!.enqueue(object : Callback<ResultAPIModel<MemberModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<MemberModel?>?>?,
                response: Response<ResultAPIModel<MemberModel?>?>?
            ) {
//                if (showLoading) GlobalData.progressDialog(
//                    activity,
//                    activity!!.resources.getString(R.string.please_wait_login),
//                    false
//                )
                val result: ResultAPIModel<MemberModel?>? = response!!.body()
                if (response.isSuccessful) {

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "loginHandle",
                                Constants.SUCCESS,
                                result
                            )
                        )

//                    EventBus.getDefault().post(ResponseEvent("resendConfirmRegister", Constants.SUCCESS, result))


                } else {
                    var errorModel: ResultAPIModel<Any>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    EventBus.getDefault()
                        .post(ResponseEvent("loginHandle", Constants.ERROR_DATA, errorModel))

//                    EventBus.getDefault().post(ResponseEvent(Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<MemberModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
//                if (showLoading) GlobalData.progressDialog(
//                    activity,
//                    activity!!.resources.getString(R.string.please_wait_login),
//                    false
//                )
                if (t is UnknownHostException || t is NoRouteToHostException) {
//                    NoConnectionDialog.with(activity)
                    EventBus.getDefault()
                        .post(ResponseEvent("loginHandle", Constants.NO_CONNECTION))
//                    EventBus.getDefault().post(ResponseEvent(Constants.NO_CONNECTION))
                } else {
                    EventBus.getDefault().post(ResponseEvent("loginHandle", Constants.FAIL_DATA))
//                    EventBus.getDefault().post(ResponseEvent(Constants.FAIL_DATA))
                }
            }
        })
    }

    fun registerHandle(memberModel: RegisterUserModel?) {

        val params: MutableMap<String?, Any?> = HashMap()
        params["full_name"] = memberModel?.full_name
        params["user_type"] = memberModel?.type
        params["email"] = memberModel?.email
        params["country_code"] = memberModel?.countryCode
        params["mobile"] = memberModel?.mobile
        if (memberModel?.countryId != 0)
            params["country_id"] = memberModel?.countryId
        params["password"] = memberModel?.password
//        params["confirm_password"] = memberModel?.password
        params["fcm_token"] = memberModel?.fcm_token

        Log.i(TAG, "Log registerHandle")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log full_name ${memberModel?.full_name}")
        Log.i(TAG, "Log user_type ${memberModel?.type}")
        Log.i(TAG, "Log email ${memberModel?.email}")
        Log.i(TAG, "Log country_code ${memberModel?.countryCode}")
        Log.i(TAG, "Log mobile ${memberModel?.mobile}")
        if (memberModel?.countryId != 0)
            Log.i(TAG, "Log country_id ${memberModel?.countryId}")
        Log.i(TAG, "Log password ${memberModel?.password}")
//        Log.i(TAG, "Log confirm_password " + memberModel?.password)
        Log.i(TAG, "Log fcm_token ${memberModel?.fcm_token}")

        val call: Call<ResultAPIModel<MemberModel?>?>? =
            apiService!!.registerHandle(headerMap, params)
        call!!.enqueue(object : Callback<ResultAPIModel<MemberModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<MemberModel?>?>?,
                response: Response<ResultAPIModel<MemberModel?>?>?
            ) {
                val result: ResultAPIModel<MemberModel?>? = response!!.body()
                if (response.isSuccessful) {

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "registerHandle",
                                Constants.SUCCESS,
                                result
                            )
                        )

//                    EventBus.getDefault().post(ResponseEvent("resendConfirmRegister", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<Any>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<Any?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    EventBus.getDefault()
                        .post(ResponseEvent("registerHandle", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<MemberModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
//                    NoConnectionDialog.with(activity)
                    EventBus.getDefault()
                        .post(ResponseEvent("registerHandle", Constants.NO_CONNECTION))
                } else {

                    EventBus.getDefault().post(ResponseEvent("registerHandle", Constants.FAIL_DATA))
                }
            }
        })
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

        val call: Call<ResultAPIModel<MemberModel>?>? =
            apiService!!.confirmRegister(headerMap, params)

        call!!.enqueue(object : Callback<ResultAPIModel<MemberModel>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<MemberModel>?>?,
                response: Response<ResultAPIModel<MemberModel>?>?
            ) {
                val result: ResultAPIModel<MemberModel>? = response!!.body()
                if (response.isSuccessful) {

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "confirmRegister",
                                Constants.SUCCESS,
                                result
                            )
                        )

                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("confirmRegister", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<MemberModel>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)
                    EventBus.getDefault()
                        .post(ResponseEvent("confirmRegister", Constants.NO_CONNECTION))
                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("confirmRegister", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun resendConfirmRegister(countryCode: Int, mobile: String) {

        val params: MutableMap<String?, Any?> = HashMap()

        params["country_code"] = countryCode
        params["mobile"] = mobile

        Log.i(TAG, "Log resendConfirmRegister")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log country_code $countryCode")
        Log.i(TAG, "Log mobile $mobile")

        val call: Call<ResultAPIModel<*>?>? =
            apiService!!.sendConfirmRegister(headerMap, params)
        call!!.enqueue(object : Callback<ResultAPIModel<*>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<*>?>?,
                response: Response<ResultAPIModel<*>?>?
            ) {
                val result: ResultAPIModel<*>? = response!!.body()
                if (response.isSuccessful) {
                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)
                    EventBus.getDefault()
                        .post(ResponseEvent("resendConfirmRegister", Constants.SUCCESS, result))

//                    EventBus.getDefault().post(ResponseEvent("resendConfirmRegister", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault().post(
                        ResponseEvent(
                            "resendConfirmRegister",
                            Constants.ERROR_DATA,
                            errorModel
                        )
                    )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<*>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)
                    EventBus.getDefault()
                        .post(ResponseEvent("resendConfirmRegister", Constants.NO_CONNECTION))
                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)
                    EventBus.getDefault()
                        .post(ResponseEvent("resendConfirmRegister", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun forgetPassword(countryCode: Int, mobile: String?) {

        val params: MutableMap<String?, Any?> = HashMap()

        params["country_code"] = countryCode
        params["mobile"] = mobile

        Log.i(TAG, "Log forgetPassword")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log country_code $countryCode")
        Log.i(TAG, "Log mobile $mobile")

        val call: Call<ResultAPIModel<*>?>? = apiService!!.forgetPassword(headerMap, params)
        call!!.enqueue(object : Callback<ResultAPIModel<*>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<*>?>?,
                response: Response<ResultAPIModel<*>?>?
            ) {
                val result: ResultAPIModel<*>? = response!!.body()
                if (response.isSuccessful) {

                    EventBus.getDefault()
                        .post(ResponseEvent("forgetPassword", Constants.SUCCESS, result))

                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    EventBus.getDefault()
                        .post(ResponseEvent("forgetPassword", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<*>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
//                    NoConnectionDialog.with(activity)
                    EventBus.getDefault()
                        .post(ResponseEvent("forgetPassword", Constants.NO_CONNECTION))
                } else {
                    EventBus.getDefault().post(ResponseEvent("forgetPassword", Constants.FAIL_DATA))
                }
            }
        })
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

        val call: Call<ResultAPIModel<*>?>? = apiService!!.resetPassword(headerMap, params)
        call!!.enqueue(object : Callback<ResultAPIModel<*>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<*>?>?,
                response: Response<ResultAPIModel<*>?>?
            ) {
                val result: ResultAPIModel<*>? = response!!.body()
                if (response.isSuccessful) {
                    EventBus.getDefault()
                        .post(ResponseEvent("resetPassword", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    EventBus.getDefault()
                        .post(ResponseEvent("forgetPassword", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<*>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
//                    NoConnectionDialog.with(activity)
                    EventBus.getDefault()
                        .post(ResponseEvent("forgetPassword", Constants.NO_CONNECTION))
                } else {
                    EventBus.getDefault().post(ResponseEvent("forgetPassword", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun sendChatNotification(chatId: Int, message: String) {

        val params: MutableMap<String?, Any?> = HashMap()

        params["chat_id"] = chatId
        params["message"] = message

        Log.i(TAG, "Log sendChatNotification")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log chatId $chatId")
        Log.i(TAG, "Log message $message")

        val call: Call<ResultAPIModel<Any>?>? = apiService!!.sendChatNotification(headerMap, params)
        call!!.enqueue(object : Callback<ResultAPIModel<Any>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any>?>?,
                response: Response<ResultAPIModel<Any>?>?
            ) {
                val result: ResultAPIModel<Any>? = response!!.body()
                if (response.isSuccessful) {

                    EventBus.getDefault()
                        .post(ResponseEvent("sendChatNotification", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "sendChatNotification",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
//                    NoConnectionDialog.with(activity)
                    EventBus.getDefault()
                        .post(ResponseEvent("sendChatNotification", Constants.NO_CONNECTION))
                } else {
                    EventBus.getDefault()
                        .post(ResponseEvent("sendChatNotification", Constants.FAIL_DATA))
                }
            }
        })
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

        val call: Call<ResultAPIModel<Any>?>? = apiService!!.sendSupport(headerMap, requestBody)
        call!!.enqueue(object : Callback<ResultAPIModel<Any>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any>?>?,
                response: Response<ResultAPIModel<Any>?>?
            ) {
                val result: ResultAPIModel<Any>? = response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(ResponseEvent("sendSupport", Constants.SUCCESS, result))

                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("sendSupport", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
//                    NoConnectionDialog.with(activity)

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("sendSupport", Constants.NO_CONNECTION))
                } else {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault().post(ResponseEvent("sendSupport", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun editProfile(
        fullName: String,
        mobile: String,
        email: String,
        cityId: Int,
        photo: File?
    ) {

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        builder.addFormDataPart("full_name", fullName)
        builder.addFormDataPart("mobile", mobile)
        builder.addFormDataPart("email", email)
        builder.addFormDataPart("city_id", cityId.toString())

        if (photo != null) {
            val requestImage: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), photo)
            builder.addFormDataPart(
                "avatar",
                photo.lastModified().toString() + ".png",
                requestImage
            )
        }

        val requestBody = builder.build()

        Log.i(TAG, "Log editProfile")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log full_name $fullName")
        Log.i(TAG, "Log mobile $mobile")
        Log.i(TAG, "Log email $email")
        Log.i(TAG, "Log cityId $cityId")

        val call: Call<ResultAPIModel<MemberModel?>?>? =
            apiService!!.editProfile(headerMap, requestBody)

        call!!.enqueue(object : Callback<ResultAPIModel<MemberModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<MemberModel?>?>?,
                response: Response<ResultAPIModel<MemberModel?>?>?
            ) {
                val result: ResultAPIModel<MemberModel?>? = response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(ResponseEvent("editProfile", Constants.SUCCESS, result))

                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("editProfile", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<MemberModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
//                    NoConnectionDialog.with(activity)

                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("editProfile", Constants.NO_CONNECTION))
                } else {

                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault().post(ResponseEvent("editProfile", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun changePassword(oldPass: String?, newPass: String?) {
        val params: MutableMap<String, Any?> =
            HashMap()
        params["old_password"] = oldPass
        params["password"] = newPass
        params["password_confirmation"] = newPass

        Log.i(TAG, "Log changePassword")
        Log.i(TAG, "Log AccessToken $accessToken")
        Log.i(TAG, "Log old_password $oldPass")
        Log.i(TAG, "Log password $newPass")
        Log.i(TAG, "Log password_confirmation $newPass")

        val call: Call<ResultAPIModel<Any?>>? =
            apiService!!.changePassword(headerMap, params)
        call!!.enqueue(object : Callback<ResultAPIModel<Any?>> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any?>>?,
                response: Response<ResultAPIModel<Any?>>?
            ) {
                val result: ResultAPIModel<Any?>? = response!!.body()
                if (response.isSuccessful) {

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(ResponseEvent("changePassword", Constants.SUCCESS, result))

                } else {
                    var errorModel: ResultAPIModel<Any>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("changePassword", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any?>>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)
                    EventBus.getDefault()
                        .post(ResponseEvent("changePassword", Constants.NO_CONNECTION))
                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)
                    EventBus.getDefault().post(ResponseEvent("changePassword", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun sendNewRequest(requestModel: SendNewRequestModel) {

        val params: MutableMap<String, Any?> = HashMap()

        params["salon_id"] = requestModel.providerId
//        if (requestModel.requestLocation == Constants.HOME) {
        params["lat"] = requestModel.lat
        params["lng"] = requestModel.lng
        params["address"] = requestModel.address
//        }
        params["scheduled_at"] = requestModel.scheduledAt
        params["payment_method_id"] = requestModel.paymentType
        params["services"] = requestModel.servicesIdList

        Log.i(TAG, "Log sendNewRequest")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log salon_id ${requestModel.providerId}")
        Log.i(TAG, "Log lat ${requestModel.lat}")
        Log.i(TAG, "Log lng ${requestModel.lng}")
        Log.i(TAG, "Log address ${requestModel.address}")
        Log.i(TAG, "Log scheduled_at ${requestModel.scheduledAt}")
        Log.i(TAG, "Log payment_method_id ${requestModel.paymentType}")
        Log.i(TAG, "Log services ${requestModel.servicesIdList}")


        val call: Call<ResultAPIModel<PayModel?>?>? =
            apiService!!.sendNewRequest(headerMap, params)

        call!!.enqueue(object : Callback<ResultAPIModel<PayModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<PayModel?>?>?,
                response: Response<ResultAPIModel<PayModel?>?>?
            ) {
                val result: ResultAPIModel<PayModel?>? = response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("sendNewRequest", Constants.SUCCESS, result))

                } else {
                    var errorModel: ResultAPIModel<Any?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<Any?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault().post(
                        ResponseEvent(
                            "sendNewRequest",
                            Constants.ERROR_DATA,
                            errorModel
                        )
                    )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<PayModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("sendNewRequest", Constants.NO_CONNECTION))
                } else {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("sendNewRequest", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun deleteGalleryPhoto(photoId: Int) {

        val params: MutableMap<String, Any?> = HashMap()

        params["photo_id"] = photoId

        Log.i(TAG, "Log deleteGalleryPhoto")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log photo_id $photoId")

        val call: Call<ResultAPIModel<Any?>?>? = apiService!!.deleteGalleryPhoto(headerMap, photoId)

        call!!.enqueue(object : Callback<ResultAPIModel<Any?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any?>?>?,
                response: Response<ResultAPIModel<Any?>?>?
            ) {
                val result: ResultAPIModel<Any?>? = response!!.body()
                if (response.isSuccessful) {

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("deleteGalleryPhoto", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("deleteGalleryPhoto", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("deleteGalleryPhoto", Constants.NO_CONNECTION))
                } else {

                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("deleteGalleryPhoto", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun uploadGalleryAvatarCoverPhoto(
        photo: File?,
        type: String
    ) {

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        builder.addFormDataPart("type", type)
        if (photo != null) {
            val requestImage: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), photo)
            builder.addFormDataPart(
                "photo",
                photo.lastModified().toString() + ".png",
                requestImage
            )
        }

        val requestBody = builder.build()

        Log.i(TAG, "Log uploadGalleryAvatarCoverPhoto")
        Log.i(TAG, "Log headerMap $headerMap")

        val call: Call<ResultAPIModel<GalleryModel>?>? =
            apiService!!.uploadGalleryPhoto(headerMap, requestBody)

        call!!.enqueue(object : Callback<ResultAPIModel<GalleryModel>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<GalleryModel>?>?,
                response: Response<ResultAPIModel<GalleryModel>?>?
            ) {
                val result: ResultAPIModel<GalleryModel>? = response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "uploadGalleryAvatarCoverPhoto",
                                Constants.SUCCESS,
                                result
                            )
                        )

                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "uploadGalleryAvatarCoverPhoto",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<GalleryModel>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
//                    NoConnectionDialog.with(activity)

                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "uploadGalleryAvatarCoverPhoto",
                                Constants.NO_CONNECTION
                            )
                        )
                } else {

                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("uploadGalleryAvatarCoverPhoto", Constants.FAIL_DATA))
                }
            }
        })
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
//        builder.addFormDataPart("photos_list", editProviderModel.photosListIds.toString())

//        if (editProviderModel.workHourList != null) {
//
//            for ((index, workHour) in editProviderModel.workHourList.withIndex()) {
//                builder.addFormDataPart(
//                    "shifts[$index][day_number]",
//                    workHour.dayNumber.toString()
//                )
//                builder.addFormDataPart("shifts[$index][first_seating]", workHour.fromTime)
//                builder.addFormDataPart("shifts[$index][last_seating]", workHour.toTime)
//                builder.addFormDataPart("shifts[$index][active]", if (workHour.status) "1" else "0")
//            }
//
//        }

//        if (editProviderModel.servicesList != null) {
//
//            for ((index, service) in editProviderModel.servicesList.withIndex()) {
//                builder.addFormDataPart("services[$index][id]", service.id.toString())
//                builder.addFormDataPart("services[$index][price]", service.servicePrice.toString())
//            }
//
//        }

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

        val call: Call<ResultAPIModel<Any?>?>? =
            apiService!!.editProviderProfile(headerMap, requestBody)

        call!!.enqueue(object : Callback<ResultAPIModel<Any?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any?>?>?,
                response: Response<ResultAPIModel<Any?>?>?
            ) {
                val result: ResultAPIModel<Any?>? = response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(ResponseEvent("editProviderProfile", Constants.SUCCESS, result))

                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "editProviderProfile",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
//                    NoConnectionDialog.with(activity)

                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("editProviderProfile", Constants.NO_CONNECTION))
                } else {

                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("editProviderProfile", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun changeWorkHoursAndServices(
        workingHoursList: MutableList<WorkingHoursModel>?,
        servicesList: MutableList<ServiceModel>?

    ) {

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        Log.i(TAG, "Log changeWorkHoursAndServices")
        Log.i(TAG, "Log headerMap $headerMap")

        if (workingHoursList != null)
            for ((index, workHour) in workingHoursList.withIndex()) {
                builder.addFormDataPart(
                    "shifts[$index][day_number]",
                    workHour.dayNumber.toString()
                )
                builder.addFormDataPart("shifts[$index][first_seating]", workHour.fromTime)
                builder.addFormDataPart("shifts[$index][last_seating]", workHour.toTime)
                builder.addFormDataPart("shifts[$index][active]", if (workHour.status) "1" else "0")

                Log.i(TAG, "Log work_hour $workHour")
            }

        if (servicesList != null) {

            for ((index, service) in servicesList.withIndex()) {
                builder.addFormDataPart("services[$index][service_id]", service.id.toString())
                builder.addFormDataPart("services[$index][price]", service.servicePrice.toString())

                Log.i(TAG, "Log service $service")
            }

        }

        val requestBody = builder.build()


        if (workingHoursList != null)
            Log.i(TAG, "Log work_hour ${workingHoursList.size}")
        if (servicesList != null)
            Log.i(TAG, "Log services ${servicesList.size}")

        val call: Call<ResultAPIModel<Any?>?>? = if (workingHoursList != null)
            apiService!!.updateProfileWorkHours(
                headerMap,
                requestBody
            ) else apiService!!.updateProfileServices(headerMap, requestBody)

        call!!.enqueue(object : Callback<ResultAPIModel<Any?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any?>?>?,
                response: Response<ResultAPIModel<Any?>?>?
            ) {
                val result: ResultAPIModel<Any?>? = response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "changeWorkHoursAndServices",
                                Constants.SUCCESS,
                                result
                            )
                        )

                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "changeWorkHoursAndServices",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
//                    NoConnectionDialog.with(activity)

                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("changeWorkHoursAndServices", Constants.NO_CONNECTION))
                } else {

                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("changeWorkHoursAndServices", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun setProviderFavOrUnFav(providerId: Int, isFav: Boolean) {

        val params: MutableMap<String, Any?> = HashMap()

        params["salon_id"] = providerId

        Log.i(TAG, "Log setProviderFavOrUnFav")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log salon_id $providerId")
        Log.i(TAG, "Log isFav $isFav")

        val call: Call<ResultAPIModel<Any?>?>? =
            if (isFav)
                apiService!!.addProviderToFavorite(headerMap, providerId)
            else
                apiService!!.deleteProviderFromFavorite(headerMap, providerId)

        call!!.enqueue(object : Callback<ResultAPIModel<Any?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any?>?>?,
                response: Response<ResultAPIModel<Any?>?>?
            ) {
                val result: ResultAPIModel<Any?>? = response!!.body()
                if (response.isSuccessful) {

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("setProviderFavOrUnFav", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "setProviderFavOrUnFav",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("setProviderFavOrUnFav", Constants.NO_CONNECTION))
                } else {

                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("setProviderFavOrUnFav", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun setRequestStatus(requestId: Int, status: Int) {

        val params: MutableMap<String, Any?> = HashMap()

        params["status"] = status

        Log.i(TAG, "Log setOrderStatus")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log requestId $requestId")
        Log.i(TAG, "Log status $status")

        val call: Call<ResultAPIModel<Any?>?>? =
            apiService!!.setRequestStatus(headerMap, requestId, params)

        call!!.enqueue(object : Callback<ResultAPIModel<Any?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any?>?>?,
                response: Response<ResultAPIModel<Any?>?>?
            ) {
                val result: ResultAPIModel<Any?>? = response!!.body()
                if (response.isSuccessful) {

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("setOrderStatus", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "setOrderStatus",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("setOrderStatus", Constants.NO_CONNECTION))
                } else {

                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("setOrderStatus", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun rateProvider(requestId: Int?, providerId: Int?, rate: Float, message: String) {

        val params: MutableMap<String, Any?> = HashMap()

        params["request_id"] = requestId
        params["provider_id"] = providerId
        params["rate"] = rate
        params["message"] = message

        Log.i(TAG, "Log rateProvider")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log requestId $requestId")
        Log.i(TAG, "Log provider_id $providerId")
        Log.i(TAG, "Log rate $rate")
        Log.i(TAG, "Log message $message")

        val call: Call<ResultAPIModel<Any?>?>? =
            apiService!!.reviewProvider(headerMap, params)

        call!!.enqueue(object : Callback<ResultAPIModel<Any?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any?>?>?,
                response: Response<ResultAPIModel<Any?>?>?
            ) {
                val result: ResultAPIModel<Any?>? = response!!.body()
                if (response.isSuccessful) {

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("rateProvider", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "rateProvider",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("rateProvider", Constants.NO_CONNECTION))
                } else {

                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("rateProvider", Constants.FAIL_DATA))
                }
            }
        })
    }

    /************************************  GET Feacher *******************************/

    fun logOut() {
        Log.i(TAG, "Log logOut")

        val call: Call<ResultAPIModel<Any>?>? = apiService!!.logOut(headerMap)

        call!!.enqueue(object : Callback<ResultAPIModel<Any>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any>?>?,
                response: Response<ResultAPIModel<Any>?>?
            ) {
                val result: ResultAPIModel<Any>? = response!!.body()

                if (response.isSuccessful) {

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault().post(ResponseEvent("logOut", Constants.SUCCESS, result))

                } else {
                    var errorModel: ResultAPIModel<Any?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<Any?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(result, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("logOut", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault().post(ResponseEvent("logOut", Constants.NO_CONNECTION))
                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault().post(ResponseEvent("logOut", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getConfig() {
        Log.i(TAG, "Log getConfig")

        val call: Call<ResultAPIModel<ConfigModel?>>? =
            apiService!!.getConfig(headerMap)
        call!!.enqueue(object :
            Callback<ResultAPIModel<ConfigModel?>> {
            override fun onResponse(
                call: Call<ResultAPIModel<ConfigModel?>>?,
                response: Response<ResultAPIModel<ConfigModel?>>?
            ) {
                val result: ResultAPIModel<ConfigModel?>? = response?.body()
                if (response?.isSuccessful!!) {
                    val configModel = result!!.data

                    val citiesJson = Gson().toJson(configModel?.countries)
                    DBFunction.setCountries(citiesJson)

                    if (configModel?.countries != null && configModel.countries?.size!! > 0) {
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)
                    } else {
                        dataFetcherCallBack?.Result(result, Constants.FAIL_DATA, true)
                    }

                    configModel?.countries = null
                    val configJson = Gson().toJson(configModel)
                    DBFunction.setSettings(configJson)

                } else {
                    var errorModel: ResultAPIModel<ErrorModel?>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getConfig", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<ConfigModel?>>,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)
                    EventBus.getDefault().post(ResponseEvent("getConfig", Constants.NO_CONNECTION))
                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)
                    EventBus.getDefault().post(ResponseEvent("getConfig", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getCategories() {
        Log.i(TAG, "Log getCategories")
        val call: Call<ResultAPIModel<MutableList<ServiceModel?>?>?>? =
            apiService!!.getCategories(headerMap)

        call!!.enqueue(object :
            Callback<ResultAPIModel<MutableList<ServiceModel?>?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<MutableList<ServiceModel?>?>?>?,
                response: Response<ResultAPIModel<MutableList<ServiceModel?>?>?>?
            ) {
                val result: ResultAPIModel<MutableList<ServiceModel?>?>? =
                    response!!.body()
                if (response.isSuccessful) {

                    val json = Gson().toJson(result!!.data)
                    DBFunction.setCategories(json)

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(ResponseEvent("getCategories", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(result, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getCategories", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<MutableList<ServiceModel?>?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getCategories", Constants.NO_CONNECTION))
                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault().post(ResponseEvent("getCategories", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getSubCategories(catId: Int) {

        Log.i(TAG, "Log getSubCategories")
        Log.i(TAG, "Log catId $catId")

        val call: Call<ResultAPIModel<MutableList<ServiceModel?>?>?>? =
            apiService!!.getSubCategories(headerMap, catId)

        call!!.enqueue(object :
            Callback<ResultAPIModel<MutableList<ServiceModel?>?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<MutableList<ServiceModel?>?>?>?,
                response: Response<ResultAPIModel<MutableList<ServiceModel?>?>?>?
            ) {
                val result: ResultAPIModel<MutableList<ServiceModel?>?>? =
                    response!!.body()
                if (response.isSuccessful) {

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(ResponseEvent("getSubCategories", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(result, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getSubCategories", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<MutableList<ServiceModel?>?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getSubCategories", Constants.NO_CONNECTION))
                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getSubCategories", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getCountryCities(countryId: Int) {

        Log.i(TAG, "Log getCountryCities")
        Log.i(TAG, "Log countryId $countryId")

        val call: Call<ResultAPIModel<MutableList<CityModel>?>?>? =
            apiService!!.getCountryCities(headerMap, countryId)

        call!!.enqueue(object :
            Callback<ResultAPIModel<MutableList<CityModel>?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<MutableList<CityModel>?>?>?,
                response: Response<ResultAPIModel<MutableList<CityModel>?>?>?
            ) {
                val result: ResultAPIModel<MutableList<CityModel>?>? =
                    response!!.body()
                if (response.isSuccessful) {

//                    val json = Gson().toJson(result!!.data)
//                    DBFunction.setCountries(json)

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(ResponseEvent("getCountryCities", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(result, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getCountryCities", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<MutableList<CityModel>?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getCountryCities", Constants.NO_CONNECTION))
                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getCountryCities", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun searchServiceProviders(searchProvidersModel: SearchProvidersModel, page: Int) {

        val params: MutableMap<String, Any?> = HashMap()

        if (searchProvidersModel.cityId != 0)
            params["city_id"] = searchProvidersModel.cityId
        if (searchProvidersModel.serviceId != 0)
            params["service_id"] = searchProvidersModel.serviceId
        if (searchProvidersModel.minPrice != 0)
            params["price_min"] = searchProvidersModel.minPrice
        if (searchProvidersModel.maxPrice != 0)
            params["price_max"] = searchProvidersModel.maxPrice
        if (searchProvidersModel.lat != 0.0)
            params["lat"] = searchProvidersModel.lat
        if (searchProvidersModel.lng != 0.0)
            params["lng"] = searchProvidersModel.lng

        Log.i(TAG, "Log searchServiceProviders")
        if (searchProvidersModel.cityId != 0)
            Log.i(TAG, "Log city_id ${searchProvidersModel.cityId}")
        if (searchProvidersModel.serviceId != 0)
            Log.i(TAG, "Log serviceId ${searchProvidersModel.serviceId}")
        if (searchProvidersModel.minPrice != 0)
            Log.i(TAG, "Log minPrice ${searchProvidersModel.minPrice}")
        if (searchProvidersModel.maxPrice != 0)
            Log.i(TAG, "Log maxPrice ${searchProvidersModel.maxPrice}")
        if (searchProvidersModel.lat != 0.0)
            Log.i(TAG, "Log lat ${searchProvidersModel.lat}")
        if (searchProvidersModel.lng != 0.0)
            Log.i(TAG, "Log lng ${searchProvidersModel.lng}")


        val call: Call<ResultAPIModel<AllProvidersModel?>>? =
            apiService!!.searchServiceProviders(headerMap, params);

        call!!.enqueue(object :
            Callback<ResultAPIModel<AllProvidersModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<AllProvidersModel?>?>?,
                response: Response<ResultAPIModel<AllProvidersModel?>?>?
            ) {
                val result: ResultAPIModel<AllProvidersModel?>? =
                    response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("searchServiceProviders", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)
                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "getServiceProviders",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<AllProvidersModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("searchServiceProviders", Constants.NO_CONNECTION))
                } else {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("searchServiceProviders", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getFavoriteProviders(page: Int) {
        Log.i(TAG, "Log getFavoriteProviders")
        val call: Call<ResultAPIModel<AllProvidersModel?>>? =
            apiService!!.getFavoriteProviders(headerMap, page)

        call!!.enqueue(object :
            Callback<ResultAPIModel<AllProvidersModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<AllProvidersModel?>?>?,
                response: Response<ResultAPIModel<AllProvidersModel?>?>?
            ) {
                val result: ResultAPIModel<AllProvidersModel?>? =
                    response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getFavoriteProviders", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)
                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "getFavoriteProviders",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<AllProvidersModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getFavoriteProviders", Constants.NO_CONNECTION))
                } else {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getFavoriteProviders", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getChats(page: Int) {
        Log.i(TAG, "Log getChats")
        val call: Call<ResultAPIModel<AllChatsModel?>>? =
            apiService!!.getChats(headerMap, page)

        call!!.enqueue(object :
            Callback<ResultAPIModel<AllChatsModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<AllChatsModel?>?>?,
                response: Response<ResultAPIModel<AllChatsModel?>?>?
            ) {
                val result: ResultAPIModel<AllChatsModel?>? =
                    response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getCustomerChats", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)
                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "getCustomerChats",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<AllChatsModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getCustomerChats", Constants.NO_CONNECTION))
                } else {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getCustomerChats", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getProviderDetails(providerId: Int) {

        Log.i(TAG, "Log getProviderDetails")
        Log.i(TAG, "Log providerId $providerId")

        val call: Call<ResultAPIModel<ProviderModel?>>? =
            apiService!!.getProviderDetails(headerMap, providerId)

        call!!.enqueue(object :
            Callback<ResultAPIModel<ProviderModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<ProviderModel?>?>?,
                response: Response<ResultAPIModel<ProviderModel?>?>?
            ) {
                val result: ResultAPIModel<ProviderModel?>? =
                    response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getProviderDetails", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)
                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "getProviderDetails",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<ProviderModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getProviderDetails", Constants.NO_CONNECTION))
                } else {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getProviderDetails", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getProviderReviews(providerId: Int, page: Int) {

        Log.i(TAG, "Log getProviderReviews")
        Log.i(TAG, "Log providerId $providerId")
        Log.i(TAG, "Log page $page")

        val call: Call<ResultAPIModel<AllReviewsModel?>>? =
            apiService!!.getProviderReviews(headerMap, providerId, page)

        call!!.enqueue(object :
            Callback<ResultAPIModel<AllReviewsModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<AllReviewsModel?>?>?,
                response: Response<ResultAPIModel<AllReviewsModel?>?>?
            ) {
                val result: ResultAPIModel<AllReviewsModel?>? =
                    response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getProviderReviews", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)
                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "getProviderReviews",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<AllReviewsModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getProviderReviews", Constants.NO_CONNECTION))
                } else {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getProviderReviews", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getRequestDetails(requestId: Int) {

        Log.i(TAG, "Log getRequestDetails")
        Log.i(TAG, "Log requestId $requestId")

        val call: Call<ResultAPIModel<RequestModel?>>? =
            apiService!!.getRequestDetails(headerMap, requestId)

        call!!.enqueue(object :
            Callback<ResultAPIModel<RequestModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<RequestModel?>?>?,
                response: Response<ResultAPIModel<RequestModel?>?>?
            ) {
                val result: ResultAPIModel<RequestModel?>? =
                    response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getRequestDetails", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)
                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "getRequestDetails",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<RequestModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getRequestDetails", Constants.NO_CONNECTION))
                } else {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getRequestDetails", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getRequests(type: String, page: Int) {

        val params: MutableMap<String, Any?> = HashMap()

        params["status"] = type
        params["page"] = page

        Log.i(TAG, "Log getRequests")
        Log.i(TAG, "Log headerMap $headerMap")
        Log.i(TAG, "Log status $type")
        Log.i(TAG, "Log page $page")

        val call: Call<ResultAPIModel<AllRequestsModel?>>? =
            apiService!!.getRequests(headerMap, params)

        call!!.enqueue(object :
            Callback<ResultAPIModel<AllRequestsModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<AllRequestsModel?>?>?,
                response: Response<ResultAPIModel<AllRequestsModel?>?>?
            ) {
                val result: ResultAPIModel<AllRequestsModel?>? =
                    response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getRequests", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)
                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "getRequests",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<AllRequestsModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getRequests", Constants.NO_CONNECTION))
                } else {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getRequests", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getWalletDetails() {

        Log.i(TAG, "Log getWalletDetails")

        val call: Call<ResultAPIModel<WalletModel?>>? =
            apiService!!.getWalletDetails(headerMap)

        call!!.enqueue(object :
            Callback<ResultAPIModel<WalletModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<WalletModel?>?>?,
                response: Response<ResultAPIModel<WalletModel?>?>?
            ) {
                val result: ResultAPIModel<WalletModel?>? =
                    response!!.body()
                if (response.isSuccessful) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(result, Constants.SUCCESS, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getWalletDetails", Constants.SUCCESS, result))
                } else {
                    var errorModel: ResultAPIModel<*>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<*>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)
                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "getWalletDetails",
                                Constants.ERROR_DATA,
                                errorModel
                            )
                        )

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<WalletModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getWalletDetails", Constants.NO_CONNECTION))
                } else {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getWalletDetails", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun withdrawBalance() {
//        val params: MutableMap<String, Any?> =
//            HashMap()
//        params["old_password"] = oldPass
//        params["password"] = newPass
//        params["password_confirmation"] = newPass

        Log.i(TAG, "Log withdrawBalance")
        Log.i(TAG, "Log AccessToken $accessToken")

        val call: Call<ResultAPIModel<Any?>>? =
            apiService!!.withdrawBalance(headerMap)

        call!!.enqueue(object : Callback<ResultAPIModel<Any?>> {
            override fun onResponse(
                call: Call<ResultAPIModel<Any?>>?,
                response: Response<ResultAPIModel<Any?>>?
            ) {
                val result: ResultAPIModel<Any?>? = response!!.body()
                if (response.isSuccessful) {

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(ResponseEvent("withdrawBalance", Constants.SUCCESS, result))

                } else {
                    var errorModel: ResultAPIModel<Any>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<ErrorModel?>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    dataFetcherCallBack?.Result(errorModel, Constants.ERROR_DATA, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("withdrawBalance", Constants.ERROR_DATA, errorModel))

                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<Any?>>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)
                    EventBus.getDefault()
                        .post(ResponseEvent("withdrawBalance", Constants.NO_CONNECTION))
                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)
                    EventBus.getDefault()
                        .post(ResponseEvent("withdrawBalance", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun getMyProfile() {
        Log.i(TAG, "Log getMyProfile")
        Log.i(TAG, "Log headerMap $headerMap")

        val call: Call<ResultAPIModel<MemberModel?>?>? =
            apiService!!.getMyProfile(headerMap)

        call!!.enqueue(object : Callback<ResultAPIModel<MemberModel?>?> {
            override fun onResponse(
                call: Call<ResultAPIModel<MemberModel?>?>?,
                response: Response<ResultAPIModel<MemberModel?>?>?
            ) {
                val result: ResultAPIModel<MemberModel?>? = response!!.body()
                if (response.isSuccessful) {

                    dataFetcherCallBack?.Result(result, Constants.SUCCESS, true)

                    EventBus.getDefault()
                        .post(
                            ResponseEvent(
                                "getMyProfile",
                                Constants.SUCCESS,
                                result
                            )
                        )

                } else {
                    var errorModel: ResultAPIModel<Any>? = null
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        errorModel = Gson().fromJson(
                            error,
                            object : TypeToken<ResultAPIModel<Any>?>() {}.type
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (errorModel?.error?.name == Constants.UNAUTHENTICATED) {
                        dataFetcherCallBack?.Result(result, Constants.UNAUTHENTICATED, false)

                        EventBus.getDefault()
                            .post(
                                ResponseEvent(
                                    "getMyProfile",
                                    Constants.UNAUTHENTICATED,
                                    errorModel
                                )
                            )
                    } else {
                        dataFetcherCallBack?.Result(result, Constants.ERROR_DATA, false)

                        EventBus.getDefault()
                            .post(ResponseEvent("getMyProfile", Constants.ERROR_DATA, errorModel))
                    }


                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<MemberModel?>?>?,
                t: Throwable?
            ) {
                t!!.printStackTrace()
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    dataFetcherCallBack?.Result(null, Constants.NO_CONNECTION, false)

                    EventBus.getDefault()
                        .post(ResponseEvent("getMyProfile", Constants.NO_CONNECTION))
                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                    EventBus.getDefault().post(ResponseEvent("getMyProfile", Constants.FAIL_DATA))
                }
            }
        })
    }

    fun checkAppVersion() {

        val params: MutableMap<String, Any> = HashMap()
        params["X-Version"] = appVersion
        params["X-Platform"] = "android"

        Log.i(TAG, "Log checkAppVersion")
        Log.i(TAG, "Log X-Version $appVersion")
        Log.i(TAG, "Log X-Platform " + "android")

        val call: Call<ResultAPIModel<AppVersionModel?>>? =
            apiService!!.checkAppVersion(headerMap)
        call?.enqueue(object : Callback<ResultAPIModel<AppVersionModel?>> {
            override fun onResponse(
                call: Call<ResultAPIModel<AppVersionModel?>>,
                response: Response<ResultAPIModel<AppVersionModel?>>
            ) {
                val result: ResultAPIModel<AppVersionModel?>? = response.body()
                if (response.isSuccessful) {

                    EventBus.getDefault()
                        .post(ResponseEvent("checkAppVersion", Constants.SUCCESS, result))

                } else {
                    try {
                        val error = response.errorBody()!!.string()
                        Log.e("Log", "Log error $error")
                        EventBus.getDefault()
                            .post(ResponseEvent("checkAppVersion", Constants.ERROR_DATA))
//                        dataFetcherCallBack!!.Result(null, Constants.ERROR_DATA, false)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(
                call: Call<ResultAPIModel<AppVersionModel?>>?,
                t: Throwable
            ) {
                t.printStackTrace()
                //                GlobalData.progressDialog(activity, activity.getResources().getString(R.string.please_wait_login), false);
                if (t is UnknownHostException || t is NoRouteToHostException) {
                    EventBus.getDefault()
                        .post(ResponseEvent("checkAppVersion", Constants.NO_CONNECTION))
                } else {
                    EventBus.getDefault()
                        .post(ResponseEvent("checkAppVersion", Constants.FAIL_DATA))
                }
            }
        })
    }


    //    private void setDB(int type, String data) {
//        DBModel dbModel = new DBModel();
//        dbModel.type = type;
//        dbModel.dataModel = data;
//        realm.beginTransaction();
//        realm.copyToRealmOrUpdate(dbModel);
//        realm.commitTransaction();
//    }
    private fun printLog(o: Any?) {
        Log.v("Log", "Log " + o.toString())
    }
}