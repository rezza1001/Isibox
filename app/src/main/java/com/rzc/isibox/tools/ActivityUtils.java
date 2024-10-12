package com.rzc.isibox.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ActivityUtils {
    public static void finishCurrentActivity(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(1);

            if (taskInfoList != null && !taskInfoList.isEmpty()) {
                ActivityManager.RunningTaskInfo runningTaskInfo = taskInfoList.get(0);
                assert runningTaskInfo.topActivity != null;
                String packageName = runningTaskInfo.topActivity.getPackageName();

                try {
                    // Get the class name of the currently active activity
                    Class<?> activityClass = Class.forName(runningTaskInfo.topActivity.getClassName());

                    // Finish the currently active activity
                    Activity activity = (Activity) context;
                    if (activity.getClass() == activityClass) {
                        activity.finish();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
