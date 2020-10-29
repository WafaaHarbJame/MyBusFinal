package com.mybus.mybusapp.classes

import com.mybus.mybusapp.models.*
import com.mybus.mybusapp.RootApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DBFunction {


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


}
