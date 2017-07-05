package com.hdz.step.steps;

import com.hdz.step.db.Walk;
import com.hdz.step.db.WalkDao;
import com.hdz.step.helper.Console;
import com.hdz.step.helper.DateFormatHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class StepCounter {

    private static final String TAG = "StepCounter";

    private static StepCounter instance;

    private Map<String, Walk> cache = new HashMap<String, Walk>();

    private ArrayList<SensorChangeListener> listeners = new ArrayList<>();

    private int stepIncrement = 0;

    private WalkDao walkDao = new WalkDao();

    private StepCounter() {
        Walk walk = walkDao.getTodayWalk();
        Console.d(TAG, "getTodayCount walk is " + walk);

        if (walk == null) {
        } else {
            cache.put(walk.getDate(), walk);
        }
    }

    public void addSensorChangedListener(SensorChangeListener listener) {
        if (listener == null || listeners.contains(listener)) return;
        listeners.add(listener);
    }

    public void removeSensorChangedListener(SensorChangeListener listener) {
        if (listener != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void increase(int step, boolean useStepCounter) {
        stepIncrement++;
        trigger(step, useStepCounter);
    }

    private void trigger(int step, boolean useStepCounter) {
        int count = getTodayCount();
        Console.d("StepCounterListener", "getTodayCount id is " + getTodayCount());
        if (useStepCounter) {
            //step表示的是增量
            count += step;
            Console.d("StepCounterListener", "count is " + count + " step is " + step);
        } else {
            count += stepIncrement;
            stepIncrement = 0;
        }

        String key = DateFormatHelper.formatData(DateFormatHelper.YYYY_MM_DD, System.currentTimeMillis());
        Walk walk = cache.get(key);
        if (walk == null) {
            walk = new Walk(key, count);
            cache.put(key, walk);
        }

        walk.setSteps(count);
        long result = walkDao.save(walk);
        Console.d(TAG, "result is " + result);

        for (SensorChangeListener listener : listeners) {
            listener.onSensorChanged(walk.getSteps(), useStepCounter);
        }
    }

    public int getTodayCount() {
        String key = DateFormatHelper.formatData(DateFormatHelper.YYYY_MM_DD, System.currentTimeMillis());
        Walk walk = cache.get(key);

        if (walk == null) {
            walk = walkDao.getTodayWalk();
            if (walk != null) {
                cache.put(walk.getDate(), walk);
            }
        }
        return walk == null ? 0 : walk.getSteps();
    }


    public static synchronized StepCounter getInstance() {
        if (instance == null) {
            instance = new StepCounter();
        }
        return instance;
    }

}
