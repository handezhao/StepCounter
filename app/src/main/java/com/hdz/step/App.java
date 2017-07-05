package com.hdz.step;

import android.app.Application;
import android.content.Intent;

import com.hdz.step.helper.SharedPreferencesHelper;
import com.hdz.step.steps.StepCounterService;

/**
 * Description:
 * Created by hdz on 2017/7/3.
 */

public class App extends Application {


    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = App.this;
        SharedPreferencesHelper.initialize();
        this.startService(new Intent(App.this, StepCounterService.class));
    }

}
