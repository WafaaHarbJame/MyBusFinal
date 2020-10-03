package com.elaj.patient.dialogs

//import com.franmontiel.localechanger.LocaleChanger
//import com.google.firebase.messaging.FirebaseMessaging
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.elaj.patient.R
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.UtilityApp
import kotlinx.android.synthetic.main.dialog_change_language.*

class ChangeLanguageDialog(
    var activity: Activity?
) :
    Dialog(activity!!) {

    val dialog: ChangeLanguageDialog
        get() = this

    private var currLang: String? = null

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.dialog_change_language)

        dialog.window?.setGravity(Gravity.BOTTOM);
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;

        currLang = UtilityApp.language

        saveBtn.setOnClickListener {

            UtilityApp.language = currLang
//            LocaleChanger.setLocale(Locale(currLang!!))

//            if (currLang == Constants.English)
//                FirebaseMessaging.getInstance()
//                    .unsubscribeFromTopic("promotional_${Constants.Arabic}")
//            else
//                FirebaseMessaging.getInstance()
//                    .unsubscribeFromTopic("promotional_${Constants.English}")
//
//            FirebaseMessaging.getInstance().subscribeToTopic("promotional_$currLang")

            dismiss()

//            val intent = Intent(activity, SplashScreen::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//            activity?.startActivity(intent)

        }


        arabicRB.setOnClickListener {
            currLang = Constants.Arabic
        }

        englishRB.setOnClickListener {
            currLang = Constants.English
        }

        if (currLang == Constants.Arabic)
            arabicRB.isChecked = true
        else
            englishRB.isChecked = true



        try {
            if (activity != null && !activity!!.isFinishing) show()
        } catch (e: Exception) {
            dismiss()
        }


    }


}