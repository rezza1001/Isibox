package com.rzc.isibox.presentation.main;

import android.content.Intent;
import android.os.Handler;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.auth.LoginActivity;

public class SplashScreenActivity extends MyActivity {
    @Override
    protected int setLayout() {
        return R.layout.main_activity_splash;
    }

    @Override
    protected void initLayout() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initialData() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(mActivity, HomeActivity.class));
            mActivity.finish();
        },1000);
    }
}
