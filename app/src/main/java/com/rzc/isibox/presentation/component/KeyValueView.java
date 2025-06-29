package com.rzc.isibox.presentation.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyView;

public class KeyValueView extends MyView {

    private TextView tv_key,tv_value;

    public KeyValueView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_key_value;
    }

    @Override
    protected void initLayout() {
        tv_key = findViewById(R.id.tv_key);
        tv_value = findViewById(R.id.tv_value);
    }

    @Override
    protected void initListener() {

    }

    public void create(String key, String value){
        create();
        tv_key.setText(key);
        tv_value.setText(value);
    }

    public void setValueGravity(int gravity){
        tv_value.setGravity(gravity);
    }

    public void setValue(String value){
        tv_value.setText(value);
    }

    public void create() {
        super.create();
    }
}
