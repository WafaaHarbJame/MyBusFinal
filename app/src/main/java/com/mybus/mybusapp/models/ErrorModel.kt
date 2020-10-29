package com.mybus.mybusapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorModel {
    @SerializedName("status")
    @Expose
    var status = 0
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("details")
    @Expose
    lateinit var details: Array<String?>
}