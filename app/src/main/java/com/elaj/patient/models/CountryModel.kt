package com.elaj.patient.models

import com.elaj.patient.classes.UtilityApp

class CountryModel {
    var id: String? = null
    var name_ar: String = ""
    var name_en: String = ""
    var code: Int = 0
    var flag: String? = null


    val name: String
        get() = if (UtilityApp.isEnglish) name_en else name_ar

}