package com.elaj.patient.models

import com.elaj.patient.classes.UtilityApp.isEnglish
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WelcomeModel {


    val title_ar: String? = ""
    val body_ar: String? = ""
    val title_en: String? = ""
    val body_en: String? = ""

    val title: String?
        get() = if (isEnglish) title_en else title_ar
    val body: String?
        get() = if (isEnglish) body_en else body_ar
}
