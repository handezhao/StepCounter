package com.hdz.step.steps;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import com.hdz.step.App;
import com.hdz.step.helper.Console;
import com.hdz.step.helper.StepSensorHelper;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class StepCounterService extends Service {

    public static final String TAG = "WalkService";

    private boolean flag;

    private StepCounterSensor stepCounterSensor;

    private AccelerometerSensor accelerometerSensor;

    private SensorManager sensorManager;

    private IBinder binder = new WalkBinder();

    private SensorChangeListener sensorChangeListener = new SensorChangeListener() {
        @Override
        public void onSensorChanged(int var, boolean useStepCounter) {
            Console.d(TAG, "change is " + var + " ,useStepCounter " + useStepCounter);
            StepCounter stepCounter = StepCounter.getInstance();
            stepCounter.increase(var, useStepCounter);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        Console.d(TAG, "oncreate");
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (StepSensorHelper.getInstance().canUseCounter(App.getInstance())) {
            Console.d(TAG, "useCounter");
            useCounter();
            if (!flag) {
                Console.d(TAG, "use counter fail ");
                useAccelerometer();
            }
        } else {
            useAccelerometer();
        }
        Console.d(TAG, "canUseCounter " + StepSensorHelper.getInstance().canUseCounter(this));
        Console.d(TAG, "Is sensor supported and successfully ? " + flag);
    }

    private void useAccelerometer() {
        accelerometerSensor = new AccelerometerSensor();
        accelerometerSensor.setSensorChangedListener(sensorChangeListener);
        flag = sensorManager.registerListener(accelerometerSensor, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @TargetApi(19)
    private void useCounter() {
        stepCounterSensor = new StepCounterSensor();
        stepCounterSensor.setSensorChangedListener(sensorChangeListener);
        flag = sensorManager.registerListener(stepCounterSensor, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void onDestroy() {
        Console.d(TAG, "onDestroy");
        super.onDestroy();
        try {
            if (accelerometerSensor != null) {
                sensorManager.unregisterListener(accelerometerSensor);
            }
            if (stepCounterSensor != null) {
                sensorManager.unregisterListener(stepCounterSensor);
            }
            stopForeground(true);
        } catch (Exception e) {
            Console.printStackTrace(e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Console.d(TAG, "onStartCommand");
//        StepNotificationHelper stepNotificationHelper = new StepNotificationHelper(this);
//        startForeground(StepNotificationHelper.STEP_NOTIFY_ID, stepNotificationHelper.getStepNotification());
        return Service.START_STICKY;
    }

    public class WalkBinder extends Binder {
        public StepCounterService getService() {
            return StepCounterService.this;
        }
    }
}
