package com.rzc.isibox.presentation.auth.activity;

import android.content.Intent;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.lifecycle.ViewModelProvider;

import com.rzc.isibox.R;
import com.rzc.isibox.connection.api.ErrorCode;
import com.rzc.isibox.data.MyPreference;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.auth.viewmodel.AuthViewModel;
import com.rzc.isibox.presentation.component.AlertDialog;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.main.HomeActivity;
import com.rzc.isibox.tools.Utility;

public class LoginActivity extends MyActivity {

    private MyButton btn_login;
    private EditText et_email,et_password;
    private ImageView iv_icon;
    private RelativeLayout rv_icon;
    private AuthViewModel viewModel;
    @Override
    protected int setLayout() {
        return R.layout.auth_activtity_login;
    }

    @Override
    protected void initLayout() {
        btn_login = findViewById(R.id.btn_login);
        btn_login.create(MyButton.TYPE.PRIMARY_LIGHT,"LOG IN");
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        iv_icon = findViewById(R.id.iv_icon);
        rv_icon = findViewById(R.id.rv_icon);
        rv_icon.setTag("0");

        viewModel = new ViewModelProvider(mActivity).get(AuthViewModel.class);
        viewModel.init(mActivity);

    }

    @Override
    protected void initListener() {
        findViewById(R.id.tv_register).setOnClickListener(v -> {
            startActivity(new Intent(mActivity, RegisterActivity.class));
            mActivity.finish();
        });

        btn_login.setOnMyClickListener(view -> {

            login();
//            AlertDialog dialog = new AlertDialog(this);
//            dialog.showInfo("Email Verification Sent!","Please check your email, a link for verification will be sent to your email");
//            dialog.setOnSelectedListener(()->{
//                startActivity(new Intent(mActivity, HomeActivity.class));
//                mActivity.finish();
//            });
        });

        rv_icon.setOnClickListener(v->checkPassword());
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

    private void login(){
        String username = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (username.isEmpty()){
            Utility.showToastError(mActivity, "Email harus diisi!");
            return;
        }
        if (password.isEmpty()){
            Utility.showToastError(mActivity, "Password harus diisi!");
            return;
        }

        viewModel.loginAccount(username, password).observe(mActivity, apiResponse -> {
            if (apiResponse.getCode() == ErrorCode.OK_200){
                startActivity(new Intent(mActivity, HomeActivity.class));
                finish();
            }
            else {
                Utility.showAlertError(mActivity, apiResponse.getMessage());
            }
        });
    }


}
