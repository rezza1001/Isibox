package com.rzc.isibox.presentation.orders;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.component.EmptyView;

import java.util.ArrayList;

public class CompleteFragment extends MyFragment {

    EmptyView empty_view;
    ArrayList<OrderModel> listOrders = new ArrayList<>();
    CompleteAdapter adapter;

    RecyclerView rcv_data;

    OrderViewModel viewModel;
    public static CompleteFragment newInstance() {
        Bundle args = new Bundle();
        CompleteFragment fragment = new CompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.orders_fragment_myorder;
    }

    @Override
    protected void initLayout(View view) {
        empty_view = view.findViewById(R.id.empty_view);
        empty_view.create("Anda belum membuat pesanan. Silahkan buat Reqyest atau Kirim Penawaran untuk bisa mengakses halaman Pesanan. Saya");
        rcv_data = view.findViewById(R.id.rcv_data);

        rcv_data.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new CompleteAdapter(listOrders);
        rcv_data.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        viewModel = new ViewModelProvider(mActivity).get(OrderViewModel.class);
        viewModel.init(mActivity);

        viewModel.loadMyOrder().observe(mActivity, orderModels -> {
            listOrders.clear();
            for(OrderModel order : orderModels){
                if(order.getStatusAction() == 4){
                    listOrders.add(order);
                }
            }
            adapter.notifyDataSetChanged();

            if (listOrders.size() == 0){
                empty_view.show();
            }
            else {
                empty_view.hide();
            }
        });
    }
}
