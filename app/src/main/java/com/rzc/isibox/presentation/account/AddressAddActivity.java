package com.rzc.isibox.presentation.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.rzc.isibox.R;
import com.rzc.isibox.connection.api.ErrorCode;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.account.model.CustomerAddressModel;
import com.rzc.isibox.presentation.account.model.GpsAddressModel;
import com.rzc.isibox.presentation.account.vm.AddressViewModel;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.component.MyEdiText;
import com.rzc.isibox.tools.PermissionChecker;
import com.rzc.isibox.tools.Utility;

public class AddressAddActivity extends MyActivity {

    private MyButton btn_add;
    private MyEdiText et_label,et_pin,et_phone,et_note;
    private TextView tv_address;
    private ImageView iv_check;

    AddressViewModel viewModel;

    GpsAddressModel gpsAddress;

    int addressId = 0;


    @Override
    protected int setLayout() {
        return R.layout.account_activity_address_add;
    }

    @Override
    protected void initLayout() {
        PermissionChecker.checkLocation(mActivity);

        btn_add = findViewById(R.id.btn_add);
        btn_add.create(MyButton.TYPE.PRIMARY,"Simpan");

        et_label = findViewById(R.id.et_label);
        et_label.create(MyEdiText.TYPE.TEXT,"Label");
        et_label.setTextInputSize(14);

        et_pin = findViewById(R.id.et_pin);
        et_pin.create(MyEdiText.TYPE.SELECT,"Pin Lokasi");
        et_pin.setRightIcon(R.drawable.ic_location_24, ContextCompat.getColor(mActivity, R.color.primary));
        et_pin.setTextInputSize(14);

        et_phone = findViewById(R.id.et_phone);
        et_phone.create(MyEdiText.TYPE.PHONE,"Nomor Hp");
        et_pin.setTextInputSize(14);

        et_note = findViewById(R.id.et_note);
        et_note.create(MyEdiText.TYPE.MULTILINE,"Catatan");
        et_note.setTextInputSize(14);

        tv_address = findViewById(R.id.tv_address);
        iv_check = findViewById(R.id.iv_check);
        iv_check.setTag(0);

    }

    @Override
    protected void initListener() {
        btn_add.setOnMyClickListener(view -> save());

        et_pin.setOnActionListener(view -> {
            Intent intent = new Intent(mActivity, AddressMapsActivity.class);
            mapsLauncher.launch(intent);
        });

        findViewById(R.id.ln_isPrimary).setOnClickListener(view -> switchPrimary());
        findViewById(R.id.iv_back).setOnClickListener(v -> mActivity.finish());

    }

    @Override
    protected void initialData() {
        viewModel = new ViewModelProvider(mActivity).get(AddressViewModel.class);
        viewModel.init(mActivity);

        CustomerAddressModel model = (CustomerAddressModel) getIntent().getSerializableExtra(Global.DATA);
        if (model != null){
            addressId = model.getId();
            et_label.setValue(model.getLabel());
            et_note.setValue(model.getNote());
            et_phone.setValue(model.getPhone());
            et_note.setValue(model.getNote());
            if (model.getPrimary() == 1){
                iv_check.setTag(0);
                switchPrimary();
            }
            viewModel.loadAddress(Double.parseDouble(model.getLongitude()), Double.parseDouble(model.getLatitude())).observe(mActivity, gpsAddressModel -> {
                et_pin.setValue(gpsAddressModel.getProvinsi()+", "+ gpsAddressModel.getKota());
                tv_address.setText(gpsAddressModel.getAlamat());
                gpsAddress = gpsAddressModel;
            });
        }
    }

    private final ActivityResultLauncher<Intent> mapsLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() == null){
                        return;
                    }
                    GpsAddressModel model = (GpsAddressModel) result.getData().getSerializableExtra(Global.DATA);
                    if (model == null){
                        return;
                    }
                    gpsAddress = model;
                    et_pin.setValue(model.getProvinsi()+", "+ model.getKota());
                    tv_address.setText(model.getAlamat());
                }
            });


    private void switchPrimary(){
        int tag = Integer.parseInt(iv_check.getTag().toString());
        if (tag == 0){
            tag = 1;
            iv_check.setImageResource(R.drawable.ic_checked);
        }
        else {
            tag = 0;
            iv_check.setImageResource(R.drawable.ic_unchecked);
        }
        iv_check.setTag(tag);
    }

    private void save(){
        String label = et_label.getValue();
        String phone = et_phone.getValue();
        String note = et_note.getValue();
        String address = tv_address.getText().toString();
        int isPrimary = Integer.parseInt(iv_check.getTag().toString());
        if (label.isEmpty()){
            Utility.showAlertError(mActivity, et_label.getErrorMessage());
            return;
        }
        if (address.isEmpty()){
            Utility.showAlertError(mActivity, et_pin.getErrorMessage());
            return;
        }
        if (phone.isEmpty()){
            Utility.showAlertError(mActivity, et_phone.getErrorMessage());
            return;
        }
        if (note.isEmpty()){
            Utility.showAlertError(mActivity, et_note.getErrorMessage());
            return;
        }

        CustomerAddressModel model = new CustomerAddressModel();
        model.setLabel(label);
        model.setAddress(address);
        model.setPhone(phone);
        model.setNote(note);
        model.setLongitude(gpsAddress.getLongitude()+"");
        model.setLatitude(gpsAddress.getLatitude()+"");
        model.setProvince(gpsAddress.getProvinsi());
        model.setCity(gpsAddress.getKota());
        model.setPrimary(isPrimary);

        if (addressId > 0){ // TODO EDIT
            viewModel.editAddress(model, addressId).observe(mActivity, apiResponse -> {
                if (apiResponse.getCode() == ErrorCode.OK_200){
                    Utility.showToastSuccess(mActivity, "Alamat berhasil ditambahkan");
                    sendBroadcast(new Intent(Global.BROADCAST_REFRESH));
                    new Handler().postDelayed(() -> mActivity.finish(),100);
                }
                else {
                    Utility.showAlertError(mActivity, apiResponse.getMessage());
                }
            });
            return;
        }

        viewModel.addAddress(model).observe(mActivity, apiResponse -> {
            if (apiResponse.getCode() == ErrorCode.OK_200){
                Utility.showToastSuccess(mActivity, "Alamat berhasil ditambahkan");
                sendBroadcast(new Intent(Global.BROADCAST_REFRESH));
                new Handler().postDelayed(() -> mActivity.finish(),100);
            }
            else {
                Utility.showAlertError(mActivity, apiResponse.getMessage());
            }
        });
    }

}
