package com.rzc.isibox.presentation.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.connection.api.EndpointURL;
import com.rzc.isibox.connection.api.ErrorCode;
import com.rzc.isibox.connection.api.PostManager;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.tools.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainViewModel extends MyViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<MainModel>> loadProduct (){
        MutableLiveData<ArrayList<MainModel>> liveData = new MutableLiveData<>();
        ArrayList<MainModel> list = new ArrayList<>();

        String endPoint =  new EndpointURL(mActivity).getBaseUrl();

        PostManager post = new PostManager(mActivity, VariableStatic.API_HOME);
        post.addParam("type","main");
        post.exPost();
        post.setOnReceiveListener(apiResponse -> {
            if (apiResponse.getCode() == ErrorCode.OK_200){
                try {
                    JSONArray ja = apiResponse.getData().getJSONArray("data");
                    for (int i=0; i<ja.length(); i++){
                        JSONObject jo = ja.getJSONObject(i);
                        MainModel model = new MainModel().fromJson(jo, MainModel.class);
                        model.setProductImg(endPoint + model.getProductImg());
                        list.add(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            liveData.postValue(list);
        });
        return liveData;
    }
}
