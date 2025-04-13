package com.rzc.isibox.presentation.request;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.request.adapter.QuotesAdapter;
import com.rzc.isibox.presentation.request.model.QuotesModel;
import com.rzc.isibox.presentation.request.vm.RequestViewModel;

import java.util.ArrayList;

public class QuotesFragment extends MyFragment {

    ArrayList<QuotesModel> listQuotes = new ArrayList<>();
    QuotesAdapter adapter;

    RequestViewModel viewModel;


    public static QuotesFragment newInstance() {
        Bundle args = new Bundle();
        QuotesFragment fragment = new QuotesFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.request_fragment_quotes;
    }

    @Override
    protected void initLayout(View view) {
        RecyclerView rcv_data = view.findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(mActivity));

        adapter = new QuotesAdapter(listQuotes);
        rcv_data.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(mActivity).get(RequestViewModel.class);
        viewModel.init(mActivity);

        listQuotes.clear();
        viewModel.loadQuotes().observe(mActivity, quotesModels -> {
            listQuotes.addAll(quotesModels);
            adapter.notifyDataSetChanged();
        });
    }
}
