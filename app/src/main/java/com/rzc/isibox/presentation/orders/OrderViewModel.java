package com.rzc.isibox.presentation.orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.tools.Utility;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class OrderViewModel extends MyViewModel {
    public OrderViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<OrderModel>> loadMyOrder(){
        MutableLiveData<ArrayList<OrderModel>> liveData = new MutableLiveData<>();

        String sData = Utility.loadJSONFromAsset(mActivity, "OrderDummy.json");
        try {
            ArrayList<OrderModel> list = new ArrayList<>();
            JSONArray ja = new JSONArray(sData);
            for (int i=0; i< ja.length(); i++){
                OrderModel model = new OrderModel().fromJson(ja.getJSONObject(i), OrderModel.class);
                list.add(model);
            }
            liveData.postValue(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return liveData;
    }


}
