package com.rzc.isibox.presentation.request;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.ConfirmDialog;
import com.rzc.isibox.presentation.component.KeyValueView;
import com.rzc.isibox.presentation.component.chip.ChipFilterView;
import com.rzc.isibox.presentation.component.chip.ChoiceModel;
import com.rzc.isibox.presentation.component.slider.ImageModel;
import com.rzc.isibox.presentation.component.slider.ImageSliderView;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.internal.Util;

public class DetailRequestActivity extends MyActivity {

    ImageView iv_back,ic_share;
    ImageSliderView slider_view;
    LinearLayout ln_value;
    ChipFilterView chip_view;
    TextView tv_created,tv_address,tv_action;
    RelativeLayout rv_action;
    @Override
    protected int setLayout() {
        return R.layout.request_activity_detail;
    }

    @Override
    protected void initLayout() {
        slider_view = findViewById(R.id.slider_view);
        ln_value    = findViewById(R.id.ln_value);
        chip_view    = findViewById(R.id.chip_view);
        tv_created    = findViewById(R.id.tv_created);
        tv_address    = findViewById(R.id.tv_address);
        iv_back    = findViewById(R.id.iv_back);
        ic_share    = findViewById(R.id.ic_share);
        tv_action    = findViewById(R.id.tv_action);
        rv_action    = findViewById(R.id.rv_action);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(v ->{
            mActivity.finish();
        });

        ic_share.setOnClickListener(v->{
            RequestShareDialog dialog = new RequestShareDialog(mActivity);
            dialog.show("Women Bag");
        });
        rv_action.setOnClickListener(v->{
            ConfirmDialog dialog = new ConfirmDialog(mActivity);
            if(tv_action.getText().equals("KIRIM PENAWARAN")){
                dialog.show(ConfirmDialog.TYPE.GREEN,"Kirim Penawaran","Untuk mengirim penawaran akan dilanjutkan via aplikasi Whatsapp",R.drawable.ic_whatsapp);
                dialog.setTextAction("Batalkan","Lanjutkan");
                dialog.setOnActionListener(new ConfirmDialog.OnActionListener() {
                    @Override
                    public void onProcess(String note) {
                        toWhatsapp();
                    }

                    @Override
                    public void onProces2() {

                    }
                });

            }else{


                dialog.show(ConfirmDialog.TYPE.RED,"Mengubah status","Apakah anda yakin ingin mengubah status barang menjadi sudah dipesan?",R.drawable.icon_md_warning);


                dialog.setOnActionListener(new ConfirmDialog.OnActionListener() {
                    @Override
                    public void onProcess(String note) {
                        Utility.showToastSuccess(mActivity,"Berhasil mengubah status menjadi sudah dipesan");
                        mActivity.finish();
                    }

                    @Override
                    public void onProces2() {

                    }
                });
            }


        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initialData() {
        int offer = getIntent().getIntExtra("NAMA_REQUEST",0);
        if (offer != 0) {
            tv_action.setText("TANDAI SUDAH DIPESAN");
        }
        ArrayList<ImageModel> models = new ArrayList<>();
        models.add(new ImageModel("https://images.tokopedia.net/img/cache/500-square/hDjmkQ/2023/9/28/7702649e-1a0b-46b1-9381-bb7e64dcb5c3.jpg.webp"));
        models.add(new ImageModel("https://images.tokopedia.net/img/cache/700/hDjmkQ/2023/9/28/7675d37d-2419-42f5-9ccf-5f9ed473292e.jpg.webp"));
        models.add(new ImageModel("https://images.tokopedia.net/img/cache/500-square/hDjmkQ/2023/9/28/bec180b0-a71b-456d-a7ec-6434218f9634.jpg.webp"));
        models.add(new ImageModel("https://images.tokopedia.net/img/cache/100-square/hDjmkQ/2023/9/28/b5e717f2-9ba3-4508-8891-2dc0cb042a69.jpg.webp"));
        slider_view.create(getSupportFragmentManager(), models);

        buildInfoView("Kategori","Bags");
        buildInfoView("Waktu Pengiriman","1-4 hari");
        buildInfoView("Pengiriman","Semua Ekspedisi");
        buildInfoView("Referensi Website","-");
        buildInfoView("Pembayaran","Semua Cara Pembayaran");


        ArrayList<ChoiceModel>  keywords = new ArrayList<>();
        keywords.add(new ChoiceModel("Women Bag"));
        keywords.add(new ChoiceModel("Tas Cewe"));
        keywords.add(new ChoiceModel("Asesoris Wanita"));
        keywords.add(new ChoiceModel("Perhiasan"));
        keywords.add(new ChoiceModel("Pakaian"));
        chip_view.create(keywords, 2, 1, R.color.grey3, R.color.white,R.color.black);

        String createdInfo = getResources().getString(R.string.date_created)+" : "+ Utility.getDateString(new Date(),"dd MMM yyyy");
        tv_created.setText(createdInfo);

    }

    public void toWhatsapp(){

        String phoneNumber = "6281322658091";

        String message = "Halo, saya tertarik dengan produk Anda!";


        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));


        try {
            startActivity(intent);
        } catch (Exception e) {

            Utility.showToastError(mActivity,"WhatsApp tidak ditemukan");
        }
    }

    private void buildInfoView(String key, String value){
        KeyValueView kView = new KeyValueView(mActivity, null);
        kView.create(key,value);
        ln_value.addView(kView);
    }
}
