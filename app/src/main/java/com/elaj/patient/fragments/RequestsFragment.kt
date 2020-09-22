//package com.elaj.patient.fragments
//
//import android.app.Activity
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentStatePagerAdapter
//import androidx.viewpager.widget.ViewPager
//import com.elaj.patient.R
//import com.elaj.patient.classes.Constants
//import kotlinx.android.synthetic.main.activity_provider_details.*
//import java.util.*
//
///**
// * A simple [Fragment] subclass.
// */
//class RequestsFragment : FragmentBase() {
//
//    var activity: Activity? = null
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view: View = inflater.inflate(R.layout.fragment_requests, container, false)
//        activity = getActivity()
//        return view
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        activity = getActivity()
//
//        setupViewPager(viewpager);
//        tabs.setupWithViewPager(viewpager);
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        activity = getActivity()
//    }
//
//    private fun setupViewPager(viewPager: ViewPager) {
//        val adapter = ViewPagerAdapter(childFragmentManager)
//
//        val currentBundle = Bundle()
//        currentBundle.putString(Constants.KEY_TYPE, Constants.CURRENT)
//        val currentFragment: Fragment = CustomerRequestsFragment()
//        currentFragment.arguments = currentBundle
//        currentFragment.retainInstance = true
//
//        val completedBundle = Bundle()
//        completedBundle.putString(Constants.KEY_TYPE, Constants.COMPLETED)
//        val completedFragment: Fragment = CustomerRequestsFragment()
//        completedFragment.arguments = completedBundle
//        completedFragment.retainInstance = true
//
//
//        adapter.addFragment(currentFragment, getString(R.string.current))
//        adapter.addFragment(completedFragment, getString(R.string.completed))
//
//        viewPager.adapter = adapter
//    }
//
//    class ViewPagerAdapter(manager: FragmentManager) :
//        FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//
//        private val mFragmentList: MutableList<Fragment> = ArrayList<Fragment>()
//        private val mFragmentTitleList: MutableList<String> = ArrayList()
//
//        override fun getItem(position: Int): Fragment {
//            return mFragmentList[position]
//        }
//
//        override fun getCount(): Int {
//            return mFragmentList.size
//        }
//
//        fun addFragment(fragment: Fragment, title: String) {
//            mFragmentList.add(fragment)
//            mFragmentTitleList.add(title)
//        }
//
//        override fun getPageTitle(position: Int): CharSequence? {
//            return mFragmentTitleList[position]
//        }
//    }
//
//
//}