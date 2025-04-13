package com.rzc.isibox.presentation.account.vm;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzc.isibox.connection.api.ApiResponse;
import com.rzc.isibox.connection.api.PostManager;
import com.rzc.isibox.data.VariableStatic;
import com.rzc.isibox.presentation.account.model.CustomerAddressModel;
import com.rzc.isibox.presentation.account.model.GpsAddressModel;
import com.rzc.isibox.presentation.account.model.ListAddressResp;
import com.rzc.isibox.master.MyViewModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddressViewModel extends MyViewModel {
    public AddressViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<GpsAddressModel> loadAddress(double longitude, double latitude){
        MutableLiveData<GpsAddressModel> liveData = new MutableLiveData<>();
        Geocoder geocoder = new Geocoder(mActivity, new Locale("id","ID"));
        try {
            GpsAddressModel model = new GpsAddressModel();

            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                model.setLatitude(latitude);
                model.setLongitude(longitude);
                Address address = addresses.get(0);

                // Extract location details
                String addressLine = address.getAddressLine(0); // Full address
                String city = address.getSubAdminArea(); // City
                String state = address.getAdminArea(); // State
                String kec = address.getLocality().replace("Kecamatan ","") ;
                String kel = address.getSubLocality() ;
                String postalCode = address.getPostalCode(); // Postal code


                model.setAlamat(addressLine);
                model.setProvinsi(state);
                model.setKota(city);
                model.setKecamatan(kec);
                model.setKelurahan(kel);
            }
            liveData.postValue(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public LiveData<ApiResponse> addAddress(CustomerAddressModel model){
        MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        PostManager post = new PostManager(mActivity, VariableStatic.MAIN_ADDRESS);
        post.setData(model.toJson());
        post.addParam("type","add-address");
        post.exPost();
        post.setOnReceiveListener(liveData::postValue);

        return liveData;
    }

    public LiveData<ApiResponse> editAddress(CustomerAddressModel model, int addressId){
        MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        PostManager post = new PostManager(mActivity, VariableStatic.MAIN_ADDRESS);
        post.setData(model.toJson());
        post.addParam("addressId",addressId);
        post.addParam("type","edit-address");
        post.exPost();
        post.setOnReceiveListener(liveData::postValue);

        return liveData;
    }

    public LiveData<ApiResponse> toPrimary(int id){
        MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        PostManager post = new PostManager(mActivity, VariableStatic.MAIN_ADDRESS);
        post.addParam("type","primary");
        post.addParam("addressId",id);
        post.exPost();
        post.setOnReceiveListener(liveData::postValue);

        return liveData;
    }
}
