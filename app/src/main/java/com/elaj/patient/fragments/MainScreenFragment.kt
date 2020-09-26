package com.elaj.patient.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.elaj.patient.Model.CardViewPager.CardFragmentPagerAdapter
import com.elaj.patient.Model.CardViewPager.ShadowTransformer
import com.elaj.patient.R
import kotlinx.android.synthetic.main.fragment_main_screen.*


class MainScreenFragment : FragmentBase() {
    var activity: Activity? = null

    private var mFragmentCardAdapter: CardFragmentPagerAdapter? = null
    private var mFragmentCardShadowTransformer: ShadowTransformer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main_screen, container, false)
        activity = getActivity()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()

        mFragmentCardAdapter =
            CardFragmentPagerAdapter(childFragmentManager, dpToPixels(2, requireActivity()))
//
//        mFragmentCardShadowTransformer = ShadowTransformer(viewpager, mFragmentCardAdapter)

        setupViewPager(viewPager)

    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }

    companion object {
        const val DElAY_TIME = 300
    }

    private fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }

    private fun setupViewPager(viewPager: ViewPager) {
        for (i in 0..3) {
//            val bundle = Bundle()
//            bundle.putString(Constants.KEY_IMAGE_URL, mainSlidesModel.banners.get(i).image)
//            bundle.putString(Constants.KEY_IMAGE_LINK, mainSlidesModel.banners.get(i).link)
            val sliderFragment = SliderFragment()
//            sliderFragment.arguments = bundle
            sliderFragment.retainInstance = true
            mFragmentCardAdapter!!.addCardFragment(sliderFragment)
        }
        viewPager.adapter = mFragmentCardAdapter
    }
}