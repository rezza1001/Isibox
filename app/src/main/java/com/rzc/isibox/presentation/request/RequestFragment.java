package com.rzc.isibox.presentation.request;

import android.os.Bundle;
import android.view.View;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;

public class RequestFragment extends MyFragment {

    public static RequestFragment newInstance() {
        Bundle args = new Bundle();
        RequestFragment fragment = new RequestFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.request_fragment_main;
    }

    @Override
    protected void initLayout(View view) {

    }

    @Override
    protected void initListener() {

    }
}
