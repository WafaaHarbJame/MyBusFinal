package com.elaj.patient

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.elaj.patient.Model.MemberModel
import com.elaj.patient.Model.ResponseEvent
import com.elaj.patient.Model.ResultAPIModel
import com.elaj.patient.activities.ActivityBase
import com.elaj.patient.activities.WelcomeActivity
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.UtilityApp
import com.elaj.patient.dialogs.MyConfirmDialog
import com.elaj.patient.dialogs.MyConfirmDialog.Click
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class SplashScreen : ActivityBase() {

    var isGetProfile = false
    private val daleniLogo: ImageView? = null
    var notifyType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        startSplash()
    }

    private fun startSplash() {
        setContentView(R.layout.splash_screen)
        initData()
    }

    override fun onResume() {
        super.onResume()
        if (isGetProfile) {
            val intent = Intent(getActiviy(), MainActivityBottomNav::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun initData() {

//        GlobalData.IS_CUSTOMER = false

        DataFeacher().getCategories()
        DataFeacher().getConfig()

        Handler().postDelayed({
            // start if has access token
            if (UtilityApp.isLogin) {
                DataFeacher(object : DataFetcherCallBack {
                    override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                        isGetProfile = true
                        if (func == Constants.SUCCESS) {
                            val result: ResultAPIModel<MemberModel> =
                                obj as ResultAPIModel<MemberModel>
                            val user: MemberModel = result.data
                            UtilityApp.userData = user
                        }
                    }
                }).getMyProfile()
            } else {
                val intent = Intent(
                    getActiviy(),
//                    LoginActivity::class.java
                    WelcomeActivity::class.java
//                    BooksActivity::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }, SPLASH_TIMER.toLong())
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(responseEvent: ResponseEvent) {

        if (responseEvent.api == "getMyProfile") {
            when (responseEvent.type) {
                Constants.ERROR_DATA, Constants.FAIL_DATA, Constants.NO_CONNECTION -> {
                    if (responseEvent.type == Constants.NO_CONNECTION)
                        Toast(R.string.no_internet_connection)

                    val intent = Intent(getActiviy(), MainActivityBottomNav::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                Constants.UNAUTHENTICATED -> {
                    signOut()
                }
                else -> {
                    val result: ResultAPIModel<MemberModel> =
                        responseEvent.data as ResultAPIModel<MemberModel>
                    if (responseEvent.type == Constants.SUCCESS) {
                        val user: MemberModel = result.data
                        UtilityApp.userData = user
                    }
                    val intent = Intent(getActiviy(), MainActivityBottomNav::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            }
        }

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
                R.string.cancel2,
                okClick,
                cancelClick
            )
        }

    companion object {
        private const val SPLASH_TIMER = 3000
    }
}
