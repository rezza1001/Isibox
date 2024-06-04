package com.rzc.isibox.presentation.orders;

import android.os.Bundle;
import android.view.View;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;

public class OrdersFragment extends MyFragment {

    public static OrdersFragment newInstance() {
        Bundle args = new Bundle();
        OrdersFragment fragment = new OrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.orders_fragment_main;
    }

    @Override
    protected void initLayout(View view) {

    }

    @Override
    protected void initListener() {

    }
}
