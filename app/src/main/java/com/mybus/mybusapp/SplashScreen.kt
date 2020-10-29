package com.mybus.mybusapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import com.mybus.mybusapp.activities.ActivityBase
import com.mybus.mybusapp.activities.LoginActivity
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.dialogs.MyConfirmDialog
import com.mybus.mybusapp.dialogs.MyConfirmDialog.Click
import com.mybus.mybusapp.models.MemberModel


class SplashScreen : ActivityBase() {

    var isGetProfile = false
    private val daleniLogo: ImageView? = null
    var notifyType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        startSplash()
    }

    private fun startSplash() {
        setContentView(R.layout.splash_screen)
        initData()
    }

    override fun onResume() {
        super.onResume()
        if (isGetProfile) {
            val intent = Intent(getActiviy(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun initData() {

//        GlobalData.IS_CUSTOMER = false

        DataFeacher(null).getCountries()

        Handler(Looper.getMainLooper()).postDelayed({
            // start if has access token
            if (UtilityApp.isLogin) {

                val mobile = UtilityApp.userData?.mobileWithCountry

                DataFeacher(object : DataFetcherCallBack {
                    override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                        if (func == Constants.SUCCESS) {

//                            val user = obj
                            UtilityApp.userData = obj as MemberModel?

                            val intent = Intent(getActiviy(), MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        } else {

                            GlobalData.errorDialog(
                                getActiviy(),
                                R.string.confirm_account,
                                getString(R.string.fail_confirm_account)
                            )

                        }


                    }
                }).getMyAccount(mobile!!)

            } else {
                val intent = Intent(
                    getActiviy(),
                    if (UtilityApp.isFirstLogin)
                        LoginActivity::class.java
                    else
//                        MainActivityBottomNav::class.java
                        LoginActivity::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }, SPLASH_TIMER.toLong())
    }

    override fun onStart() {
        super.onStart()
        //   EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        // EventBus.getDefault().unregister(this)
    }


    fun signOut() {
        UtilityApp.logOut()
        val intent = Intent(getActiviy(), Constants.MAIN_ACTIVITY_CLASS)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private val worngDialog: Unit
        private get() {
            val okClick: Click = object : Click() {
                override fun click() {
                    recreate()
                }
            }
            val cancelClick: Click = object : Click() {
                override fun click() {
                    finish()
                }
            }
            MyConfirmDialog(
                getActiviy(),
                getString(R.string.fail_to_get_data),
                R.string.retry,
                R.string.cancel,
                okClick,
                cancelClick
            )
        }

    companion object {
        private const val SPLASH_TIMER = 3000
    }
}
