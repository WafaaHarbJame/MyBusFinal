package com.elaj.patient.classes

import com.elaj.patient.models.*
import com.elaj.patient.RootApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DBFunction {
//    static Realm realm = Realm.getDefaultInstance();

    fun getCategories(): MutableList<CategoryModel>? {
        val json: String? =
            RootApplication.instance!!.sharedPManger!!.getDataString(Constants.DB_Categories)
        return Gson().fromJson(
            json,
            object : TypeToken<MutableList<CategoryModel>?>() {}.type
        )
    }

    fun setCategories(json: String?) {
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.DB_Categories, json)
    }

    fun getCountries(): MutableList<CountryModel>? {
        val json: String? =
            RootApplication.instance!!.sharedPManger!!.getDataString(Constants.DB_Countries)
        return Gson().fromJson(
            json,
            object : TypeToken<MutableList<CountryModel>?>() {}.type
        )
    }

    fun setCountries(json: String?) {
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.DB_Countries, json)
    }

    fun getSliders(): MutableList<SliderModel>? {
        val json: String? =
            RootApplication.instance!!.sharedPManger!!.getDataString(Constants.DB_Sliders)
        return Gson().fromJson(
            json,
            object : TypeToken<MutableList<SliderModel>?>() {}.type
        )
    }


    fun setSliders(json: String?) {
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.DB_Sliders, json)
    }

    fun getPlans(): MutableList<PlansModel>? {
        val json: String? =
            RootApplication.instance!!.sharedPManger!!.getDataString(Constants.DB_Plans)
        return Gson().fromJson(
            json,
            object : TypeToken<MutableList<PlansModel>?>() {}.type
        )
    }


    fun setPlans(json: String?) {
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.DB_Plans, json)
    }

//    fun getCities(): MutableList<CityModel>? {
//        val json: String? =
//            RootApplication.instance!!.sharedPManger!!.getDataString(Constants.KEY_CITIES)
//        return Gson().fromJson(
//            json,
//            object : TypeToken<MutableList<CityModel>?>() {}.type
//        )
//    }
//
//    fun setCities(json: String?) {
//        RootApplication.instance!!.sharedPManger!!.SetData(Constants.KEY_CITIES, json)
//    }

    fun getSettings(): SettingsModel? {
        val json: String? =
            RootApplication.instance!!.sharedPManger!!.getDataString(Constants.DB_Settings)
        return Gson().fromJson(
            json,
            object : TypeToken<SettingsModel?>() {}.type
        )
    }

    fun setSettings(json: String?) {
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.DB_Settings, json)
    }

    fun setGender(json: String?) {
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.KEY_GENDER, json)
    }

    fun registerHandle(memberModel: RegisterUserModel?) {

    }

    /*************************************************************/

}
