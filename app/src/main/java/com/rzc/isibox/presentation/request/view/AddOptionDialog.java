package com.rzc.isibox.presentation.request.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyDialog;
import com.rzc.isibox.presentation.account.model.CustomerAddressModel;
import com.rzc.isibox.presentation.component.MySearchView;
import com.rzc.isibox.presentation.request.adapter.AddressAdapter;

import java.util.ArrayList;

public class AddOptionDialog extends MyDialog {

    MySearchView search_view;
    AddressAdapter adapter;
    RecyclerView rcv_data;
    RelativeLayout rvl_root;
    ArrayList<CustomerAddressModel> list = new ArrayList<>();

    public AddOptionDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.transaction_dialog_address;
    }

    @Override
    protected void initLayout(View view) {
        search_view = view.findViewById(R.id.search_view);
        search_view.create("Cari Alamat..");


        rcv_data = findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(mActivity));

        rvl_root = findViewById(R.id.rvl_root);
        rvl_root.setVisibility(View.INVISIBLE);
        findViewById(R.id.iv_search).setOnClickListener(v -> {
            search_view.showSearch();
        });

        search_view.setOnActionListener(new MySearchView.OnActionListener() {
            @Override
            public void onType(String searchKey) {

            }

            @Override
            public void onShow() {

            }

            @Override
            public void onHide() {

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void show(ArrayList<CustomerAddressModel> list, OnActionListener onActionListener) {
        super.show();

        rvl_root.setVisibility(View.VISIBLE);
        rvl_root.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.zoom_in));

        adapter = new AddressAdapter(this.list);
        rcv_data.setAdapter(adapter);

        this.list.clear();
        this.list.addAll(list);

        adapter.notifyDataSetChanged();
        this.onActionListener = onActionListener;
        adapter.setOnActionListener(data -> {
            if (this.onActionListener != null){
                this.onActionListener.onAction(data);
                dismiss();
            }
        });
    }

    private OnActionListener onActionListener;


    public interface OnActionListener{
        void onAction(CustomerAddressModel model);
    }
}
