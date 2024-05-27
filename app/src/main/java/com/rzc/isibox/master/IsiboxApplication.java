package com.rzc.isibox.master;

import android.app.Application;

public class IsiboxApplication extends Application {
    private static final String TAG = "OutletApplication";
    public static String TOKEN_FCM;
    @Override
    public void onCreate() {
        super.onCreate();

//
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                return;
//            }
//
//            String token = task.getResult();
//            Log.d(TAG, "Firebase Token "+ token);
//            TOKEN_FCM = token;
//        });
    }
}
