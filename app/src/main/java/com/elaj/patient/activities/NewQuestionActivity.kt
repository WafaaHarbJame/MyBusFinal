package com.elaj.patient.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.elaj.patient.R
import com.elaj.patient.Utils.NumberHandler
import com.elaj.patient.adapters.SyndromeAdapter
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.GlobalData
import com.elaj.patient.classes.UtilityApp
import com.elaj.patient.dialogs.SuccessSendDialog
import com.elaj.patient.models.MemberModel
import com.elaj.patient.models.QuestionModel
import com.elaj.patient.models.SyndromeModel
import kotlinx.android.synthetic.main.activity_new_question.*


class NewQuestionActivity : ActivityBase() {

    var successSendDialog: SuccessSendDialog? = null

    var catId: String? = null
    var catName: String? = null

    var genderAdapter: ArrayAdapter<String>? = null
    private var genderData: ArrayList<String> = ArrayList()
    var genderVal: String? = null

    var syndromesList: MutableList<SyndromeModel>? = null

    var isCallDoctor = false
    var user: MemberModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_question)

        title = ""

        val bundle = intent.extras
        catId = bundle?.getString(Constants.KEY_CAT_ID)
        catName = bundle?.getString(Constants.KEY_CAT_NAME)

        rv.layoutManager = LinearLayoutManager(getActiviy())

        user = UtilityApp.userData

        genderSpinner.setOnItemClickListener { adapterView, view, pos, l ->

            genderVal = if (pos == 0) Constants.MALE else Constants.FEMALE

        }

        askQuestionLY.setOnClickListener {
            isCallDoctor = false
            selectButton(true)

        }

        callDoctorLY.setOnClickListener {
            isCallDoctor = true
            selectButton(false)

        }

        sendBtn.setOnClickListener {

            sendQuestion()

        }

        askQuestionLY.performClick()

        getGender()
        getCategorySyndrome()

    }

    private fun sendQuestion() {

        val titleStr = NumberHandler.arabicToDecimal(titleET.text.toString().trim())
        val descStr = NumberHandler.arabicToDecimal(detailsET.text.toString().trim())
        val ageStr = NumberHandler.arabicToDecimal(ageET.text.toString().trim())
        val whatsAppStr = NumberHandler.arabicToDecimal(whatsAppET.text.toString().trim())
        val skypeStr = NumberHandler.arabicToDecimal(skypeET.text.toString().trim())

        var hasError = false
        if (titleStr.isEmpty()) {
            hasError = true
            titleInput.error = getString(R.string.empty_title)
        }

        if (descStr.isEmpty()) {
            hasError = true
            detailsInput.error = getString(R.string.empty_desc)
        }

        val selectedSyndromes = ""
        if (isCallDoctor) {

            if (whatsAppStr.isEmpty() && skypeStr.isEmpty()) {
                hasError = true
//                whatsAppInput.error = getString(R.string.empty_whatsapp_number)
                skypeInput.error = getString(R.string.empty_whatsapp_skype_number)
            }


        } else {

            if (ageStr.isEmpty()) {
                hasError = true
                ageInput.error = getString(R.string.empty_age)
            }
            if (genderVal == null) {
                hasError = true
                genderInput.error = getString(R.string.empty_gender)
            }

            for (syndorme in syndromesList!!) {
                if (syndorme.isSelected) {
                    selectedSyndromes.plus(syndorme.name_en + ":" + syndorme.name_ar + ",")
                }
            }
            println("syndorme selectedSyndromes $selectedSyndromes")
        }

        if (hasError)
            return

        val questionModel = QuestionModel().apply {
            userMobile = user?.mobileWithCountry
            if (isCallDoctor) {
                whatsAppNumber = whatsAppStr
                skypeNumber = skypeStr
            } else {
                age = ageStr.toDouble()
                gender = genderVal
                syndromes = selectedSyndromes
            }
            title = titleStr
            details = descStr
            type = if (isCallDoctor) QuestionModel.CALL else QuestionModel.QUESTION
            status = QuestionModel.STATUS_NEED_ANSWER
        }


        GlobalData.progressDialog(
            getActiviy(),
            R.string.new_question,
            R.string.please_wait_sending
        )
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                GlobalData.progressDialogHide()

                if (func == Constants.SUCCESS) {

                    if (successSendDialog == null) {
                        successSendDialog = SuccessSendDialog(getActiviy())
                        successSendDialog!!.setOnDismissListener {
                            successSendDialog = null
                            finish()
                        }
                    }

                } else {
                    GlobalData.errorDialog(
                        getActiviy(),
                        R.string.new_question,
                        getString(R.string.fail_send_question)
                    )
                }

            }
        }).sendQuestion(questionModel)

    }

    private fun initAdapter() {

        val adapter = SyndromeAdapter(syndromesList)
        rv.adapter = adapter
    }

    private fun selectButton(isWriteQuestion: Boolean) {

        askQuestionLY.background =
            ContextCompat.getDrawable(
                getActiviy(),
                if (isWriteQuestion) R.drawable.round_corner_accent_fill else R.drawable.round_corner_primary_light_fill
            )
        askQuestionIcon.setImageDrawable(
            ContextCompat.getDrawable(
                getActiviy(),
                if (isWriteQuestion)
                    R.drawable.write_question_active
                else
                    R.drawable.write_question_inactive
            )
        )
        askQuestionTV.setTextColor(
            ContextCompat.getColor(
                getActiviy(),
                if (isWriteQuestion) R.color.white else R.color.colorAccent
            )
        )

        callDoctorLY.background =
            ContextCompat.getDrawable(
                getActiviy(),
                if (isWriteQuestion) R.drawable.round_corner_primary_light_fill else R.drawable.round_corner_accent_fill
            )
        callDoctorIcon.setImageDrawable(
            ContextCompat.getDrawable(
                getActiviy(),
                if (isWriteQuestion)
                    R.drawable.call_doctor_inactive
                else
                    R.drawable.call_doctor_active
            )
        )
        callDoctorTV.setTextColor(
            ContextCompat.getColor(
                getActiviy(),
                if (isWriteQuestion) R.color.colorAccent else R.color.white
            )
        )

        if (isWriteQuestion) {
            patientDetailsLabel.visibility = visible
            ageInput.visibility = visible
            genderInput.visibility = visible
            rv.visibility = visible

            contactInfoLabel.visibility = gone
            contactInfoHintLabel.visibility = gone
            whatsAppInput.visibility = gone
            skypeInput.visibility = gone
        } else {
            patientDetailsLabel.visibility = gone
            ageInput.visibility = gone
            genderInput.visibility = gone
            rv.visibility = gone

            contactInfoLabel.visibility = visible
            contactInfoHintLabel.visibility = visible
            whatsAppInput.visibility = visible
            skypeInput.visibility = visible
        }

    }

    private fun getCategorySyndrome() {

        syndromeLoading.visibility = visible
        rv.visibility = gone

        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                syndromeLoading.visibility = gone
                syndromesList = obj as MutableList<SyndromeModel>?
                if (syndromesList?.isNotEmpty()!!) {
                    rv.visibility = visible
                    initAdapter()
                }
            }
        }).getCategorySyndromes(catId!!)

    }

    private fun getGender() {

        genderData.clear()
        genderData.add(getString(R.string.male))
        genderData.add(getString(R.string.female))

        genderAdapter = ArrayAdapter<String>(
            getActiviy(), R.layout.row_spinner_item, R.id.spinnerRowTxt, genderData
        )
        genderAdapter!!.setDropDownViewResource(R.layout.row_popup_spinner_item);
        genderSpinner.setAdapter(genderAdapter)


    }

}
