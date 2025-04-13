package com.rzc.isibox.presentation.request.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.connection.api.ApiResponse;
import com.rzc.isibox.connection.api.EndpointURL;
import com.rzc.isibox.connection.api.ErrorCode;
import com.rzc.isibox.connection.api.PostManager;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.master.MyViewModel;
import com.rzc.isibox.presentation.account.model.ListAddressResp;
import com.rzc.isibox.presentation.component.option.OptionData;
import com.rzc.isibox.presentation.request.model.MyRequestDetailModel;
import com.rzc.isibox.presentation.request.model.QuotesModel;
import com.rzc.isibox.presentation.request.model.RequestListModel;
import com.rzc.isibox.presentation.request.model.RequestParamModel;
import com.rzc.isibox.tools.Utility;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class RequestViewModel extends MyViewModel {
    public RequestViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<RequestListModel>> loadRequest(){
        MutableLiveData<ArrayList<RequestListModel>> liveData = new MutableLiveData<>();

        String endPoint =  new EndpointURL(mActivity).getBaseUrl();

        PostManager post = new PostManager(mActivity, VariableStatic.API_LIST_ORDER);
        post.addParam("type","list");
        post.exPost();
        post.setOnReceiveListener(apiResponse -> {
            if (apiResponse.getCode() == ErrorCode.OK_200){
                try {
                    ArrayList<RequestListModel> list = new ArrayList<>();
                    JSONArray ja = apiResponse.getData().getJSONArray("data");
                    for (int i=0; i< ja.length(); i++){
                        RequestListModel model = new RequestListModel().fromJson(ja.getJSONObject(i), RequestListModel.class);
                        model.setImage(endPoint + model.getImage());
                        list.add(model);
                    }
                    liveData.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return liveData;
    }

    public LiveData<MyRequestDetailModel> loadMyRequestDetail(String requestId){
        MutableLiveData<MyRequestDetailModel> liveData = new MutableLiveData<>();
        PostManager post = new PostManager(mActivity, VariableStatic.API_LIST_ORDER);
        post.addParam("type","detail");
        post.addParam("requestId",requestId);
        post.exPost();
        post.setOnReceiveListener(apiResponse -> {
            MyRequestDetailModel model = new MyRequestDetailModel();
            if (apiResponse.getCode() == ErrorCode.OK_200){
                try {
                    model = new MyRequestDetailModel().fromJson(apiResponse.getData().getJSONObject("data"), MyRequestDetailModel.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Utility.showAlertError(mActivity, apiResponse.getMessage());
            }
            liveData.postValue(model);
        });
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
        return loadProductAttribute("delivery time");
    }

    public LiveData<ArrayList<OptionData>> loadSendingMethod(){
        return loadProductAttribute("delivery method");
    }
    public LiveData<ArrayList<OptionData>> listPaymentMethod(){
        return loadProductAttribute("payment method");
    }
    public LiveData<ArrayList<OptionData>> listCategory(){
        return loadProductAttribute("category");
    }

    public LiveData<ArrayList<OptionData>> listMetrik(){
        return loadProductAttribute("metrik");
    }

    public MutableLiveData<ArrayList<OptionData>> loadProductAttribute(String type){
        MutableLiveData<ArrayList<OptionData>> liveData = new MutableLiveData<>();
        PostManager post = new PostManager(mActivity, VariableStatic.API_PRODUCT_ATTRIBUTE);
        post.addParam("type",type);
        post.exPost();
        post.setOnReceiveListener(apiResponse -> {
            if (apiResponse.getCode() == ErrorCode.OK_200){
                try {
                    ArrayList<OptionData> list = new ArrayList<>();
                    JSONArray ja = apiResponse.getData().getJSONArray("data");
                    for (int i=0; i< ja.length(); i++){
                        OptionData model = new OptionData().fromJson(ja.getJSONObject(i), OptionData.class);
                        if (model.getIconName() != null){
                            int res = Utility.getImageResourceId(mActivity, model.getIconName());
                            model.setIcon(res);
                        }
                        list.add(model);
                    }
                    liveData.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return liveData;
    }


    public LiveData<ApiResponse> requestOrder(RequestParamModel reqModel){
        MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();

        PostManager post = new PostManager(mActivity, VariableStatic.API_NEW_ORDER);
        post.setData(reqModel.toJson());
        post.exPost();
        post.setOnReceiveListener(liveData::postValue);

        return liveData;
    }

    public LiveData<ListAddressResp> loadAddress(){
        MutableLiveData<ListAddressResp> liveData = new MutableLiveData<>();
        PostManager post = new PostManager(mActivity, VariableStatic.MAIN_ADDRESS);
        post.addParam("type","check-address");
        post.exPost();
        post.setOnReceiveListener(apiResponse -> {
            try {
                ListAddressResp data = new ListAddressResp().fromJson(apiResponse.getData().getJSONObject("data"), ListAddressResp.class);
                liveData.postValue(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return liveData;
    }
}
