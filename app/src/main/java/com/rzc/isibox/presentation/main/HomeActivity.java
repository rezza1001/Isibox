package com.rzc.isibox.presentation.main;

import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.account.AccountFragment;
import com.rzc.isibox.presentation.orders.OrdersFragment;
import com.rzc.isibox.presentation.request.MainReqFragment;

public class HomeActivity extends MyActivity {

    private MenuBottomView menu_bottom;
    private FrameLayout frame_body;

    @Override
    protected int setLayout() {
        return R.layout.main_activity_home;
    }

    @Override
    protected void initLayout() {
        menu_bottom = findViewById(R.id.menu_bottom);
        menu_bottom.create();

        frame_body = findViewById(R.id.frame_body);
    }

    @Override
    protected void initListener() {
        menu_bottom.setOnMenuSelectedListener(this::selectMenu);
    }

    private void selectMenu(int index){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment activeFragment;

        switch (index){
            case 1 :
                activeFragment = MainReqFragment.newInstance();
                fragmentTransaction.replace(frame_body.getId(), activeFragment, "request");
                break;
            case 2 :
                activeFragment = OrdersFragment.newInstance();
                fragmentTransaction.replace(frame_body.getId(), activeFragment, "orders");
                break;
            case 3 :
                activeFragment = AccountFragment.newInstance();
                fragmentTransaction.replace(frame_body.getId(), activeFragment, "profile");
                break;
            default:
                activeFragment = HomeFragment.newInstance();
                fragmentTransaction.replace(frame_body.getId(), activeFragment, "home");
                break;
        }

        fragmentTransaction.detach(activeFragment);
        fragmentTransaction.attach(activeFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
