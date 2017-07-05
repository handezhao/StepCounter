package com.hdz.step.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hdz.step.App;
import com.hdz.step.helper.Console;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "step_dao";

    private static DBHelper instance;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper(App.getInstance());
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(WalkDao.SQL_CREATE_TABLE);

        } catch (Exception e) {
            Console.printStackTrace(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void dismiss() {
        instance = null;
    }

    public void closeDatabase() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.close();
        } catch (Exception e) {
            Console.printStackTrace(e);
        }
    }
}
