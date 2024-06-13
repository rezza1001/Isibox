package com.rzc.isibox.presentation.request;

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

public class MainReqFragment extends MyFragment {

    MyRelativeLayout rv_request,rv_quotes;
    MyRelativeLayout rvSelected;
    FrameLayout frame_body;

    public static MainReqFragment newInstance() {
        Bundle args = new Bundle();
        MainReqFragment fragment = new MainReqFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.request_fragment_main;
    }

    @Override
    protected void initLayout(View view) {
        rv_request = view.findViewById(R.id.rv_request);
        rv_quotes = view.findViewById(R.id.rv_quotes);
        frame_body = view.findViewById(R.id.frame_body);

        rvSelected = rv_request;

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

        rvSelected.setColor(Color.WHITE);
        TextView tvOld = (TextView) rvSelected.getChildAt(0);
        tvOld.setTextColor(Color.BLACK);

        rvSelected = rv;
        rvSelected.setColor(ContextCompat.getColor(mActivity, R.color.primary));
        TextView tvNew = (TextView) rvSelected.getChildAt(0);
        tvNew.setTextColor(Color.WHITE);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment activeFragment;
        if (rvSelected == rv_request){
            activeFragment = RequestFragment.newInstance();
            fragmentTransaction.replace(frame_body.getId(), activeFragment, "request");
        }
        else {
            activeFragment = QuotesFragment.newInstance();
            fragmentTransaction.replace(frame_body.getId(), activeFragment, "quotes");
        }

        fragmentTransaction.detach(activeFragment);
        fragmentTransaction.attach(activeFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
