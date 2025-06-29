/*
 * Copyright (c) 2025. Rezza Developer
 */

package com.rzc.isibox.presentation.request.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.master.MyView;
import com.rzc.isibox.presentation.quots.QuotViewModel;
import com.rzc.isibox.presentation.quots.model.BidsUsersModel;
import com.rzc.isibox.presentation.request.ChooseRequestDialog;
import com.rzc.isibox.presentation.request.ListBidsActivity;
import com.rzc.isibox.presentation.request.adapter.BidAdapter;

import java.util.ArrayList;

public class ListQuotsView extends MyView {

    TextView tv_emptyBids, tv_qtyQuot, tv_viewAll;
    RecyclerView rcv_data;
    LinearLayout ln_lsitQuot;
    QuotViewModel viewModel;
    String requestId;
    ArrayList<BidsUsersModel> listBids = new ArrayList<>();
    BidAdapter adapter;

    public ListQuotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.request_view_listqots;
    }

    @Override
    protected void initLayout() {
        tv_qtyQuot = findViewById(R.id.tv_qtyQuot);
        tv_emptyBids = findViewById(R.id.tv_emptyBids);
        rcv_data = findViewById(R.id.rcv_data);
        ln_lsitQuot = findViewById(R.id.ln_lsitQuot);
        tv_viewAll = findViewById(R.id.tv_viewAll);
    }

    @Override
    protected void initListener() {
        tv_viewAll.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, ListBidsActivity.class);
            intent.putExtra(Global.DATA, requestId);
            mActivity.startActivity(intent);
        });
    }

    public void create(String requestId) {
        super.create();
        this.viewModel = new ViewModelProvider(mActivity).get(QuotViewModel.class);
        viewModel.init(mActivity);

        this.requestId = requestId;
        tv_emptyBids.setVisibility(INVISIBLE);
        ln_lsitQuot.setVisibility(INVISIBLE);
        rcv_data.setLayoutManager(new LinearLayoutManager(mActivity));

        listBids = new ArrayList<>();
        adapter = new BidAdapter(listBids);
        rcv_data.setAdapter(adapter);

        adapter.setOnActionListener(data -> {
            loadUserBid(data.getBidID());
        });

        loadData();
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void loadData(){
        Log.d("ListQuots","Load Data");
        viewModel.loadBids(requestId).observe(mActivity, bidsUsersModels -> {
            if (bidsUsersModels.isEmpty()){
                tv_emptyBids.setVisibility(VISIBLE);
                ln_lsitQuot.setVisibility(GONE);
                return;
            }

            if (bidsUsersModels.size() > 3){
                for (int i=0; i<3; i++){
                    listBids.add(bidsUsersModels.get(i));
                }
            }
            else {
                listBids.addAll(bidsUsersModels);
            }

            tv_emptyBids.setVisibility(GONE);
            ln_lsitQuot.setVisibility(VISIBLE);
            tv_qtyQuot.setText("Penawaran ("+bidsUsersModels.size()+")");
            adapter.notifyDataSetChanged();
        });
    }

    private void loadUserBid(int id){
        viewModel.loadUserBid(id).observe(mActivity, userBidding -> {
            ChooseRequestDialog dialog = new ChooseRequestDialog(mActivity);
            dialog.show(userBidding);
        });
    }
}
