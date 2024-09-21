package com.rzc.isibox.presentation.orders;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.component.EmptyView;
import com.rzc.isibox.presentation.component.option.OptionData;

import java.util.ArrayList;

public class MyOrderFragment extends MyFragment {

    EmptyView empty_view;
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

        empty_view = view.findViewById(R.id.empty_view);
        empty_view.create("Anda belum membuat pesanan. Silahkan buat Reqyest atau Kirim Penawaran untuk bisa mengakses halaman Pesanan. Saya");
    }

    @Override
    protected void initListener() {
        adapter.setListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void CancelListener(OrderModel model) {
                OrderCancelDialog dialog = new OrderCancelDialog(mActivity);
                ArrayList<OptionData> listOption = new ArrayList<>();
                listOption.add(new OptionData("Stock Habis"));
                listOption.add(new OptionData("Penjual belum menerima uang pembayaran"));
                listOption.add(new OptionData("Pengiriman Terlambat"));
                listOption.add(new OptionData("Alasan tidak bisa disebutkan"));

                dialog.show();
                dialog.setOptionData(listOption);
                Log.d("","");
            }

            @Override
            public void AcceptListener(OrderModel model) {
                Log.d("","");
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(mActivity).get(OrderViewModel.class);
        viewModel.init(mActivity);

        viewModel.loadMyOrder().observe(mActivity, orderModels -> {
            listOrders.clear();
            for(OrderModel order : orderModels){
                if(order.getStatusAction() != 4){
                    listOrders.add(order);
                }
            }
//            listOrders.addAll(orderModels);
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
