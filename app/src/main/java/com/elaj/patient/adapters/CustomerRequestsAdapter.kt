//package com.elaj.patient.adapters
//
//import android.app.Activity
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.ProgressBar
//import android.widget.TextView
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.elaj.patient.apiHandlers.DataFeacher
//import com.elaj.patient.apiHandlers.DataFetcherCallBack
//import com.elaj.patient.classes.OnLoadMoreListener
//import com.elaj.patient.Model.*
//import com.elaj.patient.R
//import com.elaj.patient.Utils.DateHandler
//import com.elaj.patient.Utils.NumberHandler
//import com.elaj.patient.classes.Constants
//import com.bumptech.glide.Glide
//
//class CustomerRequestsAdapter(
//    private val activity: Activity,
//    rv: RecyclerView,
//    objectsList: MutableList<RequestModel?>,
//    allModel: AllRequestsModel?,
//    val type: String
//) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
//
//    var view: View? = null
//    var mOnLoadMoreListener: OnLoadMoreListener? = null
//
//    private val VIEW_TYPE_ITEM = 0
//    private val VIEW_TYPE_LOADING = 1
//
//    private var isLoading = false
//    private val visibleThreshold = 5
//    private var lastVisibleItem = 0
//    private var totalItemCount = 0
//    var nextPage = 2
//
//    var show_loading = false
//    var objectsModelList: MutableList<RequestModel?> = objectsList
//    var allObjectModel: AllRequestsModel?
//
//    override fun onCreateViewHolder(
//        viewGroup: ViewGroup,
//        viewType: Int
//    ): RecyclerView.ViewHolder {
//        if (viewType == VIEW_TYPE_ITEM) {
//            val view: View =
//                LayoutInflater.from(activity)
//                    .inflate(R.layout.row_customer_request, viewGroup, false)
//            return ProductsHolder(view)
//        } else if (viewType == VIEW_TYPE_LOADING) {
//            val view: View =
//                LayoutInflater.from(activity).inflate(R.layout.row_loading, viewGroup, false)
//            return LoadingViewHolder(view)
//        }
//        return ProductsHolder(null)
//    }
//
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder is ProductsHolder) {
//
//
//            val requestsModel: RequestModel? = objectsModelList[position]
//
//            holder.providerNameTxt.text = requestsModel?.salonName
//            holder.serviceNameTxt.text = requestsModel?.servicesNames
//            val price =
//                "${requestsModel?.servicesTotalPrice} ${activity.getString(R.string.currency)}"
//            holder.servicePriceTxt.text = price
////            holder.bookDateTxt.text =
////                DateHandler.FormatDate4(requestsModel?.scheduledDate, "yyyy-MM-dd", "yyy-MM-dd")
//            holder.bookDateTxt.text = requestsModel?.scheduledDate
//            holder.timeTxt.text =
//                NumberHandler.arabicToDecimal(DateHandler.FormatTime(requestsModel?.scheduledTime))
//            holder.statusTxt.text = requestsModel?.statusName
//
//            setStatusColors(holder.statusTxt, requestsModel?.status!!)
//
////            if (requestsModel.status == Constants.ORDER_STATUS_PENDING)
////                holder.deleteBtn.visibility = View.VISIBLE
////            else
////                holder.deleteBtn.visibility = View.GONE
//
//            Glide.with(activity)
//                .asBitmap()
//                .load(requestsModel?.salonAvatar)
//                .placeholder(R.drawable.error_logo)
//                .into(holder.providerAvatarImg)
//
//        } else if (holder is LoadingViewHolder) {
//            holder.progressBar.isIndeterminate = true
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return objectsModelList.size
//    }
//
//    fun setLoaded() {
//        isLoading = false
//    }
//
//    private fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener?) {
//        this.mOnLoadMoreListener = mOnLoadMoreListener
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (objectsModelList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
//    }
//
//    internal inner class LoadingViewHolder(itemView: View) :
//        RecyclerView.ViewHolder(itemView) {
//        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar1)
//
//    }
//
//    internal inner class ProductsHolder(itemView: View?) :
//        RecyclerView.ViewHolder(itemView!!) {
//
//        val rowLY: LinearLayout = itemView!!.findViewById(R.id.rowLY)
//        val providerAvatarImg: ImageView = itemView!!.findViewById(R.id.providerAvatarImg)
//        val providerNameTxt: TextView = itemView!!.findViewById(R.id.providerNameTxt)
//
//        //        val cityLY: LinearLayout = itemView!!.findViewById(R.id.cityLY)
////        val bookLocationTxt: TextView = itemView!!.findViewById(R.id.bookLocationTxt)
//        val statusTxt: TextView = itemView!!.findViewById(R.id.statusTxt)
//        val editBtn: TextView = itemView!!.findViewById(R.id.editBtn)
//        val deleteBtn: TextView = itemView!!.findViewById(R.id.deleteBtn)
//        val timeTxt: TextView = itemView!!.findViewById(R.id.timeTxt)
//        val bookDateTxt: TextView = itemView!!.findViewById(R.id.bookDateTxt)
//
//        //        val serviceImg: ImageView = itemView!!.findViewById(R.id.serviceImg)
//        val serviceNameTxt: TextView = itemView!!.findViewById(R.id.serviceNameTxt)
//        val servicePriceTxt: TextView = itemView!!.findViewById(R.id.servicePriceTxt)
//
//
//        init {
//
//            deleteBtn.setOnClickListener {
//
//            }
//
//            editBtn.setOnClickListener {
//
//            }
//
//            editBtn.visibility = View.GONE
//            deleteBtn.visibility = View.GONE
//
//            itemView?.setOnClickListener {
//                val requestsModel: RequestModel? = objectsModelList[adapterPosition]
//
//                val intent = Intent(activity, RequestDetailsActivity::class.java)
//                intent.putExtra(Constants.KEY_REQUEST_ID, requestsModel?.id)
//                activity.startActivity(intent)
//
//            }
//        }
//    }
//
//    private fun setStatusColors(textView: TextView, status: Int) {
//        when (status) {
//            Constants.ORDER_STATUS_PENDING -> {
//                textView.background =
//                    ContextCompat.getDrawable(activity, R.drawable.round_corner_pending_fill)
//                textView.setTextColor(ContextCompat.getColor(activity, R.color.order_pending_text))
//            }
//            Constants.ORDER_STATUS_ACCEPTED -> {
//                textView.background =
//                    ContextCompat.getDrawable(activity, R.drawable.round_corner_accepted_fill)
//                textView.setTextColor(ContextCompat.getColor(activity, R.color.order_accepted_text))
//            }
//            Constants.ORDER_STATUS_REJECTED -> {
//                textView.background =
//                    ContextCompat.getDrawable(activity, R.drawable.round_corner_rejected_fill)
//                textView.setTextColor(ContextCompat.getColor(activity, R.color.order_rejected_text))
//            }
//            Constants.ORDER_STATUS_COMPLETED -> {
//                textView.background =
//                    ContextCompat.getDrawable(activity, R.drawable.round_corner_completed_fill)
//                textView.setTextColor(
//                    ContextCompat.getColor(
//                        activity,
//                        R.color.order_completed_text
//                    )
//                )
//            }
//
//        }
//    }
//
//    val adapter: CustomerRequestsAdapter
//        get() = this
//
//    private fun setOnloadListener() {
//        adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
//            override fun onLoadMore() {
//
//                //                System.out.println("Log load prev items");
//                objectsModelList.add(null)
//                notifyItemInserted(objectsModelList.size - 1)
//
//                LoadData(nextPage)
//            }
//        })
//    }
//
//    fun showLoadMore() {
//        show_loading = allObjectModel?.nextPageUrl != null
//    }
//
//    /**
//     * to load next page
//     *
//     * @param newPage
//     */
//    fun LoadData(newPage: Int) {
//        val dataFeacher = DataFeacher(object : DataFetcherCallBack {
//            override fun Result(
//                obj: Any?,
//                func: String?,
//                IsSuccess: Boolean
//            ) {
//                if (IsSuccess) {
//                    objectsModelList.removeAt(objectsModelList.size - 1)
//                    notifyItemRemoved(objectsModelList.size)
//                    val pos = objectsModelList.size
//                    val resultAPIModel: ResultAPIModel<AllRequestsModel?> =
//                        obj as ResultAPIModel<AllRequestsModel?>
//
//                    allObjectModel = resultAPIModel.data
//                    val moreData: List<RequestModel> =
//                        allObjectModel?.items!!.toMutableList()
//                    objectsModelList.addAll(moreData)
//                    nextPage++
//                    notifyItemRangeInserted(pos, objectsModelList.size)
//                    //                    notifyDataSetChanged();
//                } else {
//                    objectsModelList.removeAt(objectsModelList.size - 1)
//                    notifyItemRemoved(objectsModelList.size)
//                }
//                setLoaded()
//            }
//        })
//        dataFeacher.getRequests(type, newPage)
//    }
//
//    init {
//        allObjectModel = allModel
//
//        val linearLayoutManager =
//            rv.layoutManager as LinearLayoutManager?
//
//        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(
//                recyclerView: RecyclerView,
//                dx: Int,
//                dy: Int
//            ) {
//                super.onScrolled(recyclerView, dx, dy)
//                totalItemCount = linearLayoutManager!!.itemCount
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
//                showLoadMore()
//                if (show_loading) {
//                    if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
//                        if (mOnLoadMoreListener != null) {
//                            mOnLoadMoreListener?.onLoadMore()
//                        }
//                        isLoading = true
//                    }
//                }
//            }
//        })
//        setOnloadListener()
//    }
//}