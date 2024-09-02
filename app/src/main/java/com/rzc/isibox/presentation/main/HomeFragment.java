package com.rzc.isibox.presentation.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.request.DetailRequestActivity;
import com.rzc.isibox.presentation.request.RequestFormActivity;

import java.util.ArrayList;

public class HomeFragment extends MyFragment {

    private MyButton btn_request;
    private ArrayList<MainModel> listProduct = new ArrayList<>();
    MainAdapter adapter;

    MainViewModel viewModel;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.main_fragment_home;
    }

    @Override
    protected void initLayout(View view) {
        btn_request = view.findViewById(R.id.btn_request);
        btn_request.create(MyButton.TYPE.PRIMARY,getResources().getString(R.string.create_request));
        btn_request.setCardCfg(1,20);

        RecyclerView rcv_data = view.findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new MainAdapter(listProduct);
        rcv_data.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        btn_request.setOnMyClickListener(view -> startActivity(new Intent(mActivity, RequestFormActivity.class)));

        adapter.setOnSelectedListener(data -> {
            Intent intent = new Intent(mActivity, DetailRequestActivity.class);
            intent.putExtra(VariableStatic.DATA, data);
            startActivity(intent);
        });

    }

    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(mActivity).get(MainViewModel.class);
        viewModel.init(mActivity);
        loadData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData(){
        viewModel.loadProduct().observe(mActivity, mainModels -> {
            listProduct.clear();
            listProduct.addAll(mainModels);
            adapter.notifyDataSetChanged();
        });
    }
}
