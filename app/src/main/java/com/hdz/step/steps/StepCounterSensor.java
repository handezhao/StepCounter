package com.hdz.step.steps;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.hdz.step.helper.Console;
import com.hdz.step.helper.DateFormatHelper;
import com.hdz.step.helper.SharedPreferencesHelper;

/**
 * Use google step_counter sensor
 * require target api 19 or higher
 * require hardware support
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class StepCounterSensor implements SensorEventListener {

    private String TAG = "StepCounterListener";

    /**
     * 每日首次启动APP时获取的传感器初始值
     */
    private static float initStep;

    /**
     * 上一次记录的数据
     */
    private static float lastRecord;


    private static String todayInitStepKey;
    private static String lastRecordKey;

    static {
        todayInitStepKey = getTodayInitStepKey();
        lastRecordKey = getLastRecordKey();
//        initStep = SharedPreferencesHelper.getFloat(todayInitStepKey, -1);
    }

    private SensorChangeListener listener;

    public void setSensorChangedListener(SensorChangeListener listener) {
        this.listener = listener;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        if (Sensor.TYPE_STEP_COUNTER == sensorType) {
            initStep = SharedPreferencesHelper.getFloat(todayInitStepKey, -1);
            float currentStep = event.values[0];
            Console.d(TAG, "currentStep is " + currentStep);
            float incrace = 0;
            //判断是否是初始值
            if (initStep >= 0) {
                //当前的步数大于上次的步数
                if (lastRecord <= 0) {
                    lastRecord = SharedPreferencesHelper.getFloat(lastRecordKey, 0);
                }
                Console.d(TAG, "lastRecord is " + lastRecord);
                if (currentStep >= lastRecord) {
                    incrace = currentStep - lastRecord;
                } else {
                    incrace = currentStep;
                }
            } else {
                SharedPreferencesHelper.setFloat(todayInitStepKey, currentStep);
            }
            Console.d(TAG, "incrace is " + incrace);
            lastRecord = currentStep;
            SharedPreferencesHelper.setFloat(lastRecordKey, currentStep);

            if (listener != null && incrace > 0) {
                listener.onSensorChanged((int) incrace, true);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private static String getTodayInitStepKey() {
        return DateFormatHelper.formatData(DateFormatHelper.YYYY_MM_DD, System.currentTimeMillis());
    }

    private static String getLastRecordKey() {
        return DateFormatHelper.formatData(DateFormatHelper.YYYY_MM_DD, System.currentTimeMillis()) + "last_record";
    }
}
