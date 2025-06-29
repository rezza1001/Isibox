/*
 * Copyright (c) 2025. Rezza Developer
 */

package com.rzc.isibox.presentation.request.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.connection.api.PostManager;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.presentation.quots.model.BidsUsersModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class BidsViewModel extends MyViewModel {
    public BidsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<BidsUsersModel>> loadAll(String requestId ){
        MutableLiveData<ArrayList<BidsUsersModel>> liveData = new MutableLiveData<>();

        PostManager post = new PostManager(mActivity, VariableStatic.API_BID);
        post.addParam("type","bidsRo");
        post.addParam("request_id",requestId);
        post.exPost();
        post.setOnReceiveListener(apiResponse -> {
            try {
                ArrayList<BidsUsersModel> list = new ArrayList<>();
                JSONArray ja = apiResponse.getData().getJSONArray("data");
                for (int i=0; i< ja.length(); i++){
                    BidsUsersModel model = new BidsUsersModel().fromJson(ja.getJSONObject(i), BidsUsersModel.class);
                    list.add(model);
                }
                liveData.postValue(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return liveData;
    }
}
