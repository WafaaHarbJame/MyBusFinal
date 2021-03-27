package com.mybus.mybusapp.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mybus.mybusapp.R
import com.mybus.mybusapp.activities.*
import com.mybus.mybusapp.models.MainAdminModel

class MainAdminAdapter(
    private val activity: Activity?,
    var list: MutableList<MainAdminModel>?
) :
    RecyclerView.Adapter<MainAdminAdapter.MyHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_main_admin, null, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

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

        fun bind(mainAdminModel: MainAdminModel) {

            nameTV.text = mainAdminModel.name
        }

        init {

            itemView?.setOnClickListener {

                var mainAdminModel: MainAdminModel? = null
                list?.let {

                    mainAdminModel = list!![adapterPosition]

                    when (mainAdminModel!!.id) {
                        MainAdminModel.MANAGE_DRIVERS -> {
//                            val intent = Intent(activity, DriversActivity::class.java)
//                            activity?.startActivity(intent)
                        }
                    }


                }


            }
        }
    }

}

