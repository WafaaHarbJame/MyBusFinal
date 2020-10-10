package com.elaj.patient.models

import com.elaj.patient.classes.UtilityApp

class CategoryModel {
    var id: String? = null
    var name_ar: String? = null
    var name_en: String? = null
    var photo: String? = null

    val name: String?
        get() = if (UtilityApp.isEnglish) name_en else name_ar

}