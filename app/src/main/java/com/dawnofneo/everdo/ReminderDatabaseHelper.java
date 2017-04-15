package com.dawnofneo.everdo;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by Prafull on 15-Apr-17.
 */

public class ReminderDatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "reminder.db";
    static final String TABLE_NAME = "REMINDER_TABLE";
    static final String UID_LOCAL = "_id";
    static final String TASK_OVERVIEW = "task_overview";
    static final String SUBTASKS = "subtasks";
    static final String START_TIME_DATE = "start_time_date";
    static final String END_TIME_DATE = "end_time_date";
    static final String NOTIFY_TIME_DATE = "notify_time_date";
    static final String NOTIFY_ALL_DAY = "notify_all_day";
    static final String LOCATION_NAME = "location_name";
    static final String LOCATION_LAT = "location_lat";
    static final String LOCATION_LANG = "location_lang";
    static final String SERVER_ID = "server_id";
    static final String IS_SYNCED = "is_synced";

    static final int DATABASE_VERSION = 1;

    public ReminderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE REMINDER_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "task_overview VARCHAR(150)," +
                    "subtasks VARCHAR(400)," +
                    "start_time_date VARCHAR(20)," +
                    "end_time_date VARCHAR(20)," +
                    "notify_time_date VARCHAR(20)," +
                    "notify_all_day VARCHAR(5)," +
                    "location_name VARCHAR(30)," +
                    "location_lat VARCHAR(30)," +
                    "location_lang VARCHAR(30)," +
                    "server_id INTEGER," +
                    "is_synced VARCHAR(5));");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
