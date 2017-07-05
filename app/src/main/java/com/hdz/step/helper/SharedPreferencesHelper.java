package com.hdz.step.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.hdz.step.App;

import java.util.Set;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class SharedPreferencesHelper {

    private static String DEFAULT_PREFERENCE_NAME = "STEP_COUNTER_SP";
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    private SharedPreferencesHelper() {
    }

    public static void initialize() {
        Application application = App.getInstance();
        sp = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static boolean setBoolean(String key, boolean value) {
        return editor.putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public static boolean setString(String key, String value) {
        return editor.putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public static boolean setStringSet(String key, Set<String> value) {
        return editor.putStringSet(key, value).commit();
    }

    public static Set<String> getStringSet(String key, Set<String> defValue) {
        return sp.getStringSet(key, defValue);
    }

    public static boolean setInt(String key, int value) {
        return editor.putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static boolean setLong(String key, long value) {
        return editor.putLong(key, value).commit();
    }

    public static long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public static boolean setFloat(String key, float value) {
        return editor.putFloat(key, value).commit();
    }

    public static float getFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    /**
     *  unused.
     */
    static void clear() {
        editor.clear();
        editor.commit();
    }
}
