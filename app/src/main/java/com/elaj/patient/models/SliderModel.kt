package com.elaj.patient.models

import com.elaj.patient.classes.UtilityApp

class SliderModel {

    var title_ar: String? = null
    var title_en: String? = null
    var url: String? = null
    var status: Int? = 0

    val title: String?
        get() = if (UtilityApp.isEnglish) title_en else title_ar


}
