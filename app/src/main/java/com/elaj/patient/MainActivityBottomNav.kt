package com.elaj.patient

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.elaj.patient.Model.MessageEvent
import com.elaj.patient.activities.ActivityBase
import com.elaj.patient.classes.GlobalData
import com.elaj.patient.classes.UtilityApp
import com.elaj.patient.fragments.SoonFragment
import kotlinx.android.synthetic.main.layout_bottom_nav.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivityBottomNav : ActivityBase() {
    //    @BindView(R2.id.user) EditText username;
    private var gui_position = 0

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private var mTitle: CharSequence? = null
    private var fragmentManager: FragmentManager? = null
    private var ft: FragmentTransaction? = null
    private var newFragment: Fragment? = null

    private lateinit var tabTextArr: Array<TextView>
    private lateinit var tabIconsArr: Array<TextView>
    private lateinit var tabIndcsArr: Array<TextView>

    //    private var infoDialog: InfoDialog? = null
    private var qrCodeUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_bottom_nav)

        isMainActivityBottomNav = true
        mTitle = getString(R.string.app_name)
        //        setTitle("");

        tab4Txt.text = getString(R.string.profile)
        tab4Icon.text = getString(R.string.fal_cog)


        tabTextArr = arrayOf(tab1Txt, tab2Txt, tab3Txt, tab4Txt)
        tabIconsArr = arrayOf(tab1Icon, tab2Icon, tab3Icon, tab4Icon)
        tabIndcsArr = arrayOf(tab1Indc, tab2Indc, tab3Indc, tab4Indc)
        initListeners()


//        getHashKey();
//        bottomNav.setSelectedItemId(R.id.homeBtn);
        selectBttomTab(R.id.mainBtn)

    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun initListeners() {
        mainBtn.setOnClickListener {
            selectBttomTab(
                R.id.mainBtn
            )
        }
        searchBtn.setOnClickListener {
            selectBttomTab(
                R.id.searchBtn
            )
        }
        ordersBtn.setOnClickListener {
            selectBttomTab(
                R.id.ordersBtn
            )
        }
        profileBtn.setOnClickListener {
            selectBttomTab(
                R.id.profileBtn
            )
        }
    }

    private fun selectBttomTab(resId: Int) {
        when (resId) {
            R.id.mainBtn -> {
                newFragment = SoonFragment()
                gui_position = 0
                mTitle = getString(R.string.home)
            }
            R.id.searchBtn -> {
                newFragment = SoonFragment()
                gui_position = 2
                mTitle = getString(R.string.search)
            }
            R.id.ordersBtn -> {
                newFragment = SoonFragment()
                gui_position = 1
                mTitle = getString(R.string.my_questions)
            }
            R.id.profileBtn -> {
                newFragment =
//                    if (UtilityApp.isLogin()) AccountSettingFragment() else SettingFragment()
                    SoonFragment()
                gui_position = 3
                mTitle = getString(R.string.profile)
            }
        }
        chageColor(gui_position)

        if (newFragment != null) {
//            newFragment.setRetainInstance(true);
            fragmentManager = supportFragmentManager
            ft = fragmentManager!!.beginTransaction()
//            ft!!.setCustomAnimations(
//                R.anim.fade_in,
//                R.anim.fade_out
//            )
            ft!!.replace(R.id.container, newFragment!!).commitNowAllowingStateLoss()
        }

//        restoreActionBar();
    }

    private fun chageColor(pos: Int) {
        for (i in tabTextArr.indices) {
            if (i == pos) {
                tabTextArr[i].setTextColor(
                    ContextCompat.getColor(
                        getActiviy(),
                        R.color.bottomNavActive
                    )
                )
                tabIconsArr[i].setTextColor(
                    ContextCompat.getColor(
                        getActiviy(),
                        R.color.bottomNavActive
                    )
                )
                tabIndcsArr[i].setTextColor(
                    ContextCompat.getColor(
                        getActiviy(),
                        R.color.bottomNavActive
                    )
                )
//                tabIndcsArr[i].startAnimation(slideInRight)
                tabIndcsArr[i].visibility = visible

            } else {
                tabIconsArr[i].setTextColor(
                    ContextCompat.getColor(
                        getActiviy(),
                        R.color.bottomNavInactive
                    )
                )
                tabTextArr[i].setTextColor(
                    ContextCompat.getColor(
                        getActiviy(),
                        R.color.bottomNavInactive
                    )
                )
                tabIndcsArr[i].visibility = invisible
//                tabTextArr[i].visibility = invisible
            }
        }
    }

    fun restoreActionBar() {
        aTitle?.text = mTitle
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (GlobalData.LOGIN_TYPE == 1) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (gui_position == 0) {
                if (GlobalData.Position == 1) {
                    EventBus.getDefault()
                        .post(MessageEvent(MessageEvent.TYPE_PAGER, 0))
                    return false
                } else {
                    onBackPressed()
                }
            } else {
                // GlobalData.SelectedObject = -1;
//                onNavigationDrawerItemSelected(0);
                selectBttomTab(R.id.mainBtn)
                //                bottomNav.setSelectedItemId(R.id.homeBtn);
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.type == MessageEvent.TYPE_POSITION) {
            val pos = event.data as Int
            if (pos == 0) selectBttomTab(R.id.mainBtn) else if (pos == 1) selectBttomTab(R.id.searchBtn)
        }
    }

    fun signOut() {
        UtilityApp.logOut()
        GlobalData.Position = 0
        val intent =
            Intent(getActiviy(), MainActivityBottomNav::class.java)
        intent.putExtra("Type", "Login")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    companion object {
        const val DElAY_TIME = 350
    }
}