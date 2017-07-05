package com.hdz.step.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hdz.step.helper.Console;
import com.hdz.step.helper.DateFormatHelper;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class WalkDao {

    private static final String TAG = "WalkDao";

    private static final String TABLE_NAME = "master_walk";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "data";
    private static final String COLUMN_STEPS = "steps";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_STEPS + " INTEGER, "
            + COLUMN_DATE + " TEXT);";

    public long save(Walk walk) {
        try {
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            if (db.isOpen()) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_DATE, walk.getDate());
                values.put(COLUMN_STEPS, walk.getSteps());
                int result = db.update(TABLE_NAME, values, COLUMN_DATE + " = ?", new String[]{walk.getDate()});
                if (result == 0) {
                    db.insert(TABLE_NAME, null, values);
                }
                return result;
            }
        } catch (Exception e) {
            Console.printStackTrace(e);
        }
        return 0;
    }

    public Walk getTodayWalk() {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
            if (db.isOpen()) {
                cursor = db.query(TABLE_NAME, null, COLUMN_DATE + " = ?", new String[]{DateFormatHelper.formatData(DateFormatHelper.YYYY_MM_DD, System.currentTimeMillis())}, null, null, null);
                if (cursor.moveToFirst()) {
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                    int steps = cursor.getInt(cursor.getColumnIndex(COLUMN_STEPS));
                    return new Walk(date, steps);
                }
            }
        } catch (Exception e) {
            Console.printStackTrace(e);
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    Console.printStackTrace(e);
                }
            }
        }
        return null;
    }
}
