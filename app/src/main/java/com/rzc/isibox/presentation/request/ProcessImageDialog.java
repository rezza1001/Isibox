package com.rzc.isibox.presentation.request;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rzc.isibox.R;
import com.rzc.isibox.master.MyDialog;
import com.rzc.isibox.presentation.component.Loading;
import com.rzc.isibox.tools.FileProcessing;
import com.rzc.isibox.tools.ViewToImage;

import java.io.File;

public class ProcessImageDialog extends MyDialog {

    private ImageView iv_real;
    private TextView tv_cancel, tv_process;

    private OnProcessListener mProcessListener;

    String name = "";

    public ProcessImageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.request_dialog_process_image;
    }

    @Override
    protected void initLayout(View view) {
        iv_real = view.findViewById(R.id.iv_real);
        tv_cancel = view.findViewById(R.id.tv_cancel);

        tv_process = view.findViewById(R.id.tv_process);

        findViewById(R.id.rv_back).setOnClickListener(view1 -> dismiss());
    }

    public void show(Uri uri, String name) {
        super.show();
        this.name = name;
        FileProcessing.createFolder(mActivity, FileProcessing.ROOT);
        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mActivity).load(uri).apply(requestOptions).into(iv_real);

        tv_cancel.setOnClickListener(view -> dismiss());
        tv_process.setOnClickListener(view -> process());
    }

    private void process(){
        Loading.showLoading(mActivity,"Process image");

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) iv_real.getLayoutParams();
        ViewToImage view = new ViewToImage(mActivity,"image",iv_real,name);
        iv_real.setLayoutParams(lp);
        new Handler().postDelayed(() -> {
            Loading.cancelLoading();
            if (mProcessListener != null){
                mProcessListener.onProcess(view.getOutputFile());
            }
            dismiss();
        },1000);


    }

    public void setOnProcessListener(OnProcessListener onProcessListener){
        mProcessListener = onProcessListener;
    }

    public interface OnProcessListener{
        void onProcess(File file);
    }
}
