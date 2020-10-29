package com.mybus.mybusapp.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mybus.mybusapp.R
import com.mybus.mybusapp.apiHandlers.DataFetcherCallBack
import com.mybus.mybusapp.models.AllDriversModel

class MapDriversAdapter(
    private val activity: Activity,
    objectsList: MutableList<AllDriversModel>?,
    callBack: DataFetcherCallBack
) :
    RecyclerView.Adapter<MapDriversAdapter.ProductsHolder?>() {

    var view: View? = null
    var objectsModelList: MutableList<AllDriversModel>? = objectsList
    val dataFetcherCallBack = callBack


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): MapDriversAdapter.ProductsHolder {

        val view: View =
            LayoutInflater.from(activity).inflate(R.layout.row_map_drivers, viewGroup, false)
        return ProductsHolder(view)
    }


    override fun onBindViewHolder(holder: MapDriversAdapter.ProductsHolder, position: Int) {

        if (objectsModelList != null) {

            val allDriversModel: AllDriversModel = objectsModelList!![position]
            holder.bind(allDriversModel)
        }

    }

    override fun getItemCount(): Int {
        if (objectsModelList != null)
            return objectsModelList!!.size
        else
            return 5
    }

    inner class ProductsHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        val addressTxt: TextView = itemView!!.findViewById(R.id.addressTxt)
        val driverNameTxt: TextView = itemView!!.findViewById(R.id.driverNameTxt)

        fun bind(allDriverModel: AllDriversModel) {

            addressTxt.text = allDriverModel.getAddress()
            driverNameTxt.text=allDriverModel.getFullName()
        }


        init {
            itemView?.setOnClickListener {

                dataFetcherCallBack.Result(adapterPosition, "", true);

            }
        }
    }

    val adapter: MapDriversAdapter
        get() = this

}