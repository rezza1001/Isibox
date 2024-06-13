package com.rzc.isibox.presentation.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

        String sData = Utility.loadJSONFromAsset(mActivity, "dummyData.json");
        try {
            JSONArray ja = new JSONArray(sData);
            for (int i=0; i<ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                MainModel model = new MainModel().fromJson(jo, MainModel.class);
                list.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        liveData.postValue(list);

        return liveData;
    }
}
