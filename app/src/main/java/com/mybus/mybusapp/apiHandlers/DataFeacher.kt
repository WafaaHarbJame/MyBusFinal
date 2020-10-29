package com.mybus.mybusapp.apiHandlers

import android.util.Log
import com.google.gson.Gson
import com.mybus.mybusapp.RootApplication
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.DBFunction
import com.mybus.mybusapp.models.*


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
                    if (oldPassword == password) {
                        RootApplication.fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile)
                            ?.update("password", newPassword, "password_confirm", newPassword)
                            ?.addOnSuccessListener {

                                dataFetcherCallBack?.Result("", Constants.SUCCESS, true)

                            }?.addOnFailureListener { e ->
                                dataFetcherCallBack?.Result("", Constants.FAIL_DATA, true)
                            }
                    } else {
                        dataFetcherCallBack?.Result("", Constants.PASSWORD_WRONG, true)

                    }

                } else {
                    dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, false)

                }

            }


    }


    fun updateData(
        mobile: String?,
        lat: Double,
        lng: Double,
        address: String,
        isSelectLocation: Boolean
    ) {
        fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile!!)?.get()
            ?.addOnSuccessListener { document ->
                if (document.exists()) {
                    RootApplication.fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile)
                        ?.update(
                            "lat",
                            lat,
                            "lng",
                            lng,
                            "address",
                            address,
                            "isSelectLocation",
                            isSelectLocation
                        )
                        ?.addOnSuccessListener {
                            dataFetcherCallBack?.Result("", Constants.SUCCESS, true)

                        }?.addOnFailureListener { e ->
                            dataFetcherCallBack?.Result("", Constants.FAIL_DATA, true)
                        }
                } else {
                    dataFetcherCallBack?.Result("", Constants.FAIL_DATA, true)

                }
            }
    }


    fun updateOrder(orderNumber: String?, orderStatus: Int?) {

        Log.i(TAG, "Log updateOrder")
        Log.i(TAG, "Log updateOrder  $orderNumber")
        Log.i(TAG, "Log updateOrder $orderStatus")

        fireStoreDB?.collection(ApiUrl.Orders.name)?.document(orderNumber!!)?.get()
            ?.addOnSuccessListener { document ->
                if (document.exists()) {
                    Log.i(TAG, "Log updateOrder exists")
                    RootApplication.fireStoreDB?.collection(ApiUrl.Orders.name)?.document(
                        orderNumber
                    )
                        ?.update(
                            "requestStatus", orderStatus
                        )
                        ?.addOnSuccessListener {
                            dataFetcherCallBack?.Result("", Constants.SUCCESS, true)

                        }?.addOnFailureListener { e ->
                            dataFetcherCallBack?.Result("", Constants.FAIL_DATA, true)
                        }
                } else {
                    dataFetcherCallBack?.Result("", Constants.FAIL_DATA, true)

                }
            }
    }

    fun updateStatus(mobile: String?, isDriverActive: Boolean) {

        fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile!!)?.get()
            ?.addOnSuccessListener { document ->
                if (document.exists()) {
                    RootApplication.fireStoreDB?.collection(ApiUrl.Users.name)?.document(mobile)
                        ?.update("isDriverActive", isDriverActive)
                        ?.addOnSuccessListener {
                            dataFetcherCallBack?.Result("", Constants.SUCCESS, true)

                        }?.addOnFailureListener { e ->
                            dataFetcherCallBack?.Result("", Constants.FAIL_DATA, true)
                        }
                } else {
                    dataFetcherCallBack?.Result("", Constants.FAIL_DATA, true)

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


    }

    fun getFinishedRequests(driver_id: String?) {
        Log.i(TAG, "Log getFinishedRequests")

        fireStoreDB?.collection(ApiUrl.Orders.name)?.whereEqualTo("requestStatus", 3)
            ?.whereEqualTo("driver_id", driver_id)
            ?.get()
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    val query = it.result

                    val requestList = mutableListOf<RequestModel>()
                    for (document in query!!) {
                        val requestModel = document?.toObject(RequestModel::class.java)
                        requestList.add(requestModel!!)
                    }

                    val json = Gson().toJson(requestList)
                    dataFetcherCallBack?.Result(requestList, Constants.SUCCESS, true)
                } else {
                    it.exception?.printStackTrace()
                }

            }

    }

    fun getFinishedClientRequests(clientId: String?) {
        Log.i(TAG, "Log getFinishedRequests")

        fireStoreDB?.collection(ApiUrl.Orders.name)?.whereEqualTo("requestStatus", 3)
            ?.whereEqualTo("clientId", clientId)
            ?.get()
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    val query = it.result

                    val requestList = mutableListOf<RequestModel>()
                    for (document in query!!) {
                        val requestModel = document?.toObject(RequestModel::class.java)
                        requestList.add(requestModel!!)
                    }

                    val json = Gson().toJson(requestList)
                    dataFetcherCallBack?.Result(requestList, Constants.SUCCESS, true)
                } else {
                    it.exception?.printStackTrace()
                }

            }

    }


    fun getCurrentRequests(driver_id: String?) {
        Log.i(TAG, "Log getCurrentRequests")

        fireStoreDB?.collection(ApiUrl.Orders.name)?.whereEqualTo("requestStatus", 0)
            ?.whereEqualTo("driver_id", driver_id)
            ?.get()
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    val query = it.result

                    val requestList = mutableListOf<RequestModel>()
                    for (document in query!!) {
                        val requestModel = document?.toObject(RequestModel::class.java)
                        requestList.add(requestModel!!)
                    }

                    val json = Gson().toJson(requestList)
                    dataFetcherCallBack?.Result(requestList, Constants.SUCCESS, true)
                } else {
                    it.exception?.printStackTrace()
                }

            }

    }

    fun getAllRequests(driver_id: String?) {
        Log.i(TAG, "Log getAllRequests")
        Log.i(TAG, "Log updateOrder  $driver_id")

        fireStoreDB?.collection(ApiUrl.Orders.name)?.whereEqualTo("driver_id", driver_id)
            ?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                val query = it.result

                val requestList = mutableListOf<RequestModel>()
                for (document in query!!) {
                    val requestModel = document?.toObject(RequestModel::class.java)
                    requestList.add(requestModel!!)
                }

                val json = Gson().toJson(requestList)
                dataFetcherCallBack?.Result(requestList, Constants.SUCCESS, true)
            } else {
                it.exception?.printStackTrace()
            }

        }

    }

    fun getAllClientRequests(clientId: String?) {
        Log.i(TAG, "Log getAllRequests")
        Log.i(TAG, "Log updateOrder  $clientId")

        fireStoreDB?.collection(ApiUrl.Orders.name)?.whereEqualTo("clientId", clientId)
            ?.get()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    val query = it.result

                    val requestList = mutableListOf<RequestModel>()
                    for (document in query!!) {
                        val requestModel = document?.toObject(RequestModel::class.java)
                        requestList.add(requestModel!!)
                    }

                    val json = Gson().toJson(requestList)
                    dataFetcherCallBack?.Result(requestList, Constants.SUCCESS, true)
                } else {
                    it.exception?.printStackTrace()
                }

            }

    }

    fun getAllDrivers() {
        Log.i(TAG, "Log getAllDrivers")
        fireStoreDB?.collection(ApiUrl.Users.name)?.whereEqualTo("type", 2)
            ?.whereEqualTo("isDriverActive", true)
            ?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                val query = it.result

                val allDriversList = mutableListOf<AllDriversModel>()
                for (document in query!!) {
                    val allDriversModel = document?.toObject(AllDriversModel::class.java)
                    allDriversList.add(allDriversModel!!)
                }

                dataFetcherCallBack?.Result(allDriversList, Constants.SUCCESS, true)
            } else {
                it.exception?.printStackTrace()
            }

        }

    }

    fun orderHandler(requestModel: RequestModel) {
        Log.i(TAG, "Log orderHandler")
        Log.i(TAG, "Log clientId ${requestModel.clientId}")

        val orderID: String = fireStoreDB!!.collection(ApiUrl.Orders.name).document().id
        requestModel.orderId=orderID
        fireStoreDB!!.collection(ApiUrl.Orders.name).document(orderID)
            .set(requestModel)
            .addOnSuccessListener {
                dataFetcherCallBack?.Result(requestModel, Constants.SUCCESS, true)
            }.addOnFailureListener {
                dataFetcherCallBack?.Result(null, Constants.FAIL_DATA, true)
            }


    }



}