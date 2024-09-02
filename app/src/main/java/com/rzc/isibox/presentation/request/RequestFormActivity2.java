package com.rzc.isibox.presentation.request;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.Loading;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.component.MyEdiText;
import com.rzc.isibox.presentation.component.option.OptionData;
import com.rzc.isibox.presentation.component.option.OptionDialog;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;

public class RequestFormActivity2 extends MyActivity {

    MyButton btn_next;
    TextView tv_sendingMethod;
    MyEdiText et_keyword,edt_timeSend,et_payment;
    ImageView iv_sendingMethod;

    RequestViewModel viewModel;

    ArrayList<OptionData> listSendingTime = new ArrayList<>();
    ArrayList<OptionData> listSendingMethod = new ArrayList<>();
    ArrayList<OptionData> listPaymentMethod = new ArrayList<>();
    @Override
    protected int setLayout() {
        return R.layout.request_activity_request2;
    }

    @Override
    protected void initLayout() {
        btn_next = findViewById(R.id.btn_next);
        btn_next.create(MyButton.TYPE.PRIMARY,"Buat Permintaan Sekarang");

        edt_timeSend = findViewById(R.id.edt_timeSend);
        edt_timeSend.create(MyEdiText.TYPE.SELECT,getResources().getString(R.string.sending_time));
        et_payment = findViewById(R.id.et_payment);
        et_payment.create(MyEdiText.TYPE.SELECT,getResources().getString(R.string.payment));

        tv_sendingMethod = findViewById(R.id.tv_sendingMethod);
        iv_sendingMethod = findViewById(R.id.iv_sendingMethod);

        et_keyword = findViewById(R.id.et_keyword);
        et_keyword.create(MyEdiText.TYPE.TEXT,getResources().getString(R.string.keywords));
        et_keyword.setTextInputSize(14);
    }

    @Override
    protected void initListener() {
        edt_timeSend.setOnActionListener(view -> openSendingTime());
        et_payment.setOnActionListener(view -> openPaymentMethod());
        findViewById(R.id.rv_sending).setOnClickListener(v -> openSendingMethod());
        findViewById(R.id.iv_back).setOnClickListener(v -> mActivity.finish());
        btn_next.setOnMyClickListener(view -> send());
    }

    @Override
    protected void initialData() {
        viewModel = new ViewModelProvider(mActivity).get(RequestViewModel.class);
        viewModel.init(mActivity);

        listSendingTime.clear();
        viewModel.loadSendingTime().observe(mActivity, optionData -> listSendingTime.addAll(optionData));

        listSendingMethod.clear();
        viewModel.loadSendingMothod().observe(mActivity, optionData -> listSendingMethod.addAll(optionData));

        listPaymentMethod.clear();
        viewModel.listPaymentMethod().observe(mActivity, optionData -> listPaymentMethod.addAll(optionData));
    }

    private void openSendingTime(){

        OptionDialog dialog = new OptionDialog(mActivity);
        dialog.show(getResources().getString(R.string.sending_time));
        dialog.setOptionData(listSendingTime);
        dialog.setOnSelectedListener(data -> edt_timeSend.setValue(data.getValue()));
    }
    private void openPaymentMethod(){
        OptionDialog dialog = new OptionDialog(mActivity);
        dialog.show(getResources().getString(R.string.payment));
        dialog.setOptionData(listPaymentMethod);
        dialog.setOnSelectedListener(data -> et_payment.setValue(data.getValue()));
    }

    private void openSendingMethod(){
        OptionDialog dialog = new OptionDialog(mActivity);
        dialog.show(getResources().getString(R.string.sending));
        dialog.setOptionData(listSendingMethod);
        dialog.setOnSelectedListener(data -> {
            tv_sendingMethod.setText(data.getValue());
            iv_sendingMethod.setImageResource(data.getIcon());
            iv_sendingMethod.setVisibility(View.VISIBLE);
        });
    }

    private void send(){
        Loading.showLoading(mActivity,"Please wait..");
        broadcastFinish();
        new Handler().postDelayed(() -> {
            Loading.cancelLoading();
            Utility.showToastSuccess(mActivity,"Berhasil di proses");
            mActivity.finish();
        },1000);
    }
}
