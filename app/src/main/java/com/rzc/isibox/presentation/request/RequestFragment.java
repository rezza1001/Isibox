package com.rzc.isibox.presentation.request;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;

import java.util.ArrayList;

public class RequestFragment extends MyFragment {


    ArrayList<RequestModel> listRequest = new ArrayList<>();
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

        adapter = new RequestAdapter(listRequest);
        rcv_data.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        adapter.setOnActionListener(data -> {
            Intent intent = new Intent(mActivity, DetailRequestActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(mActivity).get(RequestViewModel.class);
        viewModel.init(mActivity);
        listRequest.clear();
        viewModel.loadRequest().observe(mActivity, requestModels -> {
            listRequest.addAll(requestModels);
            adapter.notifyDataSetChanged();
        });
    }
}
