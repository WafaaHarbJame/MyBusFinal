package com.elaj.patient.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elaj.patient.Model.AllRequestsModel
import com.elaj.patient.Model.RequestModel
import com.elaj.patient.Model.ResultAPIModel
import com.elaj.patient.R
import com.elaj.patient.apiHandlers.DataFeacher
import com.elaj.patient.apiHandlers.DataFetcherCallBack
import com.elaj.patient.classes.OnLoadMoreListener

class RequestsAdapter(
    private val activity: Activity,
    rv: RecyclerView,
    var objectsList: MutableList<RequestModel?>?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    var view: View? = null
    var mOnLoadMoreListener: OnLoadMoreListener? = null

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    private var isLoading = false
    private val visibleThreshold = 5
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    var nextPage = 2

    var show_loading = false
//    var objectsModelList: MutableList<RequestModel?> = objectsList
//    var allObjectModel: AllRequestsModel?

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val view: View =
                LayoutInflater.from(activity)
                    .inflate(R.layout.row_request, viewGroup, false)
            return ProductsHolder(view)
        } else if (viewType == VIEW_TYPE_LOADING) {
            val view: View =
                LayoutInflater.from(activity).inflate(R.layout.row_loading, viewGroup, false)
            return LoadingViewHolder(view)
        }
        return ProductsHolder(null)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductsHolder) {


//            val requestsModel: RequestModel? = objectsModelList[position]
//
//            holder.providerNameTxt.text = requestsModel?.salonName
//            holder.serviceNameTxt.text = requestsModel?.servicesNames
//            val price =
//                "${requestsModel?.servicesTotalPrice} ${activity.getString(R.string.currency)}"
//            holder.servicePriceTxt.text = price

//            holder.bookDateTxt.text = requestsModel?.scheduledDate
//            holder.timeTxt.text =
//                NumberHandler.arabicToDecimal(DateHandler.FormatTime(requestsModel?.scheduledTime))
//            holder.statusTxt.text = requestsModel?.statusName
//
//            setStatusColors(holder.statusTxt, requestsModel?.status!!)
//
//            Glide.with(activity)
//                .asBitmap()
//                .load(requestsModel?.salonAvatar)
//                .placeholder(R.drawable.error_logo)
//                .into(holder.providerAvatarImg)

        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemCount(): Int {
        return objectsList?.size ?: 12
    }

    fun setLoaded() {
        isLoading = false
    }

    private fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener?) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    override fun getItemViewType(position: Int): Int {
        return if (objectsList != null)
            if (objectsList!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
        else
            VIEW_TYPE_ITEM
    }

    internal inner class LoadingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar1)

    }

    internal inner class ProductsHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {

//        val rowLY: LinearLayout = itemView!!.findViewById(R.id.rowLY)
//        val providerAvatarImg: ImageView = itemView!!.findViewById(R.id.providerAvatarImg)
//        val providerNameTxt: TextView = itemView!!.findViewById(R.id.providerNameTxt)

        init {

            itemView?.setOnClickListener {
//                val requestsModel: RequestModel? = objectsModelList[adapterPosition]
//
//                val intent = Intent(activity, RequestDetailsActivity::class.java)
//                intent.putExtra(Constants.KEY_REQUEST_ID, requestsModel?.id)
//                activity.startActivity(intent)

            }
        }
    }

    val adapter: RequestsAdapter
        get() = this

    private fun setOnloadListener() {
        adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {

                //                System.out.println("Log load prev items");
                objectsList?.add(null)
                notifyItemInserted(objectsList?.size!! - 1)

                LoadData(nextPage)
            }
        })
    }

    fun showLoadMore() {
//        show_loading = objectsList?.nextPageUrl != null
        show_loading = false
    }

    /**
     * to load next page
     *
     * @param newPage
     */
    fun LoadData(newPage: Int) {
        val dataFeacher = DataFeacher(object : DataFetcherCallBack {
            override fun Result(
                obj: Any?,
                func: String?,
                IsSuccess: Boolean
            ) {
                if (IsSuccess) {
                    objectsList?.removeAt(objectsList?.size!! - 1)
                    notifyItemRemoved(objectsList?.size!!)
                    val pos = objectsList?.size!!
                    val resultAPIModel: ResultAPIModel<AllRequestsModel?> =
                        obj as ResultAPIModel<AllRequestsModel?>

//                    allObjectModel = resultAPIModel.data
//                    val moreData: List<RequestModel> =
//                        objectsList?.items!!.toMutableList()
//                    objectsList.addAll(moreData)
                    nextPage++
                    notifyItemRangeInserted(pos, objectsList?.size!!)
                    //                    notifyDataSetChanged();
                } else {
                    objectsList?.removeAt(objectsList?.size!! - 1)
                    notifyItemRemoved(objectsList?.size!!)
                }
                setLoaded()
            }
        })
//        dataFeacher.getRequests(type, newPage)
    }

    init {
//        allObjectModel = allObjectModel

        val linearLayoutManager =
            rv.layoutManager as LinearLayoutManager?

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager!!.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                showLoadMore()
                if (show_loading) {
                    if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener?.onLoadMore()
                        }
                        isLoading = true
                    }
                }
            }
        })
        setOnloadListener()
    }
}