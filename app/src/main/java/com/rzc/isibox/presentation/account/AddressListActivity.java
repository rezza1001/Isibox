package com.rzc.isibox.presentation.account;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.connection.api.ErrorCode;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.presentation.account.adapter.AddressAdapter;
import com.rzc.isibox.presentation.account.model.CustomerAddressModel;
import com.rzc.isibox.presentation.account.model.ListAddressResp;
import com.rzc.isibox.presentation.account.vm.AddressViewModel;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.ConfirmDialog;
import com.rzc.isibox.presentation.component.EmptyView;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.component.option.OptionData;
import com.rzc.isibox.presentation.component.option.OptionDialog;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;
import java.util.Objects;

public class AddressListActivity extends MyActivity {

    private EmptyView empty_view;
    private RecyclerView rcv_data;
    private MyButton btn_add;

    AddressViewModel viewModel;
    ListAddressResp addressResp;

    AddressAdapter adapter;
    ArrayList<CustomerAddressModel> list = new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.account_activity_address_list;
    }

    @Override
    protected void initLayout() {

        empty_view = findViewById(R.id.empty_view);
        empty_view.create("Anda belum memiliki alamat pengiriman");

        rcv_data = findViewById(R.id.rcv_data);
        rcv_data.setLayoutManager(new LinearLayoutManager(mActivity));

        btn_add = findViewById(R.id.btn_add);
        btn_add.create(MyButton.TYPE.PRIMARY,"Tambah Alamat");

        adapter = new AddressAdapter(list);
        rcv_data.setAdapter(adapter);

        mRegisterReceiver(receiver, new IntentFilter(Global.BROADCAST_REFRESH));
    }

    @Override
    protected void initListener() {
        findViewById(R.id.iv_back).setOnClickListener(v -> mActivity.finish());
        btn_add.setOnMyClickListener(view -> {
            Intent intent = new Intent(mActivity, AddressAddActivity.class);
            startActivity(intent);
        });

        adapter.setOnActionListener(new AddressAdapter.OnActionListener() {
            @Override
            public void onChange(CustomerAddressModel data) {
                Intent intent = new Intent(mActivity, AddressAddActivity.class);
                intent.putExtra(Global.DATA, data);
                startActivity(intent);
            }

            @Override
            public void omMore(CustomerAddressModel data) {
                showMoreOption(data);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void initialData() {
        viewModel = new ViewModelProvider(mActivity).get(AddressViewModel.class);
        viewModel.init(mActivity);
        onLoadData();
    }

    private void showTaggingAlert(){
        ConfirmDialog dialog = new ConfirmDialog(mActivity);
        dialog.show(ConfirmDialog.TYPE.GREEN,"Penganturan Alamat","Alamat anda sudah terdaftar, Silahkan cek dan verifikasi ulang", 0);
        dialog.setTextButton("Verifikasi","Batal");
        dialog.setOnActionListener(new ConfirmDialog.OnActionListener() {
            @Override
            public void onProcess(String note) {
                CustomerAddressModel addressModel = addressResp.getList().get(0);
                Intent intent = new Intent(mActivity, AddressAddActivity.class);
                intent.putExtra(Global.DATA, addressModel);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }
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

    public void showMoreOption(CustomerAddressModel pData){
        OptionDialog dialog  = new OptionDialog(mActivity);
        dialog.show("Opsi");
        dialog.addOption(new OptionData("1","Jadikan Alamat Utama"));
        dialog.addOption(new OptionData("2","Hapus Alamat"));
        dialog.setOnSelectedListener(data -> {
            if (data.getId().equals("1")){
                switchToPrimary(pData);
            }
        });
    }

    private void switchToPrimary(CustomerAddressModel pData){
        viewModel.toPrimary(pData.getId()).observe(mActivity, apiResponse -> {
            if (apiResponse.getCode() == ErrorCode.OK_200){
                Utility.showToastSuccess(mActivity,"Data berhasil disimpan");
                onLoadData();
            }
            else {
                Utility.showAlertError(mActivity, apiResponse.getMessage());
            }
        });
    }


    @SuppressLint("NotifyDataSetChanged")
    private void onLoadData(){
        viewModel.loadAddress().observe(mActivity, listAddressResp -> {
            addressResp = listAddressResp;
            if (addressResp.isStatus() && !addressResp.isIsset()){ // TODO belum set tapi ada di geotaging
                showTaggingAlert();
                return;
            }
            if (!addressResp.isStatus() && !addressResp.isIsset()){ // TODO belum set dan tidak ada di geotaging
                showAddNewAlert();
                return;
            }

            list.clear();
            list.addAll(listAddressResp.getList());
            adapter.notifyDataSetChanged();
            empty_view.setVisibility(View.INVISIBLE);
        });
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), Global.BROADCAST_REFRESH)){
                onLoadData();
            }
        }
    };
}
