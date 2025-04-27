package com.rzc.isibox.presentation.request;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.component.EmptyView;
import com.rzc.isibox.presentation.request.adapter.RequestAdapter;
import com.rzc.isibox.presentation.request.model.RequestListModel;
import com.rzc.isibox.presentation.request.vm.RequestViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class RequestFragment extends MyFragment {


    EmptyView empty_view;
    ArrayList<RequestListModel> listRequest = new ArrayList<>();
    ArrayList<RequestListModel> listRequestAll = new ArrayList<>();
    RequestAdapter adapter;

    RequestViewModel viewModel;
    public static RequestFragment newInstance() {
        Bundle args = new Bundle();
        RequestFragment fragment = new RequestFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.request_fragment_request;
    }

    @Override
    protected void initLayout(View view) {
        RecyclerView rcv_data = view.findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(mActivity));

        empty_view = view.findViewById(R.id.empty_view);
        empty_view.create("Anda belum membuat pesanan. Silahkan buat Request atau Kirim Penawaran untuk bisa mengakses halaman Pesanan");

        adapter = new RequestAdapter(listRequest);
        rcv_data.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        adapter.setOnActionListener(data -> {
            Intent intent = new Intent(mActivity, DetailMyRequestActivity.class);
            intent.putExtra(Global.DATA,data);//Ganti sama nama pembuat request
            startActivity(intent);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initData() {
        mRegisterReceiver(receiver, new IntentFilter(VariableStatic.BROADCAST_SEARCH));

        viewModel = new ViewModelProvider(mActivity).get(RequestViewModel.class);
        viewModel.init(mActivity);
        listRequestAll.clear();
        viewModel.loadRequest().observe(mActivity, requestModels -> {
            listRequestAll.addAll(requestModels);
            filter("");
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filter(String message){
        listRequest.clear();
        if (message.isEmpty()){
            listRequest.addAll(listRequestAll);
        }
        else {
            message = message.toLowerCase();
            for (RequestListModel model : listRequestAll){
                String key = model.getName() + model.getCategory() + model.getReqDate();
                key = key.toLowerCase();

                if (key.contains(message)) {
                    listRequest.add(model);
                }
            }
        }

        adapter.notifyDataSetChanged();
        if (listRequest.isEmpty()){
            empty_view.show();
        }
        else {
            empty_view.hide();
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), VariableStatic.BROADCAST_SEARCH)){
                filter(Objects.requireNonNull(intent.getStringExtra(Global.DATA)));
            }
        }
    };
}
