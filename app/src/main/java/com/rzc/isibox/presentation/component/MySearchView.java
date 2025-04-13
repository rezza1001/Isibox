package com.rzc.isibox.presentation.component;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyView;
import com.rzc.isibox.tools.Utility;

public class MySearchView extends MyView {

    private CardView card_search;
    private ImageView iv_closeSearch,iv_clear;
    private EditText et_find;

    public MySearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_view_mysearch;
    }

    @Override
    protected void initLayout() {
        card_search = mView.findViewById(R.id.card_search);
        card_search.setVisibility(View.GONE);

        iv_closeSearch = mView.findViewById(R.id.iv_closeSearch);

        iv_clear = mView.findViewById(R.id.iv_clear);
        iv_clear.setVisibility(View.INVISIBLE);

        et_find = mView.findViewById(R.id.et_find);
    }

    @Override
    protected void initListener() {
        iv_clear.setOnClickListener(view -> et_find.setText(null));
        et_find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (!str.isEmpty() && iv_clear.getVisibility() == View.INVISIBLE){
                    iv_clear.setVisibility(View.VISIBLE);
                }
                else if (str.isEmpty() && iv_clear.getVisibility() == View.VISIBLE){
                    iv_clear.setVisibility(View.INVISIBLE);
                }
                if (onActionListener != null){
                    onActionListener.onType(str);
                }
            }
        });

        iv_closeSearch.setOnClickListener(view -> hideSearch());
    }

    public void create(String hint) {
        super.create();
        et_find.setHint(hint);
    }

    @Override
    public void create() {
        super.create();
    }

    public void showSearch(){
        card_search.clearAnimation();
        card_search.setVisibility(View.VISIBLE);
        card_search.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.search_in));
        new Handler().postDelayed(() -> {
            et_find.requestFocus();
            Utility.showKeyboard(mActivity,et_find);
        },200);
        if (onActionListener != null){
            onActionListener.onShow();
        }
    }

    public void hideSearch(){
        if (card_search.getVisibility() == GONE){
            return;
        }
        card_search.clearAnimation();
        card_search.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.search_out));
        new Handler().postDelayed(() -> {
            et_find.clearFocus();
            Utility.hideKeyboard(mActivity, et_find);
            card_search.setVisibility(View.GONE);
            et_find.setText(null);
            if (onActionListener != null){
                onActionListener.onHide();
            }
        },200);
    }

    private OnActionListener onActionListener;
    public void setOnActionListener(OnActionListener onActionListener){
        this.onActionListener = onActionListener;
    }
    public interface OnActionListener{
        void onType(String searchKey);
        void onShow();
        void onHide();
    }
}
