package com.rzc.isibox.presentation.auth;

import android.content.Intent;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.main.HomeActivity;

public class LoginActivity extends MyActivity {

    private MyButton btn_login;
    @Override
    protected int setLayout() {
        return R.layout.auth_activtity_login;
    }

    @Override
    protected void initLayout() {
        btn_login = findViewById(R.id.btn_login);
        btn_login.create(MyButton.TYPE.PRIMARY_LIGHT,"LOG IN");
    }

    @Override
    protected void initListener() {
        findViewById(R.id.tv_register).setOnClickListener(v -> {
            startActivity(new Intent(mActivity, RegisterActivity.class));
            mActivity.finish();
        });

        btn_login.setOnMyClickListener(view -> {
            startActivity(new Intent(mActivity, HomeActivity.class));
            mActivity.finish();
        });
    }
}
