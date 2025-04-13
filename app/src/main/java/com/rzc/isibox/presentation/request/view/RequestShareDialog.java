package com.rzc.isibox.presentation.request.view;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyDialog;
import com.rzc.isibox.tools.Utility;

public class RequestShareDialog extends MyDialog {
    RelativeLayout rvly_root;
    CardView card_body;
    ImageView ic_close;
    LinearLayout ln_whatsapp,ln_email,ln_url;
    public RequestShareDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_share_request;
    }

    @Override
    protected void initLayout(View view) {
        rvly_root = view.findViewById(R.id.rvly_root);
        ic_close = view.findViewById(R.id.ic_close);
        ln_whatsapp = view.findViewById(R.id.ln_whatsapp);
        ln_email = view.findViewById(R.id.ln_email);
        ln_url = view.findViewById(R.id.ln_url);
        ln_url = view.findViewById(R.id.ln_url);
        card_body = view.findViewById(R.id.card_body);

        ic_close.setOnClickListener(v->{
            dismiss();
        });

        rvly_root.setOnClickListener(v->{
            dismiss();
        });

    }

    @Override
    public void show() {
        super.show();
        card_body.setVisibility(View.VISIBLE);
        card_body.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.zoom_in));
    }

    public  void  show(String link){
        super.show();
        ln_whatsapp.setOnClickListener(v->{
            Utility.showToastSuccess(mActivity,link);
        });

        ln_email.setOnClickListener(v->{
            Utility.showToastSuccess(mActivity,link);
        });

        ln_url.setOnClickListener(v->{
            Utility.showToastSuccess(mActivity,link);
        });

    }
}
