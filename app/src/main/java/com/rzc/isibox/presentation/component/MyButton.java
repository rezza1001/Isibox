package com.rzc.isibox.presentation.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyView;

public class MyButton extends MyView {

    public enum TYPE  {
        PRIMARY_LIGHT,PRIMARY,ORANGE,GRAY, DISABLE, LGRAY, RED, GREEN
    }

    protected CardView card_button;
    protected LinearLayout lv_button;
    protected TextView tv_button;
    protected TYPE mType;

    protected OnMyClickListener onMyClickListener;

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_button;
    }

    @Override
    protected void initLayout() {
        card_button = mView.findViewById(R.id.card_button);
        lv_button = mView.findViewById(R.id.lv_button);
        tv_button = mView.findViewById(R.id.tv_button);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void create() {
        super.create();
    }

    public void create(TYPE type, String title){
        create();
        tv_button.setText(title);
        setType(type);
        lv_button.setOnClickListener(view -> {
            if (onMyClickListener != null){
                onMyClickListener.onClick(this);
            }
        });
    }

    public TYPE getType() {
        return mType;
    }

    public void setType(TYPE type){
        mType = type;
        if (type == TYPE.PRIMARY_LIGHT){
            card_button.setCardBackgroundColor(ContextCompat.getColor(mActivity,R.color.primaryLight));
            tv_button.setTextColor(ContextCompat.getColor(mActivity,R.color.white));
        }
        else if (type == TYPE.ORANGE){
            card_button.setCardBackgroundColor(ContextCompat.getColor(mActivity,R.color.orange));
            tv_button.setTextColor(getResources().getColor(R.color.white));
        }
        else if (type == TYPE.RED){
            card_button.setCardBackgroundColor(ContextCompat.getColor(mActivity,R.color.red));
            tv_button.setTextColor(getResources().getColor(R.color.white));
        }
        else if (type == TYPE.GREEN){
            card_button.setCardBackgroundColor(ContextCompat.getColor(mActivity,R.color.green));
            tv_button.setTextColor(getResources().getColor(R.color.white));
        }
        else if (type == TYPE.PRIMARY){
            card_button.setCardBackgroundColor(ContextCompat.getColor(mActivity,R.color.primary));
            tv_button.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
        }
        else if (type == TYPE.GRAY){
            card_button.setCardBackgroundColor(ContextCompat.getColor(mActivity,R.color.grey2));
            tv_button.setTextColor(ContextCompat.getColor(mActivity,R.color.white));
        }
        else if (type == TYPE.DISABLE){
            card_button.setCardBackgroundColor(ContextCompat.getColor(mActivity,R.color.grey3));
            tv_button.setTextColor(ContextCompat.getColor(mActivity,R.color.grey1));
        }
        else if (type == TYPE.LGRAY){
            card_button.setCardBackgroundColor(ContextCompat.getColor(mActivity,R.color.grey5));
            tv_button.setTextColor(ContextCompat.getColor(mActivity,R.color.primary));
        }
    }

    public void setOnMyClickListener(OnMyClickListener myClickListener){
        this.onMyClickListener = myClickListener;
    }

    public void setTitle(String title){
        tv_button.setText(title);
    }

    public void setCardCfg(float elevation, float radius){
        card_button.setCardElevation(elevation);
        card_button.setRadius(radius);
    }

    public void setTextSize(int size){
        tv_button.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public interface OnMyClickListener{
        void onClick(MyButton view);
    }
}
