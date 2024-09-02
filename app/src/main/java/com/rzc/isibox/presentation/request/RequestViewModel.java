package com.rzc.isibox.presentation.request;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.presentation.component.option.OptionData;
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

    public LiveData<ArrayList<OptionData>> loadSendingTime(){
        MutableLiveData<ArrayList<OptionData>> liveData = new MutableLiveData<>();
        String sData = Utility.loadJSONFromAsset(mActivity, "SendingTimeDummy.json");
        try {
            ArrayList<OptionData> list = new ArrayList<>();
            JSONArray ja = new JSONArray(sData);
            for (int i=0; i< ja.length(); i++){
                OptionData model = new OptionData().fromJson(ja.getJSONObject(i), OptionData.class);
                list.add(model);
            }
            liveData.postValue(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return liveData;
    }

    public LiveData<ArrayList<OptionData>> loadSendingMothod(){
        MutableLiveData<ArrayList<OptionData>> liveData = new MutableLiveData<>();
        String sData = Utility.loadJSONFromAsset(mActivity, "SendingMethodDummy.json");
        try {
            ArrayList<OptionData> list = new ArrayList<>();
            JSONArray ja = new JSONArray(sData);
            for (int i=0; i< ja.length(); i++){
                OptionData model = new OptionData().fromJson(ja.getJSONObject(i), OptionData.class);
                int res = Utility.getImageResourceId(mActivity, model.getIconName());
                model.setIcon(res);
                list.add(model);
            }
            liveData.postValue(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return liveData;
    }
    public LiveData<ArrayList<OptionData>> listPaymentMethod(){
        MutableLiveData<ArrayList<OptionData>> liveData = new MutableLiveData<>();
        String sData = Utility.loadJSONFromAsset(mActivity, "PayementDummy.json");
        try {
            ArrayList<OptionData> list = new ArrayList<>();
            JSONArray ja = new JSONArray(sData);
            for (int i=0; i< ja.length(); i++){
                OptionData model = new OptionData().fromJson(ja.getJSONObject(i), OptionData.class);
                list.add(model);
            }
            liveData.postValue(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return liveData;
    }
    public LiveData<ArrayList<OptionData>> listCategory(){
        MutableLiveData<ArrayList<OptionData>> liveData = new MutableLiveData<>();
        String sData = Utility.loadJSONFromAsset(mActivity, "CategoryDummy.json");
        try {
            ArrayList<OptionData> list = new ArrayList<>();
            JSONArray ja = new JSONArray(sData);
            for (int i=0; i< ja.length(); i++){
                OptionData model = new OptionData().fromJson(ja.getJSONObject(i), OptionData.class);
                list.add(model);
            }
            liveData.postValue(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return liveData;
    }

    public LiveData<ArrayList<OptionData>> listMetrik(){
        MutableLiveData<ArrayList<OptionData>> liveData = new MutableLiveData<>();
        String sData = Utility.loadJSONFromAsset(mActivity, "MetrikDummy.json");
        try {
            ArrayList<OptionData> list = new ArrayList<>();
            JSONArray ja = new JSONArray(sData);
            for (int i=0; i< ja.length(); i++){
                OptionData model = new OptionData().fromJson(ja.getJSONObject(i), OptionData.class);
                list.add(model);
            }
            liveData.postValue(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return liveData;
    }
}
