package com.rzc.isibox.presentation.request;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.rzc.isibox.R;
import com.rzc.isibox.connection.api.EndpointURL;
import com.rzc.isibox.connection.api.ErrorCode;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.data.RequestStatus;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.ConfirmDialog;
import com.rzc.isibox.presentation.component.KeyValueView;
import com.rzc.isibox.presentation.component.chip.ChipFilterView;
import com.rzc.isibox.presentation.component.chip.ChoiceModel;
import com.rzc.isibox.presentation.component.slider.ImageModel;
import com.rzc.isibox.presentation.component.slider.ImageSliderView;
import com.rzc.isibox.presentation.request.model.MyRequestDetailModel;
import com.rzc.isibox.presentation.request.view.ListQuotsView;
import com.rzc.isibox.presentation.request.view.RequestShareDialog;
import com.rzc.isibox.presentation.request.vm.RequestViewModel;
import com.rzc.isibox.tools.MyCurrency;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;
import java.util.Date;

public class DetailMyRequestActivity extends MyActivity {

    ImageView iv_back,ic_share;
    ImageSliderView slider_view;
    LinearLayout ln_value;
    ChipFilterView chip_view;
    TextView tv_name,tv_metric,tv_qty,tv_price,tv_description,tv_status;
    TextView tv_created,tv_address,tv_action;
    RelativeLayout rv_action;
    ListQuotsView view_quot;

    RequestViewModel requestViewModel;
    MyRequestDetailModel myRequestDetailModel;

    String mRequestId;

    @Override
    protected int setLayout() {
        return R.layout.request_activity_mydetail;
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
        tv_status    = findViewById(R.id.tv_status);
        view_quot    = findViewById(R.id.view_quot);

        tv_address.setText("");
        tv_status.setVisibility(View.GONE);
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
            dialog.show(ConfirmDialog.TYPE.RED,"Batalkan Pesanan","Apakah anda yakin ingin membatalkan pesanan anda?",R.drawable.icon_md_warning);
            dialog.showInputNote();
            dialog.setRequiredNote();
            dialog.setOnActionListener(new ConfirmDialog.OnActionListener() {
                @Override
                public void onProcess(String note) {
                    cancelNote(note);
                }

                @Override
                public void onCancel() {

                }
            });
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initialData() {
        mRequestId = getIntent().getStringExtra(Global.DATA);
        if (mRequestId == null){
            mActivity.finish();
            return;
        }

        ln_value.removeAllViews();

        requestViewModel = new ViewModelProvider(mActivity).get(RequestViewModel.class);
        requestViewModel.init(mActivity);
        requestViewModel.loadMyRequestDetail(mRequestId).observe(mActivity, myRequestDetailModel -> {
            if (myRequestDetailModel.getRequestID() == null){
                return;
            }

            this.myRequestDetailModel = myRequestDetailModel;

            tv_name.setText(myRequestDetailModel.getProductName());
            tv_metric.setText(myRequestDetailModel.getMetric());
            tv_qty.setText(String.valueOf(myRequestDetailModel.getQuantity()));
            tv_price.setText(MyCurrency.toCurrnecy("Rp", myRequestDetailModel.getTargetPrice()));
            tv_description.setText(myRequestDetailModel.getDescription());

            Date created = Utility.convert2Date(myRequestDetailModel.getRequestDate(), "yyyy-MM-dd HH:mm");
            String createdInfo = getResources().getString(R.string.date_created)+" : "+ Utility.getDateString(created,"dd MMM yyyy");
            tv_created.setText(createdInfo);

            buildInfoView("Kategori", myRequestDetailModel.getCategory());
            buildInfoView("Waktu Pengiriman",myRequestDetailModel.getDeliveryTime());
            buildInfoView("Pengiriman", myRequestDetailModel.getShippingMethod());
            buildInfoView("Referensi Website",myRequestDetailModel.getReferenceLink());
            buildInfoView("Pembayaran",myRequestDetailModel.getPaymentMethod());

            if (myRequestDetailModel.getProvince() != null){
                tv_address.setText(myRequestDetailModel.getCity()+", "+myRequestDetailModel.getProvince());
            }

            ArrayList<ChoiceModel>  keywords = new ArrayList<>();
            for (MyRequestDetailModel.Attributes attributes : myRequestDetailModel.getKeywords()){
                keywords.add(new ChoiceModel(attributes.getValue()));
                chip_view.create(keywords, 2, 1, R.color.grey3, R.color.white,R.color.black);
            }

            ArrayList<ImageModel> models = new ArrayList<>();
            for (MyRequestDetailModel.Attributes attributes : myRequestDetailModel.getImages()){
                String endPoint =  new EndpointURL(mActivity).getBaseUrl();
                Log.d(TAG,"attributes "+attributes.getValue());
                String url = endPoint + attributes.getValue().replace("../","");
                models.add(new ImageModel(url));

            }
            slider_view.create(getSupportFragmentManager(), models);

            RequestStatus status = RequestStatus.GetStatusById(myRequestDetailModel.getStatus());
            if (status == RequestStatus.CANCELED){
                rv_action.setVisibility(View.GONE);
                tv_status.setVisibility(View.VISIBLE);
                tv_status.setText(status.getName());
            }

            view_quot.create(mRequestId);
        });
    }

    private void cancelNote(String note){
        requestViewModel.cancelOrder(myRequestDetailModel.getRequestID(), note).observe(mActivity, apiResponse -> {
            if (apiResponse.getCode() == ErrorCode.OK_200){
                initialData();
            }
        });
    }

    private void buildInfoView(String key, String value){
        KeyValueView kView = new KeyValueView(mActivity, null);
        kView.create(key,value);
        ln_value.addView(kView);
    }
}
