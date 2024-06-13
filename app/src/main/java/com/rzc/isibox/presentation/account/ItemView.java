package com.rzc.isibox.presentation.account;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyView;

public class ItemView extends MyView {

    RelativeLayout rv_item;
    TextView tv_itemName;
    ImageView iv_open;
    int id =0;
    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.account_view_item;
    }

    @Override
    protected void initLayout() {
        rv_item = findViewById(R.id.rv_item);
        tv_itemName = findViewById(R.id.tv_itemName);
        iv_open = findViewById(R.id.iv_open);
    }

    @Override
    protected void initListener() {
        rv_item.setOnClickListener(v -> {
            if (onActionListener != null){
                onActionListener.onAction(id, tv_itemName.getText().toString().trim());
            }
        });
    }

    protected void create(int id, String value) {
        super.create();
        this.id = id;
        tv_itemName.setText(value);
    }

    protected OnActionListener onActionListener;
    public void setOnActionListener(OnActionListener onActionListener){
        this.onActionListener = onActionListener;
    }
    public interface OnActionListener{
        void onAction(int id, String value);
    }
}
