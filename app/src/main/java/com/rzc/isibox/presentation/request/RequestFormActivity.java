package com.rzc.isibox.presentation.request;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.component.MyEdiText;
import com.rzc.isibox.presentation.component.option.OptionData;
import com.rzc.isibox.presentation.component.option.OptionDialog;

import java.util.ArrayList;

public class RequestFormActivity extends MyActivity {

    MyEdiText et_nameProduct,et_description,et_reference,et_qty,et_metric,et_price,et_category;
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

        btn_next = findViewById(R.id.btn_next);
        btn_next.create(MyButton.TYPE.PRIMARY, getResources().getString(R.string.next));

        registerFinishPage();

    }

    @Override
    protected void initListener() {
        findViewById(R.id.iv_back).setOnClickListener(v -> mActivity.finish());
        et_category.setOnActionListener(view -> openCategory());
        et_metric.setOnActionListener(view -> openMetrik());
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

    private void next(){
        Intent intent = new Intent(mActivity, RequestFormActivity2.class);
        startActivity(intent);
    }

}
