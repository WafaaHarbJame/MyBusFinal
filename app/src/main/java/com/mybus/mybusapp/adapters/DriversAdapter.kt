package com.mybus.mybusapp.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mybus.mybusapp.R
import com.mybus.mybusapp.activities.AddDriverActivity
import com.mybus.mybusapp.classes.Constants
import com.mybus.mybusapp.models.DriverModel

class DriversAdapter(
    private val activity: Activity,
    var list: MutableList<DriverModel?>?
) :
    RecyclerView.Adapter<DriversAdapter.MyHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_driver, null, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

//        val countryCodeModel: CountryModel = list[position]

        if (list != null) {
            holder.bind(list?.get(position)!!)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class MyHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {

        private val nameTV: TextView = itemView!!.findViewById(R.id.nameTV)
        private val ageTV: TextView = itemView!!.findViewById(R.id.ageTV)
        private val mobileTV: TextView = itemView!!.findViewById(R.id.mobileTV)
        private val busNumTV: TextView = itemView!!.findViewById(R.id.busNumTV)
        private val busModelTV: TextView = itemView!!.findViewById(R.id.busModelTV)

        fun bind(driverModel: DriverModel) {

            nameTV.text = driverModel.fullName
            ageTV.text = driverModel.age.toString().plus(" " + activity.getString(R.string.years))
            mobileTV.text = driverModel.mobileWithCountry
            busNumTV.text = driverModel.busNumber.toString()
            busModelTV.text = driverModel.busModel

        }

        init {

            itemView?.setOnClickListener {

                var driverModel: DriverModel?
                list?.let {

                    driverModel = list!![adapterPosition]

                    val intent = Intent(activity, AddDriverActivity::class.java)
                    intent.putExtra(Constants.KEY_DRIVER_MODEL, driverModel)
                    activity.startActivity(intent)

                }


            }

        }
    }

}

