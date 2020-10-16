package com.elaj.patient.activities

import android.content.Intent
import android.os.Bundle
import com.elaj.patient.MainActivityBottomNav
import com.elaj.patient.models.SettingsModel
import com.elaj.patient.R
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.DBFunction
import com.elaj.patient.classes.UtilityApp
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : ActivityBase() {

    var settingsModel: SettingsModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }

        setContentView(R.layout.activity_welcome)


        continueBtn.setOnClickListener {

            UtilityApp.isFirstLogin = false

//            val intent = Intent(getActiviy(), RegisterActivity::class.java)
            val intent = Intent(getActiviy(), LoginActivity::class.java)
            startActivity(intent)

        }

        getSettings()

        titleTV.text = settingsModel?.welcome?.title
        bodyTV.text = settingsModel?.welcome?.body


    }

    private fun getSettings() {

        settingsModel = DBFunction.getSettings()
        if (settingsModel == null) {
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    settingsModel = obj as SettingsModel?
                }
            }).getSettings()
        }
    }


}
