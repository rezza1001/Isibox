package com.rzc.isibox.presentation.main;

import android.util.Log;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.MyButton;

public class HomeActivity extends MyActivity {

    private MyButton btn_request;
    private MenuBottomView menu_bottom;

    @Override
    protected int setLayout() {
        return R.layout.main_activity_home;
    }

    @Override
    protected void initLayout() {
        btn_request = findViewById(R.id.btn_request);
        btn_request.create(MyButton.TYPE.PRIMARY,getResources().getString(R.string.create_request));
        btn_request.setCardCfg(1,20);

        menu_bottom = findViewById(R.id.menu_bottom);
        menu_bottom.create();
    }

    @Override
    protected void initListener() {
        menu_bottom.setOnMenuSelectedListener(index -> {
            Log.d(TAG,"Select Menu "+ index);
        });
    }
}
