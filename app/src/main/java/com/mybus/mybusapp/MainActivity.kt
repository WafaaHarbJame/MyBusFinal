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

class MainActivity : ActivityBase() {
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
    private  var  userType:Int = 0;
    lateinit var user: MemberModel
    private var toOrder: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_bottom_nav)

        isMainActivityBottomNav = true
        mTitle = getString(R.string.app_name)

        tabTextArr = arrayOf(tab1Txt, tab2Txt, tab3Txt, tab4Txt)
        tabIconsArr = arrayOf(tab1Icon, tab2Icon, tab3Icon, tab4Icon)


         user= UtilityApp.userData!!

        val bundle = intent.extras

        if(bundle!=null){
            toOrder = bundle?.getBoolean(Constants.KEY_TO_ORDERS)!!
            Log.d("Log toOrder", "Log toOrder $toOrder");

        }

        userType=user.type

        if(userType==0){
            getData()

        }

        if(userType==1){
            finishOrderBtn.visibility=visible
            newFragment = HomeClientFragment()

        }
        else if(userType==2){

            finishOrderBtn.visibility=gone
            ordersBtn.visibility=gone
            newFragment = HomeDriverFragment()
            tab2Txt.text=getString(R.string.all_orders);

        }

        if(toOrder){
            selectBottomTab(R.id.ordersBtn)
        }
        else{
            selectBottomTab(R.id.mainBtn)

        }


        initListeners()

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
                    HomeClientFragment()
                } else{
                    HomeDriverFragment()

                }
                gui_position = 0
                mTitle = getString(R.string.home)
            }
            R.id.ordersBtn -> {

                newFragment = if(userType==1){
                    AllOrderClientFragment()
                } else{
                    AllDriverRequestsFragment()

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
        changeColor(gui_position)

        if (newFragment != null) {
            fragmentManager = supportFragmentManager
            ft = fragmentManager!!.beginTransaction()
            ft!!.replace(R.id.container, newFragment!!).commitNowAllowingStateLoss()
        }

    }

    private fun changeColor(pos: Int) {
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (gui_position == 0) {
                if (GlobalData.Position == 1) {
                    EventBus.getDefault()
                        .post(MessageEvent(MessageEvent.TYPE_PAGER, 0))
                    return false
                } else {
                    onBackPressed()
                }
            }

            else {
                selectBottomTab(R.id.mainBtn)
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


    private fun getData() {
        val mobile = UtilityApp.userData?.mobileWithCountry
        DataFeacher(object : DataFetcherCallBack {
            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {

                if (func == Constants.SUCCESS) {
                    UtilityApp.userData = obj as MemberModel?
                    userType= UtilityApp.userData!!.type
                    if(userType==1){
                        finishOrderBtn.visibility=visible
                        newFragment = HomeClientFragment()

                    }
                    else{
                        finishOrderBtn.visibility=gone
                        newFragment = HomeDriverFragment()

                    }


                    }

            }
        }).getMyAccount(mobile!!)
    }

}