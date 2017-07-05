package com.hdz.step.steps;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class AccelerometerSensor implements SensorEventListener {

    private SensorChangeListener listener;

    private int threshold = 12;

    private long lastTime;

    public void setSensorChangedListener(SensorChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        int sensorType = event.sensor.getType();
        if (Sensor.TYPE_ACCELEROMETER == sensorType) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if (x >= threshold || x <= -threshold || y >= threshold || y <= -threshold || z >= threshold || z <= -threshold) {
                long time = System.currentTimeMillis();

                if ((time - 450) > lastTime) {
                    lastTime = time;
                    listener.onSensorChanged(0, false);
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
