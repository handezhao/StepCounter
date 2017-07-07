package com.hdz.step.activity;

import android.app.Activity;
import android.os.Bundle;

import com.hdz.step.R;
import com.hdz.step.db.WalkDao;
import com.hdz.step.steps.SensorChangeListener;
import com.hdz.step.steps.StepCounter;
import com.hdz.step.widget.StepView;

public class MainActivity extends Activity {


    private SensorChangeListener sensorChangeListener;

    private StepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepView = (StepView) findViewById(R.id.step_view);

        sensorChangeListener = new SensorChangeListener() {
            @Override
            public void onSensorChanged(final int var, boolean useStepCounter) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stepView.setSteps(var, 2000);
                    }
                });
            }
        };
        StepCounter.getInstance().addSensorChangedListener(sensorChangeListener);

        WalkDao dao = new WalkDao();
        int todaySteps = dao.getTodayWalk().getSteps();
        sensorChangeListener.onSensorChanged(todaySteps, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StepCounter.getInstance().removeSensorChangedListener(sensorChangeListener);
    }
}
