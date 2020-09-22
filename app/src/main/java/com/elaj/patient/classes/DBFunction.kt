package com.elaj.patient.classes

import com.elaj.patient.Model.*
import com.elaj.patient.RootApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DBFunction {
//    static Realm realm = Realm.getDefaultInstance();

    fun getCategories(): MutableList<ServiceModel>? {
        val json: String? =
            RootApplication.instance!!.sharedPManger!!.getDataString(Constants.KEY_CATEGORIES)
        return Gson().fromJson(
            json,
            object : TypeToken<MutableList<ServiceModel>?>() {}.type
        )
    }

    fun setCategories(json: String?) {
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.KEY_CATEGORIES, json)
    }

    fun getCountries(): MutableList<CountryModel>? {
        val json: String? =
            RootApplication.instance!!.sharedPManger!!.getDataString(Constants.KEY_COUNTRIES)
        return Gson().fromJson(
            json,
            object : TypeToken<MutableList<CountryModel>?>() {}.type
        )
    }

    fun setCountries(json: String?) {
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.KEY_COUNTRIES, json)
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

    fun getSettings(): ConfigModel? {
        val json: String? =
            RootApplication.instance!!.sharedPManger!!.getDataString(Constants.KEY_SETTINGS)
        return Gson().fromJson(
            json,
            object : TypeToken<ConfigModel?>() {}.type
        )
    }

    fun setGender(json: String?) {
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.KEY_GENDER, json)
    }

    fun setSettings(json: String?) {
        RootApplication.instance!!.sharedPManger!!.SetData(Constants.KEY_SETTINGS, json)
    }


    /*************************************************************/

}
