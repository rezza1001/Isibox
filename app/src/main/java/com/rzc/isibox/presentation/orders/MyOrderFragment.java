package com.rzc.isibox.presentation.orders;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;

import java.util.ArrayList;

public class MyOrderFragment extends MyFragment {

    ArrayList<OrderModel> listOrders = new ArrayList<>();
    OrderAdapter adapter;

    OrderViewModel viewModel;
    public static MyOrderFragment newInstance() {
        Bundle args = new Bundle();
        MyOrderFragment fragment = new MyOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.orders_fragment_myorder;
    }

    @Override
    protected void initLayout(View view) {

        RecyclerView rcv_data = view.findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new OrderAdapter(listOrders);
        rcv_data.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(mActivity).get(OrderViewModel.class);
        viewModel.init(mActivity);

        viewModel.loadMyOrder().observe(mActivity, orderModels -> {
            listOrders.clear();
            listOrders.addAll(orderModels);
            adapter.notifyDataSetChanged();
        });
    }
}
