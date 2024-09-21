package com.rzc.isibox.presentation.orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyDialog;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.presentation.component.option.OptionData;
import com.rzc.isibox.presentation.component.option.OptionDialog;

import java.util.ArrayList;

public class OrderCancelDialog extends MyDialog {
    RecyclerView rcv_option;
    OrderCancelAdapter adapter;
    ArrayList<OptionData> listData = new ArrayList<>();
    MyRelativeLayout rv_action;
    public OrderCancelDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_cancel_order;
    }

    @Override
    protected void initLayout(View view) {
        view.findViewById(R.id.iv_close).setOnClickListener(view1 -> dismiss());
        view.findViewById(R.id.rvly_root).setOnClickListener(view1 -> dismiss());

        rcv_option = view.findViewById(R.id.rcv_option);
        rv_action = view.findViewById(R.id.rv_action);
        rcv_option.setLayoutManager(new LinearLayoutManager(mActivity));


//        adapter = new OrderCancelAdapter(listData);
//        rcv_option.setAdapter(adapter);

    }

    public void show() {
        super.show();
        listData = new ArrayList<>();
        adapter = new OrderCancelAdapter(listData);
        rcv_option.setAdapter(adapter);

        adapter.setListener(new OrderCancelAdapter.OnElementClick() {
            @Override
            public void OnElementClickListener( Integer posisi) {
                adapter.notifyItemChanged(posisi);
                for(OptionData data : listData){
                    Log.d("ITEM CAOK",data.getValue() +" ubah "+ data.isSelected());
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setOptionData(ArrayList<OptionData> data){
        listData.clear();
        listData.addAll(data);
        adapter.notifyDataSetChanged();
    }




}
