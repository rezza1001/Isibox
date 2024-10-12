package com.rzc.isibox.presentation.auth.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.connection.api.ApiResponse;
import com.rzc.isibox.connection.api.PostManager;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.tools.Utility;

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
}
