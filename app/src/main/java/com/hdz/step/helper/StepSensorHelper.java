package com.hdz.step.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class StepSensorHelper {

    private static StepSensorHelper instance;

    /**
     * Android版本号高于4.4
     * 有 TYPE_STEP_DETECTOR 传感器
     */
    public boolean canUseDetector(Context context) {
        if (Build.VERSION.SDK_INT >= 19 && hasDetectorSensor(context)) {
            return true;
        }
        return false;
    }

    public boolean canUseCounter(Context context) {
        if (Build.VERSION.SDK_INT >= 19 && hasCounterSensor(context)) {
            return true;
        }
        return false;
    }

    @TargetApi(19)
    private boolean hasCounterSensor(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER);
    }

    @TargetApi(19)
    private boolean hasDetectorSensor(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR);
    }


    public static StepSensorHelper getInstance() {
        if (instance == null) {
            instance = new StepSensorHelper();
        }
        return instance;
    }
}
