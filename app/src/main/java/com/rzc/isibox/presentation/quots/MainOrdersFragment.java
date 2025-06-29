package com.rzc.isibox.presentation.quots;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.component.MyRelativeLayout;

public class MainOrdersFragment extends MyFragment {

    MyRelativeLayout rv_request,rv_quotes;
    MyRelativeLayout rvSelected;
    FrameLayout frame_body;

    public static MainOrdersFragment newInstance() {
        Bundle args = new Bundle();
        MainOrdersFragment fragment = new MainOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.orders_fragment_main;
    }

    @Override
    protected void initLayout(View view) {
        rv_request = view.findViewById(R.id.rv_request);
        rv_quotes = view.findViewById(R.id.rv_quotes);
        frame_body = view.findViewById(R.id.frame_body);

        switchMenu(rv_request);
    }

    @Override
    protected void initListener() {
        rv_request.setOnClickListener(v -> switchMenu((MyRelativeLayout) v));
        rv_quotes.setOnClickListener(v -> switchMenu((MyRelativeLayout) v));
    }

    private void switchMenu(MyRelativeLayout rv){
        if (rv == rvSelected){
            return;
        }

        if (rvSelected == null){
            rvSelected = rv;
        }

        rvSelected.setColor(Color.WHITE);
        TextView tvOld = (TextView) rvSelected.getChildAt(0);
        tvOld.setTextColor(Color.BLACK);

        rvSelected = rv;
        rvSelected.setColor(ContextCompat.getColor(mActivity, R.color.primary));
        TextView tvNew = (TextView) rvSelected.getChildAt(0);
        tvNew.setTextColor(Color.WHITE);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment activeFragment;
        activeFragment = QuotFragment.newInstance();
        fragmentTransaction.replace(frame_body.getId(), activeFragment, "orders");

        fragmentTransaction.detach(activeFragment);
        fragmentTransaction.attach(activeFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
