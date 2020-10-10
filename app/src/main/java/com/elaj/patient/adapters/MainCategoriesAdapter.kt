package com.elaj.patient.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elaj.patient.R
import com.elaj.patient.models.CategoryModel
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

        if (list != null) {
            holder.bind(list!![position])
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class MyHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {

        private val nameTV: TextView = itemView!!.findViewById(R.id.nameTV)
        private val categoryImg: ImageView = itemView!!.findViewById(R.id.categoryImg)

        fun bind(categoryModel: CategoryModel) {

            nameTV.text = categoryModel.name

            Glide.with(activity!!)
                .asBitmap()
                .load(categoryModel.photo)
                .placeholder(R.drawable.error_logo)
                .into(categoryImg)
        }

        init {

            itemView?.setOnClickListener {

                var categoryModel: CategoryModel? = null
                list?.let {

                    categoryModel = list!![adapterPosition]

                }

                val intent = Intent(activity, NewQuestionActivity::class.java)
                intent.putExtra(Constants.KEY_CAT_ID, categoryModel?.id)
                intent.putExtra(Constants.KEY_CAT_NAME, categoryModel?.name)
                activity?.startActivity(intent)

            }
        }
    }

}

