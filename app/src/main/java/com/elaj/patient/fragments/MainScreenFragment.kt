package com.elaj.patient.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.elaj.patient.models.CardViewPager.CardFragmentPagerAdapter
import com.elaj.patient.models.CardViewPager.ShadowTransformer
import com.elaj.patient.R
import com.elaj.patient.adapters.MainCategoriesAdapter
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.DBFunction
import com.elaj.patient.models.CategoryModel
import com.elaj.patient.models.SettingsModel
import com.elaj.patient.models.SliderModel
import kotlinx.android.synthetic.main.fragment_main_screen.*


class MainScreenFragment : FragmentBase() {
    var activity: Activity? = null

    private var mFragmentCardAdapter: CardFragmentPagerAdapter? = null

    var slidersList: MutableList<SliderModel>? = null
    var categoriesList: MutableList<CategoryModel>? = null

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

        rv.layoutManager = GridLayoutManager(requireActivity(), 2)

        getSliders()

        getCategories()
    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }


    private fun initAdapter() {

        val adapter = MainCategoriesAdapter(requireActivity(), categoriesList)
        rv.adapter = adapter
    }

    private fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }

    private fun setupViewPager(viewPager: ViewPager) {
        for (slider in slidersList!!) {
            if (slider.status == 1) {
                val bundle = Bundle()
                bundle.putString(Constants.KEY_SLIDER_TITLE, slider.title)
                bundle.putString(Constants.KEY_SLIDER_URL, slider.url)
                val sliderFragment = SliderFragment()
                sliderFragment.arguments = bundle
                sliderFragment.retainInstance = true
                mFragmentCardAdapter!!.addCardFragment(sliderFragment)
            }
        }
        viewPager.adapter = mFragmentCardAdapter
    }

    private fun getSliders() {

        slidersList = DBFunction.getSliders()
        if (slidersList == null) {
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    slidersList = obj as MutableList<SliderModel>?
                    setupViewPager(viewPager)
                }
            }).getSliders()
        } else {
            setupViewPager(viewPager)
        }
    }

    private fun getCategories() {

        categoriesList = DBFunction.getCategories()
        if (categoriesList == null) {
            DataFeacher(object : DataFetcherCallBack {
                override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
                    categoriesList = obj as MutableList<CategoryModel>?
                    initAdapter()
                }
            }).getSliders()
        } else {
            initAdapter()
        }
    }


}