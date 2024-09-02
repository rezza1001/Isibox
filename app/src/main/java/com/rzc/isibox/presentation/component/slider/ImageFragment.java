package com.rzc.isibox.presentation.component.slider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rzc.isibox.R;
import com.rzc.isibox.master.MyFragment;
import com.rzc.isibox.tools.Utility;

public class ImageFragment extends MyFragment {

    RoundedImageView iv_image;
    public static ImageFragment newInstance(ImageModel model) {
        Bundle args = new Bundle();
        args.putSerializable("data", model);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.component_fragment_imageslider;
    }

    @Override
    protected void initLayout(View view) {
        iv_image = view.findViewById(R.id.iv_image);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        super.initData();
        loadData();
    }

    protected void loadData(){
        if (getArguments() == null){
            Utility.showToastError(mActivity, "Failed load Data");
            return;
        }

        ImageModel model = (ImageModel) getArguments().getSerializable("data");
        if (model == null) {
            Utility.showToastError(mActivity, "Failed load Image");
            return;
        }
        Glide.with(mActivity).load(model.getImageUrl()).into(iv_image);
        Log.d(TAG,model.getImageUrl());
    }
}
