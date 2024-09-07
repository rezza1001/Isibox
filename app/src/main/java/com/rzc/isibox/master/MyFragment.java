package com.rzc.isibox.master;

import static android.content.Context.RECEIVER_EXPORTED;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import org.json.JSONObject;

import java.util.Objects;

public abstract class MyFragment extends Fragment {

    protected AppCompatActivity mActivity;
    private View view;
    protected String TAG = "MyFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setLayout(), container, false);
        initLayout(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initListener();
    }

    protected abstract int setLayout();
    protected abstract void initLayout(View view);
    protected abstract void initListener();

    protected void initData(){
    }

    protected void registerNotificationFB(){

    }

    protected void inComingNotificationFB(String title, String message, JSONObject data){
    }


    public View getmView() {
        return view;
    }

    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), MyActivity.BROADCAST_FINISH)){
                mActivity.finish();
            }
            else if (Objects.equals(intent.getAction(), MyActivity.BROADCAST_REFRESH)){
                Log.d(TAG,"BROADCAST_REFRESH");
                onRefreshPage();
            }
        }
    };

    protected void onRefreshPage(){

    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    protected void mRegisterReceiver(BroadcastReceiver receiver, IntentFilter intentFilter){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mActivity.registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED);
        }
        else {
            mActivity.registerReceiver(receiver, intentFilter);
        }

    }
}