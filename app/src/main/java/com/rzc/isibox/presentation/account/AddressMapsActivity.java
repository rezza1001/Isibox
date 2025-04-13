package com.rzc.isibox.presentation.account;

import android.content.Intent;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.rzc.isibox.R;
import com.rzc.isibox.data.DataGPSModel;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.data.GpsVariable;
import com.rzc.isibox.data.MyPreference;
import com.rzc.isibox.master.MyActivity;
import com.rzc.isibox.presentation.account.model.GpsAddressModel;
import com.rzc.isibox.presentation.account.vm.AddressViewModel;
import com.rzc.isibox.presentation.component.MyButton;

public class AddressMapsActivity extends MyActivity {
    GoogleMap googleMap;
    MyButton btn_add;
    TextView tv_address;
    Marker myPositionMarker;
    LatLng currentLocation;
    private GpsAddressModel mAddress;

    AddressViewModel viewModel;

    @Override
    protected int setLayout() {
        return R.layout.account_activity_address_map;
    }

    @Override
    protected void initLayout() {

        btn_add = findViewById(R.id.btn_add);
        btn_add.create(MyButton.TYPE.DISABLE,"Set Lokasi");

        tv_address  = findViewById(R.id.tv_address);
        tv_address.setText("");


        setupMap();
    }

    @Override
    protected void initListener() {
        btn_add.setOnMyClickListener(view -> set());

        findViewById(R.id.ln_mylocation).setOnClickListener(view -> goToMyLocation());
        findViewById(R.id.iv_back).setOnClickListener(v -> mActivity.finish());
    }

    @Override
    protected void initialData() {
        viewModel = new ViewModelProvider(mActivity).get(AddressViewModel.class);
        viewModel.init(mActivity);
    }

    private void setupMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null){
            return;
        }
        mapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            goToMyLocation();
//
//            myPositionMarker = googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Lokasi Saya"));

            googleMap.setOnCameraIdleListener(() -> {
                LatLng center = googleMap.getCameraPosition().target;
                viewModel.loadAddress(center.longitude, center.latitude).observe(mActivity, gpsAddressModel -> {
                    String address = gpsAddressModel.getAlamat();
                    tv_address.setText(address);
                    mAddress = gpsAddressModel;
                    if (btn_add.getType() == MyButton.TYPE.DISABLE){
                        btn_add.setType(MyButton.TYPE.PRIMARY);
                    }
                });
            });
        });
    }

    private void goToMyLocation(){
        String sLastLocation = MyPreference.getString(mActivity, GpsVariable.LAST_LOCATION);
        DataGPSModel model = new DataGPSModel();
        model.unpack(sLastLocation);
        currentLocation = new LatLng(model.getLatitude(), model.getLongitude());

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        googleMap.setTrafficEnabled(false);
        googleMap.setBuildingsEnabled(false);
    }

    private void set(){
        if (mAddress == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(Global.DATA, mAddress);
        setResult(RESULT_OK, intent);
        mActivity.finish();

    }


}
