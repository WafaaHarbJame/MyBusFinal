package com.elaj.patient.models

import com.elaj.patient.classes.UtilityApp

class SyndromeModel() {

    var id: String? = null
    var name_ar: String = ""
    var name_en: String = ""

    val name: String?
        get() = if (UtilityApp.isEnglish) name_en else name_ar
}