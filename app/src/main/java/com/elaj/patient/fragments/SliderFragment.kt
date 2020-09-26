package com.elaj.patient.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.elaj.patient.R
import kotlinx.android.synthetic.main.fragment_slider.*

class SliderFragment : FragmentBase() {
    var activity: Activity? = null

    var imgUrl: String? = null
    var link: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_slider, container, false)
        activity = getActivity()

        return view
    }

    fun getCardView(): CardView {
        return cardView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()

//        cardView.maxCardElevation = 40f

//        val bundle = arguments
//        if (bundle != null) {
//            imgUrl = bundle.getString(Constants.KEY_IMAGE_URL)
////            link = bundle.getString(Constants.KEY_IMAGE_LINK)
//        }

//        Glide.with(activity!!)
//            .asBitmap()
//            .load(imgUrl)
//            .apply(
//                RequestOptions()
//                    .placeholder(R.drawable.error_logo)
//            )
//            .into(slideImg!!)

//        slideImg!!.setOnClickListener { view: View? ->
//            if (link != null && !link!!.isEmpty()) {
//                link = link!!.replace("amp;", "")
//                ActivityHandler.OpenBrowser(requireActivity(), GlobalData.BaseURL.toString() + link)
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        activity = getActivity()
    }

    companion object {
        const val DElAY_TIME = 300
    }
}