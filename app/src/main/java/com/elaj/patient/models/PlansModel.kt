package com.elaj.patient.models

import com.elaj.patient.classes.UtilityApp

class PlansModel {

    var title_ar: String? = null
    var title_en: String? = null
    var desc_ar: String? = null
    var desc_en: String? = null
    var price: String? = null

    val title: String?
        get() = if (UtilityApp.isEnglish) title_en else title_ar

    val desc: String?
        get() = if (UtilityApp.isEnglish) desc_en else desc_ar


}
