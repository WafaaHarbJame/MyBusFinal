package com.elaj.patient.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elaj.patient.models.CountryModel
import com.elaj.patient.R
import com.elaj.patient.Utils.NumberHandler
import com.elaj.patient.adapters.CountryCodeAdapter
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.DBFunction
import kotlinx.android.synthetic.main.dialog_country_code.*

import java.util.*

class CountryCodeDialog(
    var activity: Activity?,
    var countryCode: Int = 0,
    var dataFetcherCallBack: DataFetcherCallBack? = null
) :
    Dialog(activity!!) {

    val dialog: CountryCodeDialog
        get() = this

    var countryCodeModels: MutableList<CountryModel>? = null
    var selectedPos = 0
    var retryCount = 0
    var selectedCountry: CountryModel? = null

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.dialog_country_code)

        dialog.window?.setGravity(Gravity.BOTTOM);
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;


        rv.setHasFixedSize(true)
        val llm = LinearLayoutManager(activity)
        llm.orientation = RecyclerView.VERTICAL
        rv.layoutManager = llm

        searchTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (countryCodeModels != null) {
                    val searchStr = NumberHandler.arabicToDecimal(s.toString())
                    val list: MutableList<CountryModel> =
                        ArrayList()
                    for (countryCodeModel in countryCodeModels!!) {
                        if (countryCodeModel.name.toLowerCase()
                                .contains(searchStr) || countryCodeModel.code.toString()
                                .contains(searchStr)
                        ) list.add(countryCodeModel)
                    }
                    initAdapter(list)
                }
            }
        })


        closeBtn.setOnClickListener { dismiss() }

        okBtn.setOnClickListener {

            dismiss()

            if (dataFetcherCallBack != null)
                dataFetcherCallBack!!.Result(
                    selectedCountry,
                    "InfoDialog",
                    true
                )

        }

        getCountryCodes()

        try {
            if (activity != null && !activity!!.isFinishing) show()
        } catch (e: Exception) {
            dismiss()
        }


    }

    private fun initAdapter(countryList: MutableList<CountryModel>) {

        val adapter = CountryCodeAdapter(activity, countryList, selectedCountry,
            object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    selectedCountry = obj as CountryModel?

//                    if (selectedCountry == null) {
//                        GlobalData.Toast(activity, R.string.not_select_country_code)
//                        return@label
//                    }

                }
            })
        rv.adapter = adapter
        rv.scrollToPosition(selectedPos)

    }

    private fun getCountryCodes() {
        countryCodeModels = DBFunction.getCountries()
        if (countryCodeModels == null && retryCount < 2) {
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    retryCount++
                    if (IsSuccess)
                        getCountryCodes()
                }
            }).getCountries()
        } else {
            if (countryCodeModels != null) {
                selectCountry()
                initAdapter(countryCodeModels!!)
            }
        }

    }

    private fun selectCountry() {
        for (i in countryCodeModels!!.indices) {
            if (countryCodeModels!![i].code == countryCode) {
                selectedCountry = countryCodeModels!![i]
                selectedPos = i
            }
        }
    }

}