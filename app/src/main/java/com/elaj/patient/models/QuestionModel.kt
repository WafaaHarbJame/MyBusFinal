package com.elaj.patient.models

class QuestionModel {

    var userMobile: String? = null
    var age: Double = 0.0
    var gender: String? = null
    var title: String? = null
    var details: String? = null
    var syndromes: String? = null
    var whatsAppNumber: String? = null
    var skypeNumber: String? = null
    var type: String? = null
    var status: String? = null


    companion object {
        const val QUESTION = "question"
        const val CALL = "call"

        const val STATUS_NEED_ANSWER = "need_answer"
        const val STATUS_ANSWERED = "answered"
        const val STATUS_NEED_REPLAY = "need_answer"
    }

}
