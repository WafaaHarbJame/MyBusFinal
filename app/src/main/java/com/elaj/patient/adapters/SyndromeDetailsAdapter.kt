package com.elaj.patient.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elaj.patient.Model.CountryModel
import com.elaj.patient.Model.SyndromeModel
import com.elaj.patient.R
import com.elaj.patient.apiHandlers.DataFetcherCallBack

class SyndromeDetailsAdapter(
    private val activity: Activity?,
    var list: MutableList<SyndromeModel>?
) :
    RecyclerView.Adapter<SyndromeDetailsAdapter.MyHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_syndrome_details, null, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        if (list != null) {
            val syndromeModel: SyndromeModel = list!![position]
//        holder.nameTxt.text = countryCodeModel.name
        }

    }

    override fun getItemCount(): Int {
        return list?.size ?: 2
    }

    inner class MyHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {

        val nameTxt: TextView = itemView!!.findViewById(R.id.nameTxt)

        init {

            itemView?.setOnClickListener {


            }
        }
    }

}

