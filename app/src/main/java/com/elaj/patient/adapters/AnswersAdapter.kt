package com.elaj.patient.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elaj.patient.models.SyndromeModel
import com.elaj.patient.R

class AnswersAdapter(
    private val activity: Activity?,
    var list: MutableList<SyndromeModel>?
) :
    RecyclerView.Adapter<AnswersAdapter.MyHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_answer, null, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        if (list != null) {
            val syndromeModel: SyndromeModel = list!![position]
//        holder.nameTxt.text = countryCodeModel.name
        }

        holder.rowLY.setBackgroundColor(
            ContextCompat.getColor(
                activity!!,
                if (position == 0) R.color.colorPrimaryLight else R.color.white
            )
        )

    }

    override fun getItemCount(): Int {
        return list?.size ?: 2
    }

    inner class MyHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {

        val rowLY: RelativeLayout = itemView!!.findViewById(R.id.rowLY)

        init {


        }
    }

}

