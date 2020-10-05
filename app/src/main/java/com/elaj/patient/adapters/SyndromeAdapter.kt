package com.elaj.patient.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elaj.patient.models.SyndromeModel
import com.elaj.patient.R
import com.elaj.patient.apiHandlers.DataFetcherCallBack

class SyndromeAdapter(
    private val activity: Activity?,
    var list: MutableList<SyndromeModel>?,
    var dataFetcherCallBack: DataFetcherCallBack?
) :
    RecyclerView.Adapter<SyndromeAdapter.MyHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_syndrome, null, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        if (list != null) {
            val syndromeModel: SyndromeModel = list!![position]
//        holder.nameTxt.text = countryCodeModel.name
        }

    }

    override fun getItemCount(): Int {
        return list?.size ?: 3
    }

    inner class MyHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {

        val nameTxt: TextView = itemView!!.findViewById(R.id.nameTxt)
        val yesRB: RadioButton = itemView!!.findViewById(R.id.yesRB)
        val noRB: RadioButton = itemView!!.findViewById(R.id.noRB)

        init {

            itemView?.setOnClickListener {


            }
        }
    }

}

