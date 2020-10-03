package com.elaj.patient.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.elaj.patient.R
import kotlinx.android.synthetic.main.tool_bar.*


class SettingsFragment : FragmentBase() {
    var activity: Activity? = null

    //    var changeLanguageDialog: ChangeLanguageDialog? = null
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


//        languageTxt.text =
//            if (UtilityApp.language == Constants.Arabic) getString(R.string.arabic) else getString(R.string.english)
//
//        languageBtn.setOnClickListener {
//
//            if (changeLanguageDialog == null) {
//                changeLanguageDialog = ChangeLanguageDialog(requireActivity())
//                changeLanguageDialog!!.setOnDismissListener { changeLanguageDialog = null }
//            }
//
//        }

//        termsBtn.setOnClickListener {
//
//            val intent = Intent(requireActivity(), PageFragmentActivity::class.java)
//            intent.putExtra(Constants.KEY_FRAGMENT_TYPE, Constants.FRAG_TERMS)
//            startActivity(intent)
//        }

//        passwordBtn.setOnClickListener {
//
//            val intent = Intent(requireActivity(), ChangePasswordActivity::class.java)
//            startActivity(intent)
//
//        }

//        ratingBtn.setOnClickListener {
//
//            ActivityHandler.OpenGooglePlay(requireActivity())
//
//        }

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
