package com.rzc.isibox.presentation.auth.activity;

import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.auth.viewmodel.AuthViewModel;
import com.rzc.isibox.presentation.component.AlertDialog;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.main.HomeActivity;
import com.rzc.isibox.tools.Utility;

public class RegisterActivity extends MyActivity {

    private MyButton btn_signup;
    private RelativeLayout rv_icon;
    private EditText et_name,et_email,et_phone,et_address,et_password;
    private ImageView iv_icon;
    private AuthViewModel viewModel;
    @Override
    protected int setLayout() {
        return R.layout.auth_activtity_register;
    }

    @Override
    protected void initLayout() {
        viewModel = new ViewModelProvider(mActivity).get(AuthViewModel.class);
        viewModel.init(mActivity);

        btn_signup = findViewById(R.id.btn_signup);
        rv_icon = findViewById(R.id.rv_icon);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.et_address);
        et_password = findViewById(R.id.et_password);
        iv_icon = findViewById(R.id.iv_icon);
        btn_signup.create(MyButton.TYPE.PRIMARY_LIGHT,"SIGN UP");
        rv_icon.setTag("0");

    }

    @Override
    protected void initListener() {
        findViewById(R.id.tv_signIn).setOnClickListener(v -> handleBackPress());

        btn_signup.setOnMyClickListener(view -> {
            register();
        });
        rv_icon.setOnClickListener(v->checkPassword());

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handleBackPress();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void handleBackPress() {
        startActivity(new Intent(mActivity, LoginActivity.class));
        mActivity.finish();
    }

    private void checkPassword(){
        if(rv_icon.getTag().equals("0")){
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            iv_icon.setImageResource(R.drawable.ic_eye_on);
            iv_icon.setColorFilter(R.color.primary);
            rv_icon.setTag("1");
        }else if(rv_icon.getTag().equals("1")){
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            iv_icon.setImageResource(R.drawable.ic_eye_off);
            iv_icon.setColorFilter(R.color.primary);
            rv_icon.setTag("0");
        }
    }

    private void  register(){
        if(et_name.getText().toString().isEmpty()){
            Utility.showToastError(mActivity,"Mohon isi field name");
            return;
        }
        if(et_email.getText().toString().isEmpty()){
            Utility.showToastError(mActivity,"Mohon isi field email");
            return;
        }
        if(et_phone.getText().toString().isEmpty()){
            Utility.showToastError(mActivity,"Mohon isi field phone");
            return;
        }

//        if(et_address.getText().toString().isEmpty()){
//            Utility.showToastError(mActivity,"Mohon isi field address");
//            return;
//        }

        if(et_password.getText().toString().isEmpty()){
            Utility.showToastError(mActivity,"Mohon isi field password");
            return;
        }


        viewModel.registerAccount(et_name.getText().toString(),et_email.getText().toString(),et_phone.getText().toString(),"",et_password.getText().toString()).observe(mActivity,apiResponse -> {
            if(apiResponse.getCode() == 200){
                AlertDialog dialog = new AlertDialog(this);
                dialog.show("Berhasil",apiResponse.getMessage()+". Silahkan login dengan akun yang terdaftar");
                dialog.setOnSelectedListener(()->{
                    startActivity(new Intent(mActivity, LoginActivity.class));
                    mActivity.finish();
                });
            }else{
                Utility.showAlertError(mActivity,apiResponse.getMessage());
            }
        });
    }

}
