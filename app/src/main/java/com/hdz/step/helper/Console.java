package com.hdz.step.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.hdz.step.App;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class Console {
    private static final Handler EVENT_HANDLER = new Handler(Looper.getMainLooper()) {
    };

    private static boolean isDebug = true;

    public static boolean isDebug() {
        return isDebug;
    }

    public static void openDebug(boolean isDebug) {
        Console.isDebug = isDebug;
    }

    public static void setDebug(boolean isDebug) {
        Console.isDebug = isDebug;
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            if (msg == null)
                msg = "null";
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            if (msg == null)
                msg = "null";
            Log.w(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            if (msg == null)
                msg = "null";
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            if (msg == null)
                msg = "null";
            Log.e(tag, msg);
        }
    }

    public static void printStackTrace(Throwable e) {
        if (isDebug) {
            e.printStackTrace();
        }
    }

    public static void showToast(final String text) {
        if (isDebug) {
            EVENT_HANDLER.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        Toast.makeText(App.getInstance(), text, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Console.printStackTrace(e);
                    }
                }
            });
        }
    }

    public static void showToast(final Context context, final String text) {
        if (isDebug) {
            EVENT_HANDLER.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Console.printStackTrace(e);
                    }
                }
            });
        }
    }
}
