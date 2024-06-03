package com.rzc.isibox.presentation.main;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyView;

import java.util.ArrayList;

public class MenuBottomView extends MyView {

    LinearLayout ln_home,ln_request,ln_order,ln_account;

    ArrayList<LinearLayout> listMenu = new ArrayList<>();
    LinearLayout ln_select = null;
    public MenuBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.main_view_bottom_menu;
    }

    @Override
    protected void initLayout() {
        ln_home = findViewById(R.id.ln_home);
        ln_request = findViewById(R.id.ln_request);
        ln_order = findViewById(R.id.ln_order);
        ln_account = findViewById(R.id.ln_account);




    }

    @Override
    protected void initListener() {
        ln_home.setOnClickListener(v -> onSelected(ln_home));
        ln_request.setOnClickListener(v -> onSelected(ln_request));
        ln_order.setOnClickListener(v -> onSelected(ln_order));
        ln_account.setOnClickListener(v -> onSelected(ln_account));
    }

    @Override
    protected void create() {
        super.create();
        listMenu.add(ln_home);
        listMenu.add(ln_request);
        listMenu.add(ln_order);
        listMenu.add(ln_account);
        new Handler().postDelayed(() -> onSelected(ln_home),100);
    }

    private void onSelected(LinearLayout linearLayout){
        if (ln_select == linearLayout){
            return;
        }

        for (LinearLayout ln : listMenu){
            if (linearLayout == ln){
                ln_select = ln;
                changeColor(ln, true);
                if (onMenuSelectedListener != null){
                    onMenuSelectedListener.onSelectMenu(listMenu.indexOf(linearLayout));
                }
                continue;
            }
            changeColor(ln, false);
        }
    }

    private void changeColor(LinearLayout linearLayout, boolean select){
        ImageView iv = (ImageView) linearLayout.getChildAt(0);
        TextView tv = (TextView) linearLayout.getChildAt(1);
        if (select){
            tv.setTextColor(ContextCompat.getColor(mActivity, R.color.primaryDark));
            iv.setColorFilter(ContextCompat.getColor(mActivity, R.color.primaryDark));
        }
        else {
            tv.setTextColor(ContextCompat.getColor(mActivity, R.color.black));
            iv.setColorFilter(ContextCompat.getColor(mActivity, R.color.black));
        }
    }

    private OnMenuSelectedListener onMenuSelectedListener;
    public void setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener){
        this.onMenuSelectedListener = onMenuSelectedListener;
    }

    public interface OnMenuSelectedListener{
        void onSelectMenu(int indext);
    }
}
