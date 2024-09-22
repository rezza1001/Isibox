package com.rzc.isibox.presentation.request;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.AlertDialog;
import com.rzc.isibox.presentation.component.ConfirmDialog;
import com.rzc.isibox.presentation.component.Loading;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.component.MyEdiText;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.presentation.component.chip.ChipFilterView;
import com.rzc.isibox.presentation.component.chip.ChoiceModel;
import com.rzc.isibox.presentation.component.option.OptionData;
import com.rzc.isibox.presentation.component.option.OptionDialog;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;

public class RequestFormActivity2 extends MyActivity {

    MyButton btn_next;
    TextView tv_sendingMethod;
    MyEdiText edt_timeSend,et_payment;
    EditText et_keyword;
    MyRelativeLayout rv_keyword,rv_tambah;
    ImageView iv_sendingMethod;

    ArrayList<String> listKeywords = new ArrayList<>();
    RequestViewModel viewModel;
    ChipFilterView chip_view;
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
        rv_keyword = findViewById(R.id.rv_keyword);
        rv_tambah = findViewById(R.id.rv_tambah);
        chip_view  = findViewById(R.id.chip_view);

        chip_view.create();


    }

    @Override
    protected void initListener() {
        edt_timeSend.setOnActionListener(view -> openSendingTime());
        et_payment.setOnActionListener(view -> openPaymentMethod());
        findViewById(R.id.rv_sending).setOnClickListener(v -> openSendingMethod());
        findViewById(R.id.iv_back).setOnClickListener(v -> mActivity.finish());
        btn_next.setOnMyClickListener(view -> showDialog());
        rv_tambah.setOnClickListener(v->{
            tambahChip(et_keyword.getText().toString());
            et_keyword.setText("");
        });
        et_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                    tambahChip(textView.getText().toString());
                    et_keyword.setText("");
                    Log.d("CACAOKAN","Enter pressed "+ textView.getText());
                }
                return false;
            }
        });

    }

    private void tambahChip(String text){
        if (text .isEmpty()){
            Utility.showToastError(mActivity,"Keyword tidak boleh kosong");

            return;
        }
        ChoiceModel model = new ChoiceModel();

        model.setName(text);

        chip_view.addChip(model);
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


    private void showDialog(){
        ConfirmDialog dialog = new ConfirmDialog(mActivity);
        dialog.show2(ConfirmDialog.TYPE.GREEN,"Verifikasi Nomor","Nomor Whatsapp yang terdaftar padaa akun ini adalah +62 08123013213123",R.drawable.icon_md_warning);
        dialog.setTextAction("Ganti Nomor","Lanjutkan");
        dialog.setOnActionListener(new ConfirmDialog.OnActionListener() {
            @Override
            public void onProcess(String note) {
                send();
            }

            @Override
            public void onProces2() {
                dialog.dismiss();
                changeNumberDialog();
            }
        });
    }

    private void changeNumberDialog(){
        ConfirmDialog dialogNo = new ConfirmDialog(mActivity);
        dialogNo.show(ConfirmDialog.TYPE.ORANGE,"Ganti Nomor","Masukkan No Whatsapp yang aktif",R.drawable.icon_md_warning);
        dialogNo.showInputNote();
        dialogNo.setHint("Nomor Whatsapp");
        dialogNo.setRequiredNote();
        dialogNo.setOnActionListener(new ConfirmDialog.OnActionListener() {
            @Override
            public void onProcess(String note) {
                send();
            }

            @Override
            public void onProces2() {
            }
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
