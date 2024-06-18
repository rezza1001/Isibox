package com.rzc.isibox.presentation.orders;

import android.os.Bundle;
import android.view.View;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;

public class CompleteFragment extends MyFragment {

    public static CompleteFragment newInstance() {
        Bundle args = new Bundle();
        CompleteFragment fragment = new CompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.orders_fragment_myorder;
    }

    @Override
    protected void initLayout(View view) {

    }

    @Override
    protected void initListener() {

    }
}
