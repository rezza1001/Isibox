package com.rzc.isibox.presentation.request;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.rzc.isibox.R;
import com.rzc.isibox.connection.api.EndpointURL;
import com.rzc.isibox.connection.api.ErrorCode;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.ConfirmDialog;
import com.rzc.isibox.presentation.component.KeyValueView;
import com.rzc.isibox.presentation.component.chip.ChipFilterView;
import com.rzc.isibox.presentation.component.chip.ChoiceModel;
import com.rzc.isibox.presentation.component.slider.ImageModel;
import com.rzc.isibox.presentation.component.slider.ImageSliderView;
import com.rzc.isibox.presentation.quots.dialog.BidsDialog;
import com.rzc.isibox.presentation.quots.dialog.BidsInfoDialog;
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
    String requestId;
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
            if(tv_action.getText().equals("KIRIM PENAWARAN")){
                sendBids();
            }
            else {
                openMyBids();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initialData() {
        requestId = getIntent().getStringExtra(Global.DATA);
        if (requestId == null){
            mActivity.finish();
            return;
        }
        ln_value.removeAllViews();

        viewModel = new ViewModelProvider(mActivity).get(RequestViewModel.class);
        viewModel.init(mActivity);
        viewModel.loadMyRequestDetail(requestId).observe(mActivity, myRequestDetailModel -> {
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

            if (detailModel.getBid() != null){
                rv_action.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.grey2));
                tv_action.setText("Detail Penawaran Anda");
            }
            else {
                rv_action.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.primary));
                tv_action.setText("KIRIM PENAWARAN");
            }

            viewModel.viewProduct(detailModel.getRequestID());
        });


    }

    private void sendBids(){
        BidsDialog dialog = new BidsDialog(mActivity);
        dialog.show(detailModel);
        dialog.setOnActionListener((offering, note) -> {
            viewModel.sendBid(requestId, offering, note).observe(mActivity, apiResponse -> {
                if (apiResponse.getCode() == ErrorCode.OK_200){
                    Utility.showToastSuccess(mActivity,apiResponse.getMessage());
                    initialData();
                }
                else {
                    Utility.showAlertError(mActivity, apiResponse.getMessage());
                }
            });
        });
    }

    private void openMyBids(){
        BidsInfoDialog dialog = new BidsInfoDialog(mActivity);
        dialog.show(detailModel);
        dialog.setOnActionListener(() -> {
            ConfirmDialog confirmDialog = new ConfirmDialog(mActivity);
            confirmDialog.show(ConfirmDialog.TYPE.RED,"Pembatalan", "Anda yakin akan membatalkan pengajuan penawaran ?", R.drawable.icon_md_warning);
            confirmDialog.showInputNote();
            confirmDialog.setRequiredNote();
            confirmDialog.setOnActionListener(new ConfirmDialog.OnActionListener() {
                @Override
                public void onProcess(String note) {
                    cancel(note);
                }

                @Override
                public void onCancel() {

                }
            });
        });

    }


    private void cancel(String note){
        viewModel.cancelBid(detailModel.getBid().getBidID()+"", note).observe(mActivity, apiResponse -> {
            if (apiResponse.getCode() == ErrorCode.OK_200){
                Utility.showToastSuccess(mActivity,apiResponse.getMessage());
                initialData();
            }
            else {
                Utility.showAlertError(mActivity, apiResponse.getMessage());
            }
        });
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
