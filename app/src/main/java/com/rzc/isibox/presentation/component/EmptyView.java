package com.rzc.isibox.presentation.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyView;

public class EmptyView extends MyView {

    TextView tv_empty;
    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_view_empty;
    }

    @Override
    protected void initLayout() {
        tv_empty = findViewById(R.id.tv_empty);
    }

    @Override
    protected void initListener() {

    }

    public void create(String text) {
        super.create();
        tv_empty.setText(text);
        hide();
    }

    public void show(){
        setVisibility(VISIBLE);
    }
    public void hide(){
        setVisibility(GONE);
    }


}
