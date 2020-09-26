package com.elaj.patient.fragments

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.elaj.patient.R
import com.elaj.patient.classes.Constants
import com.github.chrisbanes.photoview.PhotoViewAttacher
import com.wang.avi.AVLoadingIndicatorView

class ImageFragment : FragmentBase() {
    private var parent: LinearLayout? = null
    private var img: ImageView? = null
    private var loadingLY: AVLoadingIndicatorView? = null
    var imgUrl: String? = ""

    //    ArrayList<String> imgUrlList = new ArrayList<>();
    var imgBitmap: Bitmap? = null
    var mAttacher: PhotoViewAttacher? = null
    var activity: Activity? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_image, container, false)
        parent = view.findViewById(R.id.parent)
        img = view.findViewById(R.id.img)
        loadingLY = view.findViewById(R.id.loadingLY)
        activity = getActivity()

//        mAttacher = new PhotoViewAttacher(img);
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity()
        val bundle = arguments
        if (bundle != null) {
            imgUrl = bundle.getString(Constants.KEY_IMAGE_URL)
        }

//        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//            @Override
//            public void onPhotoTap(View view, float x, float y) {
////                System.out.println("Log photo tap");
//            }
//
//            @Override
//            public void onOutsidePhotoTap() {
////                System.out.println("Log out photo tap");
//                activity.finish();
//            }
//        });

//        if (imgBitmap != null) {
//            loadingLY.setVisibility(View.GONE);
//            img.setVisibility(View.VISIBLE);
//            img.setImageBitmap(imgBitmap);
//        } else {
//            loadingLY.setVisibility(View.VISIBLE);
        Glide.with(this)
            .asBitmap()
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.error_logo)
            ) //                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            //                .listener(new RequestListener<String, GlideDrawable>() {
            //                    @Override
            //                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            //                        loadingLY.setVisibility(View.GONE);
            //                        img.setVisibility(View.VISIBLE);
            //                        mAttacher.update();
            //                        return false;
            //                    }
            //
            //                    @Override
            //                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            //                        loadingLY.setVisibility(View.GONE);
            //                        img.setVisibility(View.VISIBLE);
            //                        mAttacher.update();
            //                        return false;
            //                    }
            //                })
            .into(img!!)
        //        }
    }
}
