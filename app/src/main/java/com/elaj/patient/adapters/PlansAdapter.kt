package com.elaj.patient.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elaj.patient.models.SyndromeModel
import com.elaj.patient.R
import com.elaj.patient.apiHandlers.DataFetcherCallBack

class PlansAdapter(
    private val activity: Activity,
    var list: MutableList<SyndromeModel>?,
    var dataFetcherCallBack: DataFetcherCallBack?
) :
    RecyclerView.Adapter<PlansAdapter.MyHolder>() {

    var selectedPos = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_plan, null, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        if (list != null) {
            val syndromeModel: SyndromeModel = list!![position]
//        holder.nameTxt.text = countryCodeModel.name
        }

        if (selectedPos == position) {
            holder.rowLY.background =
                ContextCompat.getDrawable(activity, R.drawable.round_corner_primary_trans)
            holder.checkTV.background =
                ContextCompat.getDrawable(activity, R.drawable.circle_primary_fill)
            holder.checkTV.text = activity.getString(R.string.fal_check)
        } else {
            holder.rowLY.background =
                ContextCompat.getDrawable(activity, R.drawable.round_corner_gray_trans)
            holder.checkTV.background =
                ContextCompat.getDrawable(activity, R.drawable.circle_corner_gray_trans)
            holder.checkTV.text = ""
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 5
    }

    inner class MyHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {

        val rowLY: LinearLayout = itemView!!.findViewById(R.id.rowLY)
        val checkTV: TextView = itemView!!.findViewById(R.id.checkTV)
//        val yesRB: RadioButton = itemView!!.findViewById(R.id.yesRB)
//        val noRB: RadioButton = itemView!!.findViewById(R.id.noRB)

        init {

            itemView?.setOnClickListener {

                if (selectedPos != adapterPosition) {
                    selectedPos = adapterPosition
                    notifyDataSetChanged()

                }

            }
        }
    }

}

