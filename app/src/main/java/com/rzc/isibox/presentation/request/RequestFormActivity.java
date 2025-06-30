package com.rzc.isibox.presentation.request;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.rzc.isibox.R;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.data.OrderType;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.component.MyEdiText;
import com.rzc.isibox.presentation.component.option.OptionData;
import com.rzc.isibox.presentation.component.option.OptionDialog;
import com.rzc.isibox.presentation.request.model.RequestParamModel;
import com.rzc.isibox.presentation.request.view.ImageChooserView;
import com.rzc.isibox.presentation.request.vm.ImageViewModel;
import com.rzc.isibox.presentation.request.vm.RequestViewModel;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;

public class RequestFormActivity extends MyActivity {

    MyEdiText et_type, et_nameProduct,et_description,et_reference,et_qty,et_metric,et_price,et_category;
    ImageChooserView view_chooserImage;
    MyButton btn_next;

    RequestViewModel viewModel;
    ArrayList<OptionData> listCategory = new ArrayList<>();
    ArrayList<OptionData> listMetrik = new ArrayList<>();


    @Override
    protected int setLayout() {
        return R.layout.request_activity_request;
    }

    @Override
    protected void initLayout() {
        view_chooserImage = findViewById(R.id.view_chooserImage);
        view_chooserImage.create();

        et_nameProduct = findViewById(R.id.et_nameProduct);
        et_nameProduct.create(MyEdiText.TYPE.TEXT, getResources().getString(R.string.product_name));

        et_description = findViewById(R.id.et_description);
        et_description.create(MyEdiText.TYPE.MULTILINE, getResources().getString(R.string.description));
        et_description.setMinHeight(4);

        et_reference = findViewById(R.id.et_reference);
        et_reference.create(MyEdiText.TYPE.TEXT, getResources().getString(R.string.reference_link));

        et_qty = findViewById(R.id.et_qty);
        et_qty.create(MyEdiText.TYPE.NUMBER,"");

        et_metric = findViewById(R.id.et_metric);
        et_metric.create(MyEdiText.TYPE.SELECT,"");

        et_price = findViewById(R.id.et_price);
        et_price.create(MyEdiText.TYPE.CURRENCY,getResources().getString(R.string.price_target));

        et_category = findViewById(R.id.et_category);
        et_category.create(MyEdiText.TYPE.SELECT, getResources().getString(R.string.category));

        et_type = findViewById(R.id.et_type);
        et_type.create(MyEdiText.TYPE.SELECT, getResources().getString(R.string.request_type));

        btn_next = findViewById(R.id.btn_next);
        btn_next.create(MyButton.TYPE.PRIMARY, getResources().getString(R.string.next));

        registerFinishPage();

    }

    @Override
    protected void initListener() {
        findViewById(R.id.iv_back).setOnClickListener(v -> mActivity.finish());
        et_category.setOnActionListener(view -> openCategory());
        et_metric.setOnActionListener(view -> openMetrik());
        et_type.setOnActionListener(view -> openType());
        btn_next.setOnMyClickListener(view -> next());
    }

    @Override
    protected void initialData() {
        viewModel = new ViewModelProvider(mActivity).get(RequestViewModel.class);
        viewModel.init(mActivity);

        viewModel.listCategory().observe(mActivity, optionData -> listCategory.addAll(optionData));
        viewModel.listMetrik().observe(mActivity, optionData -> listMetrik.addAll(optionData));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        view_chooserImage.onActivityResult(requestCode, resultCode, data);
    }

    private void openCategory(){
        OptionDialog dialog = new OptionDialog(mActivity);
        dialog.show(getResources().getString(R.string.category));
        dialog.setOptionData(listCategory);
        dialog.setOnSelectedListener(data -> et_category.setValue(data.getValue()));
    }
    private void openMetrik(){
        OptionDialog dialog = new OptionDialog(mActivity);
        dialog.show(getResources().getString(R.string.metrik));
        dialog.setOptionData(listMetrik);
        dialog.setOnSelectedListener(data -> et_metric.setValue(data.getValue()));
    }

    private void openType(){
        ArrayList<OptionData> listType = new ArrayList<>();
        for (OrderType orderType : OrderType.values()){
            listType.add(new OptionData(orderType.getId(),orderType.getValue()));
        }
        OptionDialog dialog = new OptionDialog(mActivity);
        dialog.show(getResources().getString(R.string.request_type));
        dialog.setOptionData(listType);
        dialog.setOnSelectedListener(data -> {
            et_type.setValue(data.getValue());
            et_type.setObject(data);
        });
    }

    private void next(){
        if (isInvalidInput(et_type)){
            return;
        }
        if (isInvalidInput(et_nameProduct)){
            return;
        }
        if (isInvalidInput(et_description)){
            return;
        }
        if (isInvalidInput(et_qty)){
            return;
        }
        if (isInvalidInput(et_metric)){
            return;
        }
        if (isInvalidInput(et_price)){
            return;
        }
        if (isInvalidInput(et_category)){
            return;
        }
        if (view_chooserImage.getAllPhoto().isEmpty()){
            Utility.showAlertError(mActivity, "Silahkan pilih foto produk terlebih dahulu !");
            return;
        }
        if (view_chooserImage.getAllPhoto().size() > 6){
            Utility.showAlertError(
                    mActivity, "Maksimal 6 foto produk !");
            return;
        }

        OptionData optType = (OptionData) et_type.getObject();

        RequestParamModel model = new RequestParamModel();
        model.setProductName(et_nameProduct.getValue());
        model.setProductDescription(et_description.getValue());
        model.setQty(Integer.parseInt(et_qty.getValue()));
        model.setMetrik(et_metric.getValue());
        model.setPrice(Long.parseLong(et_price.getValue()));
        model.setCategory(et_category.getValue());
        model.setLink(et_reference.getValue());
        model.setImagesPath(view_chooserImage.getAllPhotoPath());
        model.setRequestType(optType.getId());

        Intent intent = new Intent(mActivity, RequestFormActivity2.class);
        intent.putExtra(Global.DATA, model);
        startActivity(intent);

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
