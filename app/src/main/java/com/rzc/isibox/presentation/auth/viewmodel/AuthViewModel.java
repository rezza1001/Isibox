package com.rzc.isibox.presentation.auth.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.connection.api.ApiResponse;
import com.rzc.isibox.connection.api.ErrorCode;
import com.rzc.isibox.connection.api.PostManager;
import com.rzc.isibox.connection.db.table.AccountDB;
import com.rzc.isibox.connection.db.table.AccountModel;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.tools.Utility;

import org.json.JSONException;

public class AuthViewModel extends MyViewModel {
    public AuthViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<ApiResponse> registerAccount(String name,String email, String phone, String address, String password){
        MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();

        PostManager post = new PostManager(mActivity, VariableStatic.API_REGISTER);
        post.addParam("email",email);
        post.addParam("phone",phone);
        post.addParam("name",name);
        post.addParam("address",address);
        post.addParam("password", Utility.ConvertPassword(password));
        post.exPost();
        post.setOnReceiveListener(liveData::postValue);
        return liveData;
    }

    public LiveData<ApiResponse> loginAccount(String username,String password){
        MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        PostManager post = new PostManager(mActivity, VariableStatic.API_LOGIN);
        post.addParam("username",username);
        post.addParam("password", Utility.ConvertPassword(password));
        post.exPost();
        post.setOnReceiveListener(apiResponse -> {
            if (apiResponse.getCode() == ErrorCode.OK_200){
                try {
                    AccountModel model = new AccountModel().fromJson(apiResponse.getData().getJSONObject("data"), AccountModel.class);
                    saveAccountData(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                    apiResponse.setCode(ErrorCode.FAILED);
                    apiResponse.setMessage(e.getMessage());
                }
            }
            liveData.postValue(apiResponse);
        });

        return liveData;
    }

    private void saveAccountData(AccountModel model){
        Log.d("KOCAK", model.getUser_id());
        AccountDB accountDB = new AccountDB();
        accountDB.model = model;
        accountDB.insert(mActivity);

        Log.d("KOCAK",accountDB.model.getName()+"," +accountDB.model.getUser_id());


    }
}
