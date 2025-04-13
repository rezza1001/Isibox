package com.rzc.isibox.presentation.request;

import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.rzc.isibox.R;
import com.rzc.isibox.connection.api.ErrorCode;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.account.AddressAddActivity;
import com.rzc.isibox.presentation.account.model.CustomerAddressModel;
import com.rzc.isibox.presentation.component.ConfirmDialog;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.component.MyEdiText;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.presentation.component.chip.ChipFilterView;
import com.rzc.isibox.presentation.component.chip.ChoiceModel;
import com.rzc.isibox.presentation.component.option.OptionData;
import com.rzc.isibox.presentation.component.option.OptionDialog;
import com.rzc.isibox.presentation.request.model.RequestParamModel;
import com.rzc.isibox.presentation.request.view.AddOptionDialog;
import com.rzc.isibox.presentation.request.vm.ImageViewModel;
import com.rzc.isibox.presentation.request.vm.RequestViewModel;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;

public class RequestFormActivity2 extends MyActivity {

    MyButton btn_next;
    TextView tv_sendingMethod, tv_address;
    MyEdiText edt_timeSend,et_payment, et_address;
    EditText et_keyword;
    MyRelativeLayout rv_keyword,rv_tambah;
    ImageView iv_sendingMethod;
    ChipFilterView chip_view;
    ArrayList<OptionData> listSendingTime = new ArrayList<>();
    ArrayList<OptionData> listSendingMethod = new ArrayList<>();
    ArrayList<OptionData> listPaymentMethod = new ArrayList<>();

    RequestParamModel requestParam;

    RequestViewModel viewModel;
    ImageViewModel imageViewModel;

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

        et_address = findViewById(R.id.et_address);
        et_address.create(MyEdiText.TYPE.SELECT, "Alamat");
        tv_address = findViewById(R.id.tv_address);

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

        et_keyword.setOnEditorActionListener((textView, i, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                tambahChip(textView.getText().toString());
                et_keyword.setText("");
            }
            return false;
        });

        et_address.setOnActionListener(view -> checkAddress());

    }

    @Override
    protected void initialData() {
        requestParam = (RequestParamModel) getIntent().getSerializableExtra(Global.DATA);

        viewModel = new ViewModelProvider(mActivity).get(RequestViewModel.class);
        viewModel.init(mActivity);

        imageViewModel = new ViewModelProvider(mActivity).get(ImageViewModel.class);
        imageViewModel.init(mActivity);

        listSendingTime.clear();
        viewModel.loadSendingTime().observe(mActivity, optionData -> listSendingTime.addAll(optionData));

        listSendingMethod.clear();
        viewModel.loadSendingMethod().observe(mActivity, optionData -> listSendingMethod.addAll(optionData));

        listPaymentMethod.clear();
        viewModel.listPaymentMethod().observe(mActivity, optionData -> listPaymentMethod.addAll(optionData));
    }


    private void tambahChip(String text){
        if (text .isEmpty()){
            Utility.showToastError(mActivity,"Keyword tidak boleh kosong");
            return;
        }
        ChoiceModel model = new ChoiceModel();
        model.setName(text);
        model.setKey(text);
        chip_view.addChip(model);
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
            public void onCancel() {
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
            public void onCancel() {
            }
        });

    }
    private void send(){
        if (isInvalidInput(edt_timeSend)){
            return;
        }
        if (isInvalidInput(et_payment)){
            return;
        }

        String delivMethod = tv_sendingMethod.getText().toString().trim();
        if (delivMethod.isEmpty()){
            Utility.showAlertError(mActivity, "Metode Pengiriman Harus diisi");
            return;
        }

        CustomerAddressModel addressModel = (CustomerAddressModel) et_address.getObject();
        if (addressModel == null){
            Utility.showAlertError(mActivity, "Alamat pengiriman harus diisi");
            return;
        }

        imageViewModel.buildBase64(requestParam.getImagesPath()).observe(mActivity, strings -> {

            requestParam.setAddress(addressModel);
            requestParam.setImages(strings);
            requestParam.setDeliveryTime(edt_timeSend.getValue());
            requestParam.setPaymentMethod(et_payment.getValue());
            requestParam.setDeliveryMethod(delivMethod);
            requestParam.setKeywords(chip_view.getDataArrayList());

            viewModel.requestOrder(requestParam).observe(mActivity, apiResponse -> {
                broadcastFinish();

                if (apiResponse.getCode() == ErrorCode.OK_200){
                    Utility.showToastSuccess(mActivity,apiResponse.getMessage());
                    new Handler().postDelayed(() -> mActivity.finish(),1000);
                }
                else {
                    Utility.showAlertError(mActivity,apiResponse.getMessage());
                }
            });
        });
    }

    private void checkAddress(){
        viewModel.loadAddress().observe(mActivity, listAddressResp -> {
            if (!listAddressResp.isStatus()){
                showAddNewAlert();
                return;
            }
            showAddressOption(listAddressResp.getList());
        });
    }

    private void showAddressOption(ArrayList<CustomerAddressModel> listAddressModel){
        AddOptionDialog dialog = new AddOptionDialog(mActivity);
        dialog.show(listAddressModel, model -> {
            et_address.setValue(model.getLabel());
            et_address.setObject(model);
            tv_address.setText(model.getAddress());
        });
    }

    private void showAddNewAlert(){
        ConfirmDialog dialog = new ConfirmDialog(mActivity);
        dialog.show(ConfirmDialog.TYPE.GREEN,"Penganturan Alamat","Alamat anda belum terdaftar, Silahkan tambah alamat anda", 0);
        dialog.setTextButton("Tambah","Batal");
        dialog.setOnActionListener(new ConfirmDialog.OnActionListener() {
            @Override
            public void onProcess(String note) {
                Intent intent = new Intent(mActivity, AddressAddActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }
        });

    }


    private boolean isInvalidInput(MyEdiText et){
        if (et.getType() == MyEdiText.TYPE.CURRENCY || et.getType() == MyEdiText.TYPE.NUMBER){
            if (et.getValue().equals("0")){
                Utility.showAlertError(mActivity, et.getErrorMessage());
                return true;
            }
        }
        else {
            if (et.getValue().isEmpty()){
                Utility.showAlertError(mActivity, et.getErrorMessage());
                return true;
            }
        }
        return false;
    }
}
