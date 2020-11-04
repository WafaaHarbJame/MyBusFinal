package com.mybus.mybusapp.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mybus.mybusapp.R;
import com.mybus.mybusapp.adapters.PayWayAdapter;
import com.mybus.mybusapp.classes.DataCallback;
import com.mybus.mybusapp.models.PayWayImage;

import java.util.ArrayList;
import java.util.List;

public class PayWayBottomDialog extends BottomSheetDialogFragment {

    View viewDialog;
    ImageButton dialogButtonCancel;
    RecyclerView recyclerView;
    PayWayAdapter adapter;
    List<PayWayImage> list;
    String selectedType="";
    DataCallback dataCallback;
    TextView SaveBut;

    public PayWayBottomDialog(DataCallback callback) {
        this.dataCallback=callback;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        viewDialog = View.inflate(getContext(), R.layout.pay_way_layout, null);
        dialog.setContentView(viewDialog);

        dialogButtonCancel =  viewDialog.findViewById(R.id.buttonCancel);
        dialogButtonCancel.setOnClickListener(v -> dismiss());
        recyclerView = viewDialog.findViewById(R.id.rvPayWay);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        SaveBut = viewDialog.findViewById(R.id.SaveBut);

        list = new ArrayList<>();
        list.add(new PayWayImage(R.drawable.visa_card,"visa"));
        list.add(new PayWayImage(R.drawable.mada,"mada"));
        list.add(new PayWayImage(R.drawable.sadaapayment,"sadaa"));

        adapter = new PayWayAdapter(getContext(), list);

        SaveBut.setOnClickListener(view -> {
            if(dataCallback!=null){
                Log.e("selectedType SaveBut",selectedType);
                PayWayImage payWayImage=new PayWayImage(0,selectedType);
                dataCallback.dataResult(payWayImage,"payWayImage",true);
            }

            dismiss();

        });
        adapter.setiClickListener((position, payWayImage) -> {
            selectedType=list.get(position).getPayName();
            Log.e("selectedType click",selectedType);

        });
        recyclerView.setAdapter(adapter);

    }

}
