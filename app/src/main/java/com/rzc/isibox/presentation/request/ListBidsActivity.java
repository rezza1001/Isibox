/*
 * Copyright (c) 2025. Rezza Developer
 */

package com.rzc.isibox.presentation.request;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.MySearchView;
import com.rzc.isibox.presentation.quots.model.BidsUsersModel;
import com.rzc.isibox.presentation.request.adapter.BidAdapter;
import com.rzc.isibox.presentation.request.vm.BidsViewModel;

import java.util.ArrayList;

public class ListBidsActivity extends MyActivity {

    MySearchView search_view;
    ImageView iv_search;
    TextView tv_total;

    BidsViewModel viewModel;
    ArrayList<BidsUsersModel> listBids = new ArrayList<>();
    ArrayList<BidsUsersModel> listBidsFilter = new ArrayList<>();
    BidAdapter adapter;


    @Override
    protected int setLayout() {
        return R.layout.request_activity_list_bids;
    }

    @Override
    protected void initLayout() {
        search_view = findViewById(R.id.search_view);
        search_view.create("Cari data");

        iv_search = findViewById(R.id.iv_search);
        tv_total = findViewById(R.id.tv_total);

        adapter = new BidAdapter(listBidsFilter);
        RecyclerView rcv_data = findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(mActivity));
        rcv_data.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        findViewById(R.id.iv_back).setOnClickListener(v -> mActivity.finish());
        search_view.setOnActionListener(new MySearchView.OnActionListener() {
            @Override
            public void onType(String searchKey) {
                search(searchKey);
            }

            @Override
            public void onShow() {

            }

            @Override
            public void onHide() {

            }
        });

        iv_search.setOnClickListener(v -> search_view.showSearch());
        adapter.setOnActionListener(data -> {

        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initialData() {
        viewModel = new ViewModelProvider(mActivity).get(BidsViewModel.class);
        viewModel.init(mActivity);
        viewModel.loadAll(getIntent().getStringExtra(Global.DATA)).observe(mActivity, bidsUsersModels -> {
            listBids.clear();
            listBids.addAll(bidsUsersModels);
            search("");
            tv_total.setText("Total Bids ("+ bidsUsersModels.size()+")");
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void search(String value){
        listBidsFilter.clear();
        if (value.isEmpty()){
            listBidsFilter.addAll(listBids);
        }
        else {
            for (BidsUsersModel model : listBids){
                String key = model.getName();
                key = key.toLowerCase();
                if (key.contains(value.toLowerCase())){
                    listBidsFilter.add(model);
                }
            }

        }
        adapter.notifyDataSetChanged();
    }
}
