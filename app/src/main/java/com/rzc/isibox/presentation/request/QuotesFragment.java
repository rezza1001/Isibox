package com.rzc.isibox.presentation.request;

import android.os.Bundle;
import android.view.View;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;

public class QuotesFragment extends MyFragment {

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

    }

    @Override
    protected void initListener() {

    }
}
