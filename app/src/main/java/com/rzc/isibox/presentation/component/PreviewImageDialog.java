package com.rzc.isibox.presentation.component;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rzc.isibox.R;
import com.rzc.isibox.master.MyDialog;

import java.io.File;

public class PreviewImageDialog extends MyDialog {

    private ImageView iv_image;

    public PreviewImageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_dialog_preview;
    }

    @Override
    protected void initLayout(View view) {
        iv_image = view.findViewById(R.id.iv_image);
        iv_image.setVisibility(View.INVISIBLE);

        view.findViewById(R.id.iv_close).setOnClickListener(view1 -> dismiss());
    }

    public void show(File file) {
        super.show();

        iv_image.setVisibility(View.VISIBLE);
        iv_image.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.zoom_in));

        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mActivity).load(file).apply(requestOptions).into(iv_image);
    }

    public void show(Uri file) {
        super.show();

        iv_image.setVisibility(View.VISIBLE);
        iv_image.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.zoom_in));

        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mActivity).load(file).apply(requestOptions).into(iv_image);
    }
}
