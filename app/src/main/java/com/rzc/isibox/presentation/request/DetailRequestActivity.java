package com.rzc.isibox.presentation.request;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.rzc.isibox.R;
import com.rzc.isibox.connection.api.EndpointURL;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.ConfirmDialog;
import com.rzc.isibox.presentation.component.KeyValueView;
import com.rzc.isibox.presentation.component.chip.ChipFilterView;
import com.rzc.isibox.presentation.component.chip.ChoiceModel;
import com.rzc.isibox.presentation.component.slider.ImageModel;
import com.rzc.isibox.presentation.component.slider.ImageSliderView;
import com.rzc.isibox.presentation.main.MainModel;
import com.rzc.isibox.presentation.request.model.MyRequestDetailModel;
import com.rzc.isibox.presentation.request.view.RequestShareDialog;
import com.rzc.isibox.presentation.request.vm.RequestViewModel;
import com.rzc.isibox.tools.MyCurrency;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;
import java.util.Date;

public class DetailRequestActivity extends MyActivity {

    ImageView iv_back,ic_share;
    ImageSliderView slider_view;
    LinearLayout ln_value;
    ChipFilterView chip_view;
    TextView tv_created,tv_address,tv_action, tv_creator;
    TextView tv_name,tv_metric,tv_qty,tv_price,tv_description;
    RelativeLayout rv_action;
    RequestViewModel viewModel;
    MainModel mainModel;
    MyRequestDetailModel detailModel;

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
        tv_name    = findViewById(R.id.tv_name);
        tv_metric    = findViewById(R.id.tv_metric);
        tv_qty    = findViewById(R.id.tv_qty);
        tv_price    = findViewById(R.id.tv_price);
        tv_description    = findViewById(R.id.tv_description);
        tv_creator    = findViewById(R.id.tv_creator);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(v ->{
            mActivity.finish();
        });

        findViewById(R.id.rv_map).setOnClickListener(v -> openMap());

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
                    public void onCancel() {

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
                    public void onCancel() {

                    }
                });
            }

        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initialData() {
        mainModel = (MainModel) getIntent().getSerializableExtra(Global.DATA);
        if (mainModel == null){
            mActivity.finish();
            return;
        }
        viewModel = new ViewModelProvider(mActivity).get(RequestViewModel.class);
        viewModel.init(mActivity);
        viewModel.loadMyRequestDetail(mainModel.getId()).observe(mActivity, myRequestDetailModel -> {
            detailModel = myRequestDetailModel;

            tv_name.setText(detailModel.getProductName());
            tv_metric.setText(detailModel.getMetric());
            tv_qty.setText(String.valueOf(detailModel.getQuantity()));
            tv_price.setText(MyCurrency.toCurrnecy("Rp", detailModel.getTargetPrice()));
            tv_description.setText(detailModel.getDescription());
            tv_creator.setText(detailModel.getRequestByName());
            if (detailModel.getCity() != null){
                tv_address.setText(detailModel.getCity()+", "+ detailModel.getProvince());
            }


            Date created = Utility.convert2Date(detailModel.getRequestDate(), "yyyy-MM-dd HH:mm");
            String createdInfo = getResources().getString(R.string.date_created)+" : "+ Utility.getDateString(created,"dd MMM yyyy");
            tv_created.setText(createdInfo);

            buildInfoView("Kategori", detailModel.getCategory());
            buildInfoView("Waktu Pengiriman",detailModel.getDeliveryTime());
            buildInfoView("Pengiriman", detailModel.getShippingMethod());
            buildInfoView("Referensi Website",detailModel.getReferenceLink());
            buildInfoView("Pembayaran",detailModel.getPaymentMethod());

            ArrayList<ChoiceModel>  keywords = new ArrayList<>();
            for (MyRequestDetailModel.Attributes attributes : detailModel.getKeywords()){
                keywords.add(new ChoiceModel(attributes.getValue()));
                chip_view.create(keywords, 2, 1, R.color.grey3, R.color.white,R.color.black);
            }

            ArrayList<ImageModel> models = new ArrayList<>();
            for (MyRequestDetailModel.Attributes attributes : detailModel.getImages()){
                String endPoint =  new EndpointURL(mActivity).getBaseUrl();
                Log.d(TAG,"attributes "+attributes.getValue());
                String url = endPoint + attributes.getValue().replace("../","");
                models.add(new ImageModel(url));

            }
            slider_view.create(getSupportFragmentManager(), models);
        });

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

    private void openMap(){
        if (detailModel.getAddress() == null){
            Utility.showAlertError(mActivity,"Alamat tidak ditemukan");
            return;
        }
        double destinationLatitude = Double.parseDouble(detailModel.getLatitude());
        double destinationLongitude = Double.parseDouble(detailModel.getLongitude());
        String label = detailModel.getRequestByName();

        String uri = "geo:" + destinationLatitude + "," + destinationLongitude + "?q=" + destinationLatitude + "," + destinationLongitude + "(" + label + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        mActivity.startActivity(intent);
    }

    private void buildInfoView(String key, String value){
        KeyValueView kView = new KeyValueView(mActivity, null);
        kView.create(key,value);
        ln_value.addView(kView);
    }
}
