package com.elaj.patient.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elaj.patient.Model.CountryModel
import com.elaj.patient.R
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.bumptech.glide.Glide
import com.elaj.patient.MainActivityBottomNav
import com.elaj.patient.Model.CategoryModel
import com.elaj.patient.activities.NewQuestionActivity
import com.elaj.patient.classes.Constants

class MainCategoriesAdapter(
    private val activity: Activity?,
    var list: MutableList<CategoryModel>?
) :
    RecyclerView.Adapter<MainCategoriesAdapter.MyHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_category, null, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

//        val countryCodeModel: CountryModel = list[position]

//        holder.nameTxt.text = countryCodeModel.name
//
//        val code = "+" + countryCodeModel.countryCode
//        holder.codeTxt.text = code
//
//        Glide.with(activity!!)
//            .asBitmap()
//            .load(countryCodeModel.getPhoto())
//            .placeholder(R.drawable.error_logo)
//            .into(holder.flagImg)

    }

    override fun getItemCount(): Int {
        return list?.size ?: 8
    }

    inner class MyHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {

//        val nameTxt: TextView = itemView!!.findViewById(R.id.nameTxt)
//        val codeTxt: TextView = itemView!!.findViewById(R.id.codeTxt)
//        val selectTxt: TextView = itemView!!.findViewById(R.id.selectTxt)
//        val flagImg: ImageView = itemView!!.findViewById(R.id.flagImg)


        init {

            itemView?.setOnClickListener {

                var categoryModel: CategoryModel? = null
                list?.let {

                    categoryModel = list!![adapterPosition]

                }

                val intent = Intent(activity, NewQuestionActivity::class.java)
                intent.putExtra(Constants.KEY_CAT_ID, categoryModel?.id)
                activity?.startActivity(intent)

            }
        }
    }

}

