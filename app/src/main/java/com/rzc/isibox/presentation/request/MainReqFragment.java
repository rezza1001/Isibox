package com.rzc.isibox.presentation.request;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rzc.isibox.R;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.presentation.component.MySearchView;

public class MainReqFragment extends MyFragment {

    MyRelativeLayout rv_request,rv_quotes;
    ImageView iv_search;
    MyRelativeLayout rvSelected;
    FrameLayout frame_body;
    MySearchView search_view;

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
        search_view = view.findViewById(R.id.search_view);
        iv_search = view.findViewById(R.id.iv_search);
        search_view.create("Cari Permintann");

        switchMenu(rv_request);

    }

    @Override
    protected void initListener() {
        rv_request.setOnClickListener(v -> switchMenu((MyRelativeLayout) v));
        rv_quotes.setOnClickListener(v -> switchMenu((MyRelativeLayout) v));
        iv_search.setOnClickListener(v -> search_view.showSearch());
        search_view.setOnActionListener(new MySearchView.OnActionListener() {
            @Override
            public void onType(String searchKey) {
                Intent intent = new Intent(VariableStatic.BROADCAST_SEARCH);
                intent.putExtra(Global.DATA, searchKey);
                mActivity.sendBroadcast(intent);
            }

            @Override
            public void onShow() {

            }

            @Override
            public void onHide() {

            }
        });
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
        activeFragment = RequestFragment.newInstance();
        fragmentTransaction.replace(frame_body.getId(), activeFragment, "request");
        fragmentTransaction.detach(activeFragment);
        fragmentTransaction.attach(activeFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
