package com.rzc.isibox.presentation.account;

import android.os.Bundle;
import android.view.View;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;

public class AccountFragment extends MyFragment {

    public static AccountFragment newInstance() {
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.account_fragment_main;
    }

    @Override
    protected void initLayout(View view) {

    }

    @Override
    protected void initListener() {

    }
}
