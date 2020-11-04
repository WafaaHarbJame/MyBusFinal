package com.mybus.mybusapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.mybus.mybusapp.R;
import com.mybus.mybusapp.models.PayWayImage;

import java.util.List;

public class PayWayAdapter extends RecyclerView.Adapter<PayWayAdapter.MyHolder> {

    public interface IClickListener{
        void onItemClick(int position, PayWayImage payWayImage);
    }

    IClickListener iClickListener;
    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    MyHolder holder;
    String sessionType;
    private final List<PayWayImage> imageViews;
    final Context context;
    private LayoutInflater inflater;
    private int lastIndex = 0;
    public int selectedIndex = -1;

    public PayWayAdapter(Context context, List<PayWayImage> imageViews){
        this.imageViews = imageViews;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public PayWayAdapter(Context context, List<PayWayImage> imageViews, String sessionType){
        this.imageViews = imageViews;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.sessionType = sessionType;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pay_way_image_layout,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        this.holder = holder;

        holder.img.setImageResource(imageViews.get(position).getImgResource());
        if (lastIndex == position) {
            holder.cardViewBillDetails.setBackground(ContextCompat.getDrawable(context,R.drawable.round_blue_small));

        } else {
            holder.cardViewBillDetails.setBackground(ContextCompat.getDrawable(context,R.drawable.round_white_small));

        }


        holder.cardViewBillDetails.setOnClickListener(view -> {
            selectedIndex = position;
            lastIndex = position;
            notifyDataSetChanged();
            if(iClickListener !=null){
                iClickListener.onItemClick(position,imageViews.get(position));
            }
        });
    }
    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

    @Override
    public int getItemCount() {
        return imageViews.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder  {
        ImageView img;
       LinearLayout cardViewBillDetails;

        public MyHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.payWayImage);
            cardViewBillDetails =itemView.findViewById(R.id.cardViewBillDetails);
            this.setIsRecyclable(false);
        }


    }


}
