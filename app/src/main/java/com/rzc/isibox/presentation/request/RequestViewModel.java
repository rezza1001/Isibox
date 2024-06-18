package com.rzc.isibox.presentation.request;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.tools.Utility;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class RequestViewModel extends MyViewModel {
    public RequestViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<RequestModel>> loadRequest(){
        MutableLiveData<ArrayList<RequestModel>> liveData = new MutableLiveData<>();

        String sData = Utility.loadJSONFromAsset(mActivity, "RequestDummy.json");
        try {
            ArrayList<RequestModel> list = new ArrayList<>();
            JSONArray ja = new JSONArray(sData);
            for (int i=0; i< ja.length(); i++){
                RequestModel model = new RequestModel().fromJson(ja.getJSONObject(i), RequestModel.class);
                list.add(model);
            }
            liveData.postValue(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return liveData;
    }

    public LiveData<ArrayList<QuotesModel>> loadQuotes(){
        MutableLiveData<ArrayList<QuotesModel>> liveData = new MutableLiveData<>();

        String sData = Utility.loadJSONFromAsset(mActivity, "QuotesDummy.json");
        try {
            ArrayList<QuotesModel> list = new ArrayList<>();
            JSONArray ja = new JSONArray(sData);
            for (int i=0; i< ja.length(); i++){
                QuotesModel model = new QuotesModel().fromJson(ja.getJSONObject(i), QuotesModel.class);
                list.add(model);
            }
            liveData.postValue(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return liveData;
    }
}
