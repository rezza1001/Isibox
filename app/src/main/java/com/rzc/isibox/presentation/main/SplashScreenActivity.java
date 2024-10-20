package com.rzc.isibox.presentation.main;



import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.auth.activity.LoginActivity;

public class SplashScreenActivity extends MyActivity {
    private Runnable runnable;
    private Handler handler;
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
//        new Handler().postDelayed(() -> {
//            startActivity(new Intent(mActivity, LoginActivity.class));
//            mActivity.finish();
//        },1000);

        start();
    }

    private void start(){
        runnable = () -> {
            Intent intent;
            Log.d("KOCAK",accountModel.getName()+"," +accountModel.getUser_id());
            if (accountModel.getUser_id() == null){
                Log.w(TAG,"Account Is Null");
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            }
            else {
                Log.i(TAG,"Account Active "+ accountModel.getUser_id());
                intent = new Intent(getApplicationContext(), HomeActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            mActivity.finish();
        };

        handler = new Handler();
        handler.postDelayed(runnable, 1000);
    }
}
