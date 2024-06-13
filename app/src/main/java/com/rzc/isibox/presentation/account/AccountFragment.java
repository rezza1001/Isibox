package com.rzc.isibox.presentation.account;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;

public class AccountFragment extends MyFragment {

    ItemView item_setting,item_upgrade,item_about;
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
        item_setting = view.findViewById(R.id.item_setting);
        item_setting.create(1,"Pengaturan Akun");
        item_upgrade = view.findViewById(R.id.item_upgrade);
        item_upgrade.create(2,"Upgrade Akun");
        item_about = view.findViewById(R.id.item_about);
        item_about.create(3,"Tentang ISIBOX");
    }

    @Override
    protected void initListener() {

    }
}
