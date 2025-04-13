package com.rzc.isibox.presentation.request.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rzc.isibox.R;
import com.rzc.isibox.master.MyView;
import com.rzc.isibox.presentation.component.PreviewImageDialog;
import com.rzc.isibox.presentation.component.option.OptionData;
import com.rzc.isibox.presentation.component.option.OptionDialog;
import com.rzc.isibox.tools.FileProcessing;
import com.rzc.isibox.tools.PermissionChecker;
import com.rzc.isibox.tools.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ImageChooserView extends MyView {

    private LinearLayout ln_images;
    ArrayList<RoundedImageView> listImgView = new ArrayList<>();
    ArrayList<File> listFile = new ArrayList<>();

    public ImageChooserView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.request_view_photo;
    }

    @Override
    protected void initLayout() {
        ln_images = findViewById(R.id.ln_images);
    }

    @Override
    protected void initListener() {
        mView.findViewById(R.id.rv_pick).setOnClickListener(v -> pickImage());
    }

    @Override
    public void create() {
        super.create();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            if (data == null){
                Utility.showToastError(mActivity,"Terjadi Kesalahan");
                return;
            }
            if (data.getData() == null){
                Utility.showToastError(mActivity,"Terjadi Kesalahan Pengambilan Gambar");
                return;
            }
            processImageAfterPick(data.getData());
        }
    }

    private void pickImage(){
        boolean check = PermissionChecker.checkCamera(mActivity);
        if (!check ){
            return;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    private void processImageAfterPick(Uri uri){
        ProcessImageDialog dialog = new ProcessImageDialog(mActivity);
        dialog.show(uri, "img_"+System.currentTimeMillis()+".jpg");
        dialog.setOnProcessListener(this::createImageView);
    }

    private void createImageView(File file){
        listFile.add(file);
        RoundedImageView imageView = new RoundedImageView(mActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Utility.toUnitDP(mActivity, 70), ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setCornerRadius(4);
        imageView.setBorderColor(ContextCompat.getColor(mActivity, R.color.grey1));
        imageView.setBorderWidth((float) 1);
//        imageView.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.grey3));

        lp.leftMargin = Utility.toUnitDP(mActivity, 1);
        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mActivity).load(file).apply(requestOptions).into(imageView);
        imageView.setTag(listFile.size());
        ln_images.addView(imageView,lp);
        listImgView.add(imageView);
        imageView.setOnClickListener(v -> optionImage(imageView, file));

    }

    private void optionImage(RoundedImageView iv, File file){

        OptionDialog dialog = new OptionDialog(mActivity);
        dialog.show("Opsi");
        dialog.addOption(new OptionData("1","Lihat Foto"));
        dialog.addOption(new OptionData("2","Hapus Foto"));
        dialog.setOnSelectedListener(data -> {
            if (data.getId().equals("1")){
                PreviewImageDialog previewImageDialog = new PreviewImageDialog(mActivity);
                previewImageDialog.show(file);
            }
            else {
                int idx = listImgView.indexOf(iv);
                listImgView.remove(idx);
                listFile.remove(idx);
                ln_images.removeView(iv);
                FileProcessing.deleteImage(file);
            }
        });
    }

    public ArrayList<File> getAllPhoto(){
        return listFile;
    }

    public ArrayList<String> getAllPhotoPath(){
        ArrayList<String> listPath = new ArrayList<>();
        for (File file : listFile){
            listPath.add(file.getAbsolutePath());
        }
        return listPath;
    }
}
