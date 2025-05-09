package com.rzc.isibox.presentation.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rzc.isibox.R;
import com.rzc.isibox.connection.db.DatabaseManager;
import com.rzc.isibox.data.MyPreference;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.presentation.component.ConfirmDialog;
import com.rzc.isibox.presentation.main.SplashScreenActivity;

public class AccountFragment extends MyFragment {

    ItemView item_setting,item_upgrade,item_about,item_logout, item_address;
    TextView tv_name;

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
        item_logout = view.findViewById(R.id.item_logout);
        item_logout.create(4,"Logout");
        item_address = view.findViewById(R.id.item_address);
        item_address.create(5,"Alamat Pengiriman");

        tv_name = view.findViewById(R.id.tv_name);
    }

    @Override
    protected void initListener() {

        item_logout.setOnActionListener((id, value) -> logout());
        item_address.setOnActionListener((id, value) -> {
            Intent intent = new Intent(mActivity, AddressListActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        String name = "Hi, "+ accountModel.getName();
        tv_name.setText(name);
    }

    private void logout(){
        ConfirmDialog dialog = new ConfirmDialog(mActivity);
        dialog.show(ConfirmDialog.TYPE.RED,"Keluar Aplikasi","Anda yakin untuk keluar dari aplikasi",R.drawable.icon_md_warning);
        dialog.setOnActionListener(new ConfirmDialog.OnActionListener() {
            @Override
            public void onProcess(String note) {
                clearData();
            }

            @Override
            public void onCancel() {

            }
        });
    }


    private void clearData(){
        try {
            MyPreference.clear(mActivity);
            DatabaseManager manager = new DatabaseManager(mActivity);
            manager.clearAllData(mActivity);
            startActivity(new Intent(mActivity, SplashScreenActivity.class));
            mActivity.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
