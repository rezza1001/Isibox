package com.rzc.isibox.tools;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PermissionChecker {

    public static final int PERMISSION_STORAGE = 1;
    public static final int PERMISSION_BLUETOOTH = 2;

    public static boolean checkPermissionStorage(AppCompatActivity mActivity){
        String[] PERMISSIONS = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            PERMISSIONS = new String[]{
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_IMAGES};
        }
        if(hasPermissions(mActivity, PERMISSIONS)){
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, PERMISSION_STORAGE);
            return false;
        }
        return true;
    }

    public static boolean checkPermissionNfc(AppCompatActivity mActivity){
        String[] PERMISSIONS = new String[]{
                Manifest.permission.NFC};

        if(hasPermissions(mActivity, PERMISSIONS)){
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, PERMISSION_STORAGE);
            return false;
        }
        return true;

    }
    public static boolean checkPermissionReadContact(AppCompatActivity mActivity){
        String[] PERMISSIONS = new String[]{
                Manifest.permission.READ_CONTACTS};

        if(hasPermissions(mActivity, PERMISSIONS)){
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, PERMISSION_STORAGE);
            return false;
        }
        return true;

    }

    public static boolean checkBluetooth(AppCompatActivity mActivity){
        String[] PERMISSIONS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PERMISSIONS = new String[]{
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN};
        }
        else {
            PERMISSIONS = new String[]{
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH};
        }

        if(hasPermissions(mActivity, PERMISSIONS)){
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, PERMISSION_BLUETOOTH);
            return false;
        }
        return true;

    }


    public static boolean checkLocation(AppCompatActivity mActivity){
        String[] PERMISSIONS = new String[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PERMISSIONS = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};
        }

        if(hasPermissions(mActivity, PERMISSIONS)){
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, PERMISSION_BLUETOOTH);
            return false;
        }
        return true;

    }

    public static boolean checkNotification(AppCompatActivity mActivity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String[] PERMISSIONS = new String[]{Manifest.permission.POST_NOTIFICATIONS};

            if(hasPermissions(mActivity, PERMISSIONS)){
                ActivityCompat.requestPermissions(mActivity, PERMISSIONS, PERMISSION_STORAGE);
                return false;
            }
        }
        return true;


    }

    public static boolean checkCamera(AppCompatActivity mActivity) {
        String[] PERMISSIONS = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            PERMISSIONS = new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_IMAGES};
        }

        if (!hasPermissions(mActivity, PERMISSIONS)) {
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, PERMISSION_BLUETOOTH);
            return false;
        }
        return true;
    }

    public static  boolean hasPermissions(AppCompatActivity mActivity,  String... permissions) {
        boolean isValid  = true;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                Log.e("PERMISSION", permission+" = false");
                isValid = false;
            }
        }
        return isValid;
    }
}
