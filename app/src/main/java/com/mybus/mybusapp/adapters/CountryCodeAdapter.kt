package com.mybus.mybusapp.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mybus.mybusapp.models.CountryModel
import com.mybus.mybusapp.R
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.bumptech.glide.Glide

class CountryCodeAdapter(
    private val activity: Activity?,
    var list: MutableList<CountryModel>,
    var selectedCountry: CountryModel?,
    var dataFetcherCallBack: DataFetcherCallBack?
) :
    RecyclerView.Adapter<CountryCodeAdapter.MyHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_country_code, null, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val countryCodeModel: CountryModel = list[position]

        holder.nameTxt.text = countryCodeModel.name

        val code = "+" + countryCodeModel.code
        holder.codeTxt.text = code

        Glide.with(activity!!)
            .asBitmap()
            .load(countryCodeModel.flag)
            .placeholder(R.drawable.error_logo)
            .into(holder.flagImg)

        if (selectedCountry?.id == countryCodeModel.id) {
            holder.selectTxt.text = activity.getString(R.string.fa_circle)
            holder.selectTxt.setTextColor(
                ContextCompat.getColor(
                    activity,
                    R.color.colorPrimaryDark
                )
            )
        } else {
            holder.selectTxt.text = activity.getString(R.string.fa_circle_o)
            holder.selectTxt.setTextColor(ContextCompat.getColor(activity, R.color.header3))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {

        val nameTxt: TextView = itemView!!.findViewById(R.id.nameTxt)
        val codeTxt: TextView = itemView!!.findViewById(R.id.codeTxt)
        val selectTxt: TextView = itemView!!.findViewById(R.id.selectTxt)
        val flagImg: ImageView = itemView!!.findViewById(R.id.flagImg)


        init {

            itemView?.setOnClickListener {

                val countryCodeModel = list[adapterPosition]

                if (selectedCountry !== countryCodeModel) {
                    selectedCountry = countryCodeModel
                    notifyDataSetChanged()
                    if (dataFetcherCallBack != null) dataFetcherCallBack!!.Result(
                        countryCodeModel,
                        adapterPosition.toString(),
                        true
                    )
                }

            }
        }
    }

}

