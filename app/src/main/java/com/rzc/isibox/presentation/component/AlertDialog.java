package com.rzc.isibox.presentation.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyDialog;

public class AlertDialog extends MyDialog {

    private TextView tv_title,tv_description, tv_action;
    private ImageView iv_icon;
    private CardView card_body;
    private OnSelectedListener listener;

    public static AlertDialog newInstance(Context context) {
        return new AlertDialog(context);
    }

    public AlertDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_dialog_alert;
    }

    @Override
    protected void initLayout(View view) {
        iv_icon = view.findViewById(R.id.iv_icon);
        card_body = view.findViewById(R.id.card_body);
        card_body.setVisibility(View.INVISIBLE);

        tv_action = view.findViewById(R.id.tv_action);

        tv_action.setOnClickListener(view1 -> {
            if (listener != null){
                listener.onSelected();
            }
            dismiss();
        });

        tv_title = view.findViewById(R.id.tv_title);
        tv_description = view.findViewById(R.id.tv_description);
    }


    public void show(String title, String description) {
        show();
        tv_title.setText(title);
        tv_description.setText(description);
    }
    public void showInfo(String title, String description, int icon) {
        show();
        tv_title.setText(title);
        tv_description.setText(description);
        iv_icon.setImageResource(icon);
        int color = ContextCompat.getColor(mActivity, R.color.primary);
        iv_icon.setColorFilter(color);
        tv_title.setTextColor(color);
        tv_action.setTextColor(color);
    }
    public void showInfoImage(String title, String description, int icon) {
        show();
        tv_title.setText(title);
        tv_description.setText(description);
        iv_icon.setImageResource(icon);
        int color = ContextCompat.getColor(mActivity, R.color.primary);
        tv_title.setTextColor(color);
        tv_action.setTextColor(color);
    }

    @SuppressLint("SetTextI18n")
    public void show(String description) {
        show();
        tv_title.setText("Konfirmasi");
        tv_description.setText(description);
    }

    public void showSuccess(String message){
        show("Berhasil",message);
        iv_icon.setImageResource(R.drawable.ic_success);
    }
    public void showSuccess(String title, String message){
        show(title,message);
        iv_icon.setImageResource(R.drawable.ic_success);
    }

    public void showInfo(String message){
        show("Informasi",message);
        iv_icon.setImageResource(R.drawable.icon_awesome_info_circle);
        int color = ContextCompat.getColor(mActivity, R.color.primary);
        iv_icon.setColorFilter(color);
        tv_title.setTextColor(color);
        tv_action.setTextColor(color);
    }

    public void showFailed(String message){
        show("Gagal",message);
        iv_icon.setImageResource(R.drawable.icon_md_warning);
        tv_title.setTextColor(Color.parseColor("#f93b37"));
        tv_action.setTextColor(Color.parseColor("#f93b37"));

    }

    public void setFailed(){
        iv_icon.setImageResource(R.drawable.icon_md_warning);
        tv_title.setTextColor(Color.parseColor("#f93b37"));
        tv_action.setTextColor(Color.parseColor("#f93b37"));
    }

    public void setTextButton(String text){
        tv_action.setText(text);
    }

    @Override
    public void show() {
        super.show();
        card_body.setVisibility(View.VISIBLE);
        card_body.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.zoom_in));
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener){
        listener = onSelectedListener;
    }
    public interface OnSelectedListener{
        void onSelected();
    }
}
