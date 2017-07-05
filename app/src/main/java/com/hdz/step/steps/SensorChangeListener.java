package com.hdz.step.steps;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public interface SensorChangeListener {

    /**
     * @param var 变化量
     * @param useStepCounter 是否使用Step_counter传感器
     */
    void onSensorChanged(int var, boolean useStepCounter);
}
