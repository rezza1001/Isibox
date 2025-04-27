package com.rzc.isibox.presentation.quots;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.presentation.quots.model.QuotesModel;
import com.rzc.isibox.tools.Utility;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class OrderViewModel extends MyViewModel {
    public OrderViewModel(@NonNull Application application) {
        super(application);
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
