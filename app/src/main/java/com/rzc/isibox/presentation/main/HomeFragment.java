package com.rzc.isibox.presentation.main;

import android.os.Bundle;
import android.view.View;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.component.MyButton;

public class HomeFragment extends MyFragment {

    private MyButton btn_request;

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
    }

    @Override
    protected void initListener() {

    }
}
