package com.rzc.isibox.presentation.orders;

import android.os.Bundle;
import android.view.View;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.component.EmptyView;

public class CompleteFragment extends MyFragment {

    EmptyView empty_view;
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
        empty_view = view.findViewById(R.id.empty_view);
        empty_view.create("Anda belum membuat pesanan. Silahkan buat Reqyest atau Kirim Penawaran untuk bisa mengakses halaman Pesanan. Saya");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        empty_view.show();
    }
}
