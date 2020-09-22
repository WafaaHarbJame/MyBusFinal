package com.elaj.patient.classes

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.content.pm.PackageInfoCompat
import com.elaj.patient.Model.MemberModel
import com.elaj.patient.RootApplication
import com.google.gson.Gson


object UtilityApp {
    val unique: String
        get() {
            val android_id: String = Settings.Secure.getString(
                RootApplication.instance!!.getContentResolver(),
                Settings.Secure.ANDROID_ID
            )
            return android_id
        }

    val appVersion: Any
        get() {
            var pinfo: PackageInfo? = null
            try {
                pinfo = RootApplication.instance!!.packageManager.getPackageInfo(
                    RootApplication.instance!!.getPackageName(),
                    0
                )
                val versionNumber = PackageInfoCompat.getLongVersionCode(pinfo)
                //            Log.i("Utility", "Log versionNumber " + versionNumber);
                return versionNumber
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            //        int versionCode = BuildConfig.VERSION_CODE;
            return 0
        }

    val appVersionStr: String
        get() {
            var pinfo: PackageInfo? = null
            try {
                pinfo = RootApplication.instance!!.packageManager.getPackageInfo(
                    RootApplication.instance!!.getPackageName(),
                    0
                )
                val versionName: String = pinfo.versionName
                //            Log.i("Utility", "Log versionNumber " + versionNumber);
                return versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            //        int versionCode = BuildConfig.VERSION_CODE;
            return ""
        }

    // getPackageName() from Context or Activity object
    val appMarketUrl: String
        get() {
            val appPackageName: String =
                RootApplication.instance!!.getPackageName() // getPackageName() from Context or Activity object
            return "https://play.google.com/store/apps/details?id=$appPackageName"
        }

    var isRateApp: Boolean
        get() = RootApplication.instance!!.sharedPManger!!.getDataBool(Constants.KEY_IS_RATE_APP)
        set(isRate) {
            RootApplication.instance!!.sharedPManger!!.SetData(Constants.KEY_IS_RATE_APP, isRate)
        }

    var isFirstLogin: Boolean
        get() {
            return RootApplication.instance!!.sharedPManger!!.getDataBool(
                Constants.KEY_FIRST_RUN,
                true
            )
        }
        set(isFirstLogin) {
            RootApplication.instance!!.sharedPManger!!.SetData(
                Constants.KEY_FIRST_RUN,
                isFirstLogin
            )
        }

    val isLogin: Boolean
        get() {
            val userToken: String? =
                RootApplication.instance!!.sharedPManger!!.getDataString(Constants.KEY_MEMBER, null)
            return userToken != null
        }

    fun logOut() {
        if (isLogin) {
            //            MemberModel user = getUserData();
//            FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.NOTIFICATION_TOPIC + user.getId());
//            FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.NOTIFICATION_USER_TOPIC + user.getId());
//            FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.CHAT_TOPIC + user.getId());
        }
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.KEY_MEMBER, null)
    }

    var fCMToken: String?
        get() {
            return RootApplication.instance!!.sharedPManger!!.getDataString(Constants.KEY_FIREBASE_TOKEN)
        }
        set(fcmToken) {
            RootApplication.instance!!.sharedPManger!!.SetData(
                Constants.KEY_FIREBASE_TOKEN,
                fcmToken
            )
        }

    var language: String?
        get() {
            return RootApplication.instance!!.sharedPManger!!.getDataString(
                Constants.KEY_MEMBER_LANGUAGE
            )
        }
        set(language) {
            RootApplication.instance!!.sharedPManger!!.SetData(
                Constants.KEY_MEMBER_LANGUAGE,
                language
            )
        }

    var userData: MemberModel?
        get() {
            val userJsonData: String? =
                RootApplication.instance!!.sharedPManger!!.getDataString(Constants.KEY_MEMBER)
            return if (userJsonData != null) Gson().fromJson(
                userJsonData,
                MemberModel::class.java
            ) else null
        }
        set(user) {
            val userData: String = Gson().toJson(user)
            RootApplication.instance!!.sharedPManger!!.SetData(Constants.KEY_MEMBER, userData)
        }

    fun setToShPref(key: String?, data: String?) {
        RootApplication.instance?.sharedPManger?.SetData(key, data)
    }

    fun getFromShPref(key: String?): String? {
        return RootApplication.instance?.sharedPManger?.getDataString(key)
    }

    fun isCustomer(): Boolean {
        val user = userData
        return user?.type == 1
    }

//    var isCustomer: Boolean = false
//        get() {
//            return RootApplication.instance!!.sharedPManger!!.getDataBool(Constants.KEY_IS_CUSTOMER)
//        }
//        set(user) {
//            RootApplication.instance!!.sharedPManger!!.SetData(
//                Constants.KEY_IS_CUSTOMER,
//                isCustomer
//            )
//        }

    val userToken: String?
        get() {
            val memberModel: MemberModel? = userData
            if (memberModel != null) {
                return Constants.TOKEN_PREFIX + memberModel.apiToken
            }
            return null
        }

    //    public static void startLoginUserActivity(Context context) {
//        User user = UtilityBakkalti.getUserData();
//        logCrashAnaliseUser(user.getUsername(), user.getApiToken());
//        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        switch (user.getUserType()) {
//            case Constants.OWNER:
//                intent.setClass(context, OwnerActivity.class);
//                break;
//            case Constants.BUYER:
//                intent.setClass(context, OrderTypeActivity.class);
//                break;
//            case Constants.SELLER:
//                intent.setClass(context, SellerDashboardActivity.class);
//        }
//        context.startActivity(intent);
//    }
}