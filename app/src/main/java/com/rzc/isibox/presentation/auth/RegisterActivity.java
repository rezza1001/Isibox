package com.rzc.isibox.presentation.auth;

import android.content.Intent;

import androidx.activity.OnBackPressedCallback;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.AlertDialog;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.main.HomeActivity;

public class RegisterActivity extends MyActivity {

    private MyButton btn_signup;
    @Override
    protected int setLayout() {
        return R.layout.auth_activtity_register;
    }

    @Override
    protected void initLayout() {
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.create(MyButton.TYPE.PRIMARY_LIGHT,"SIGN UP");
    }

    @Override
    protected void initListener() {
        findViewById(R.id.tv_signIn).setOnClickListener(v -> handleBackPress());

        btn_signup.setOnMyClickListener(view -> {
            AlertDialog dialog = new AlertDialog(this);
            dialog.showInfo("Email Verification Sent!","Please check your email, a link for verification will be sent to your email");
            dialog.setOnSelectedListener(()->{
                startActivity(new Intent(mActivity, HomeActivity.class));
                mActivity.finish();
            });
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handleBackPress();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void handleBackPress() {
        startActivity(new Intent(mActivity, LoginActivity.class));
        mActivity.finish();
    }

}
