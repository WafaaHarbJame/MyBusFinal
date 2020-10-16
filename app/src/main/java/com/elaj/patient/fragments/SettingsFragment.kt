package com.elaj.patient.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.elaj.patient.MainActivityBottomNav
import com.elaj.patient.R
import com.elaj.patient.Utils.ActivityHandler
import com.elaj.patient.activities.LoginActivity
import com.elaj.patient.activities.PlansActivity
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.UtilityApp
import com.elaj.patient.dialogs.ChangeLanguageDialog
import com.elaj.patient.dialogs.ChangePasswordDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.tool_bar.*


class SettingsFragment : FragmentBase() {
    var activity: Activity? = null

    private var changeLanguageDialog: ChangeLanguageDialog? = null
    private var changePasswordDialog: ChangePasswordDialog? = null
    lateinit var container: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        this.container = container as FrameLayout

        activity = getActivity()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()

        mainTitleTxt.text = getString(R.string.settings)
        homeBtn.visibility = gone


        if (UtilityApp.isLogin) {
            signOutIcon.text = getString(R.string.fal_sign_out)
            signOutLabel.text = getString(R.string.sign_out)
        } else {
            signOutIcon.text = getString(R.string.fal_sign_in)
            signOutLabel.text = getString(R.string.sign_in)
        }

        languageTxt.text =
            if (UtilityApp.language == Constants.Arabic) getString(R.string.arabic) else getString(R.string.english)

        languageBtn.setOnClickListener {

            if (changeLanguageDialog == null) {
                changeLanguageDialog = ChangeLanguageDialog(requireActivity())
                changeLanguageDialog!!.setOnDismissListener { changeLanguageDialog = null }
            }

        }

        plansBtn.setOnClickListener {

            val intent = Intent(requireActivity(), PlansActivity::class.java)
            startActivity(intent)

        }

        termsBtn.setOnClickListener {

//            val intent = Intent(requireActivity(), PageFragmentActivity::class.java)
//            intent.putExtra(Constants.KEY_FRAGMENT_TYPE, Constants.FRAG_TERMS)
//            startActivity(intent)
        }

        passwordBtn.setOnClickListener {

            if (changePasswordDialog == null) {
                changePasswordDialog = ChangePasswordDialog(requireActivity())
                changePasswordDialog!!.setOnDismissListener { changePasswordDialog = null }
            }
        }

        ratingBtn.setOnClickListener {

            ActivityHandler.OpenGooglePlay(requireActivity())

        }

        logoutBtn.setOnClickListener {

            var intent = Intent(requireActivity(), LoginActivity::class.java)

            if (UtilityApp.isLogin) {
                UtilityApp.logOut()
                FirebaseAuth.getInstance().signOut()
                intent = Intent(requireActivity(), MainActivityBottomNav::class.java)
            }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }

    }

//    private fun replaceFragment(fragment: Fragment) {
//
//        val backStateName = fragment.javaClass.name
////            String backStateName = fragment . getClass ().getName();
//
//        val fragmentManager = childFragmentManager
//        val fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0)
//
//        if (!fragmentPopped) { //fragment not in back stack, create it.
//            val ft = fragmentManager.beginTransaction()
//            ft.add(R.id.container, fragment)
//            ft.addToBackStack(null)
//            ft.commit()
//        }
//    }


//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
//            finish();
//        } else {
//            super.onBackPressed();
//            restoreActionBar();
//        }
//    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }


}
