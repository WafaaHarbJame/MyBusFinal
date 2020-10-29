package com.mybus.mybusapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mybus.mybusapp.models.MessageEvent
import com.mybus.mybusapp.activities.ActivityBase
import com.mybus.mybusapp.apiHandlers.DataFeacher
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.classes.GlobalData
import com.mybus.mybusapp.classes.UtilityApp
import com.mybus.mybusapp.fragments.*
import com.mybus.mybusapp.models.MemberModel
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
    private var qrCodeUrl: String? = null
    private  var  userType:Int = 0;
    lateinit var user: MemberModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_bottom_nav)

        isMainActivityBottomNav = true
        mTitle = getString(R.string.app_name)

        tabTextArr = arrayOf(tab1Txt, tab2Txt, tab3Txt, tab4Txt)
        tabIconsArr = arrayOf(tab1Icon, tab2Icon, tab3Icon, tab4Icon)


         user= UtilityApp.userData!!
        Log.d("User type", "user type$userType");

        userType=user.type

        if(userType==0){
            getData()

        }

        if(userType==1){
            finishOrderBtn.visibility=visible
            newFragment = MainScreenFragment()

        }
        else{

            finishOrderBtn.visibility=gone
            ordersBtn.visibility=gone
            newFragment = HomeScreenDriverFragment()
            tab2Txt.text=getString(R.string.all_orders);



        }


        initListeners()
        selectBottomTab(R.id.mainBtn)

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
            selectBottomTab(
                R.id.mainBtn
            )
        }
        finishOrderBtn.setOnClickListener {
            selectBottomTab(
                R.id.finishOrderBtn
            )
        }
        ordersBtn.setOnClickListener {
            selectBottomTab(
                R.id.ordersBtn
            )
        }
        settingsBtn.setOnClickListener {
            selectBottomTab(
                R.id.settingsBtn
            )
        }
    }


    private fun selectBottomTab(resId: Int) {
        when (resId) {
            R.id.mainBtn -> {
                newFragment = if(userType==1){
                    MainScreenFragment()
                } else{
                    HomeScreenDriverFragment()

                }
                gui_position = 0
                mTitle = getString(R.string.home)
            }
            R.id.ordersBtn -> {

                newFragment = if(userType==1){
                    AllOrderClientFragment()
                } else{
                    AllRequestsFragment()

                }
                gui_position = 1
                mTitle = getString(R.string.all_orders)
            }

            R.id.finishOrderBtn -> {
                newFragment = if(userType==1){
                    FinishedClientFragment()
                } else{
                    FinishedDriveragment()

                }
                gui_position = 2
                mTitle = getString(R.string.finshed_order)
            }

            R.id.settingsBtn -> {
                newFragment =
                    SettingsFragment()
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
            }
        }
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
                selectBottomTab(R.id.mainBtn)
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
            if (pos == 0) selectBottomTab(R.id.mainBtn)
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

    private fun getData() {
        val mobile = UtilityApp.userData?.mobileWithCountry
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                if (func == Constants.SUCCESS) {
                    UtilityApp.userData = obj as MemberModel?
                    userType= UtilityApp.userData!!.type
                    if(userType==1){
                        finishOrderBtn.visibility=visible
                        newFragment = MainScreenFragment()

                    }
                    else{
                        finishOrderBtn.visibility=gone
                        newFragment = HomeScreenDriverFragment()

                    }

                    }

            }
        }).getMyAccount(mobile!!)
    }

}