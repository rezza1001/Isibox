package com.rzc.isibox.presentation.quots;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.component.EmptyView;
import com.rzc.isibox.presentation.quots.adapter.QuotesAdapter;
import com.rzc.isibox.presentation.quots.model.QuotesModel;
import com.rzc.isibox.presentation.request.DetailRequestActivity;

import java.util.ArrayList;

public class QuotFragment extends MyFragment {

    EmptyView empty_view;
    ArrayList<QuotesModel> listOrders = new ArrayList<>();
    QuotesAdapter adapter;

    QuotViewModel viewModel;
    public static QuotFragment newInstance() {
        Bundle args = new Bundle();
        QuotFragment fragment = new QuotFragment();
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
        adapter = new QuotesAdapter(listOrders);
        rcv_data.setAdapter(adapter);

        empty_view = view.findViewById(R.id.empty_view);
        empty_view.create("Anda belum membuat pesanan. Silahkan buat Reqyest atau Kirim Penawaran untuk bisa mengakses halaman Pesanan. Saya");
    }

    @Override
    protected void initListener() {
        adapter.setOnActionListener(model -> {
            Intent intent = new Intent(mActivity, DetailRequestActivity.class);
            intent.putExtra(Global.DATA,model.getId());
            startActivity(intent);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel == null){
            return;
        }
        refreshData();
    }


    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(mActivity).get(QuotViewModel.class);
        viewModel.init(mActivity);
        refreshData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void refreshData(){
        viewModel.loadQuotes().observe(mActivity, orderModels -> {
            listOrders.clear();
            listOrders.addAll(orderModels);
            adapter.notifyDataSetChanged();

            if (listOrders.isEmpty()){
                empty_view.show();
            }
            else {
                empty_view.hide();
            }
        });
    }
}
