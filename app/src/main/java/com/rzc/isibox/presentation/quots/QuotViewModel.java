package com.rzc.isibox.presentation.quots;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.connection.api.EndpointURL;
import com.rzc.isibox.connection.api.ErrorCode;
import com.rzc.isibox.connection.api.PostManager;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.presentation.quots.model.BidsUsersModel;
import com.rzc.isibox.presentation.quots.model.QuotesModel;
import com.rzc.isibox.presentation.request.model.UserBidding;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class QuotViewModel extends MyViewModel {
    public QuotViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<QuotesModel>> loadQuotes(){
        MutableLiveData<ArrayList<QuotesModel>> liveData = new MutableLiveData<>();

        String endPoint =  new EndpointURL(mActivity).getBaseUrl();

        PostManager post = new PostManager(mActivity, VariableStatic.API_BID);
        post.addParam("type","mybids");
        post.exPost();
        post.setOnReceiveListener(apiResponse -> {
            try {
                ArrayList<QuotesModel> list = new ArrayList<>();
                JSONArray ja = apiResponse.getData().getJSONArray("data");
                for (int i=0; i< ja.length(); i++){
                    QuotesModel model = new QuotesModel().fromJson(ja.getJSONObject(i), QuotesModel.class);
                    model.setImage(endPoint + model.getImage());
                    list.add(model);
                }
                liveData.postValue(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return liveData;
    }
    public LiveData<ArrayList<BidsUsersModel>> loadBids(String request){
        MutableLiveData<ArrayList<BidsUsersModel>> liveData = new MutableLiveData<>();

        PostManager post = new PostManager(mActivity, VariableStatic.API_BID);
        post.addParam("type","bidsRo");
        post.addParam("request_id",request);
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

    public LiveData<UserBidding> loadUserBid(int id){
        MutableLiveData<UserBidding> liveData = new MutableLiveData<>();
        PostManager post = new PostManager(mActivity, VariableStatic.API_BID);
        post.addParam("type","userbid");
        post.addParam("id",id);
        post.exPost();
        post.setOnReceiveListener(apiResponse -> {
            if (apiResponse.getCode() == ErrorCode.OK_200){
                UserBidding bid = null;
                try {
                    bid = new UserBidding().fromJson(apiResponse.getData().getJSONObject("data"), UserBidding.class);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                liveData.postValue(bid);
            }
        });
        return liveData;
    }


}
