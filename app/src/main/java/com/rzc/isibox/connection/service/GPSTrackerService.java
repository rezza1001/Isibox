package com.rzc.isibox.connection.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.rzc.isibox.data.DataGPSModel;
import com.rzc.isibox.data.Global;
import com.rzc.isibox.data.GpsVariable;
import com.rzc.isibox.data.MyPreference;

import java.util.Date;

public class GPSTrackerService extends Service {
    private static final String TAG = "GPSTrackerService";

    Location mLocation; // location
    double latitude; // latitude
    double longitude; // longitude
    private static final long MIN_TIME_BW_UPDATES = 1000 ; // 1 minute


    @Override
    public void onCreate() {
        super.onCreate();

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, MIN_TIME_BW_UPDATES)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(MIN_TIME_BW_UPDATES)
                .setMaxUpdateDelayMillis(MIN_TIME_BW_UPDATES)
                .setGranularity(Granularity.GRANULARITY_FINE)
                .setDurationMillis(Long.MAX_VALUE)
                .build();

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    mLocation = location;
                    Log.d(TAG, "Lat: " + location.getLatitude() + ", Long: " + location.getLongitude() +" Alt : "+ location.getAltitude());

                    String note = "Provider "+location.getProvider();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        if (location.isMock()){
                            note = "FAKE GPS";
                        }
                    }else {
                        if ( location.isFromMockProvider()){
                            note = "FAKE GPS";
                        }
                    }

                    DataGPSModel dataGPSModel = new DataGPSModel();
                    dataGPSModel.setTime(new Date());
                    dataGPSModel.setNote(note);
                    dataGPSModel.setLongitude(location.getLongitude());
                    dataGPSModel.setLatitude(location.getLatitude());
//        -6.8482625,107.9289133
//        dataGPSModel.setLatitude(-6.559133);
//        dataGPSModel.setLongitude(107.761156);

                    dataGPSModel.setAltitude(location.hasAltitude() ? location.getAltitude() : 0.0);
                    MyPreference.save(getApplicationContext(), GpsVariable.LAST_LOCATION, dataGPSModel.pack().toString()); // SAVE LAST LOCATION

                    Intent intent = new Intent(Global.BROADCAST_GPS);
                    intent.putExtra(GpsVariable.DATA, dataGPSModel);
                    sendBroadcast(intent);
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG,"Permission Denied");
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }



    public double getLatitude() {
        if (mLocation != null) {
            latitude = mLocation.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (mLocation != null) {
            longitude = mLocation.getLongitude();
        }
        return longitude;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
