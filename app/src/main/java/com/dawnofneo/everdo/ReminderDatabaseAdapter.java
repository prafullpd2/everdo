package com.dawnofneo.everdo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.END_TIME_DATE;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.IS_SYNCED;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.LOCATION_LANG;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.LOCATION_LAT;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.LOCATION_NAME;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.NOTIFY_ALL_DAY;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.NOTIFY_TIME_DATE;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.SERVER_ID;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.START_TIME_DATE;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.SUBTASKS;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.TABLE_NAME;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.TASK_OVERVIEW;
import static com.dawnofneo.everdo.ReminderDatabaseAdapter.ReminderDatabaseHelper.UID_LOCAL;


/**
 * Created by Prafull on 15-Apr-17.
 */
public class ReminderDatabaseAdapter {

    ReminderDatabaseHelper helper;
    Context xContext;
    ReminderDatabaseAdapter(Context context){
        xContext = context;
        helper = new ReminderDatabaseHelper(context);
    }

    public long insertReminderData(String taskOverview,String subtasks,
                                   String startTimeDate,String endTimeDate,
                                   String locationName,String locationLAT,String locationLANG,
                                   String notifyTimeDate,String isNotifyAllDay){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_OVERVIEW,taskOverview);
        values.put(SUBTASKS,subtasks);
        values.put(START_TIME_DATE,startTimeDate);
        values.put(END_TIME_DATE,endTimeDate);
        values.put(LOCATION_NAME,locationName);
        values.put(LOCATION_LANG,locationLANG);
        values.put(LOCATION_LAT,locationLAT);
        values.put(NOTIFY_TIME_DATE,notifyTimeDate);
        values.put(NOTIFY_ALL_DAY,isNotifyAllDay);
        values.put(IS_SYNCED,"no");
        values.put(SERVER_ID,-1);
        long id = -1;
        try {
            id = db.insert(TABLE_NAME,null,values);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(xContext, ""+e, Toast.LENGTH_SHORT).show();
        }
        return id;
    }

    public List<ReminderElements> getAllReminderDataList(){
        SQLiteDatabase db  = helper.getWritableDatabase();
        String[] columns = {TASK_OVERVIEW,SUBTASKS,START_TIME_DATE,END_TIME_DATE,
        LOCATION_NAME,LOCATION_LANG,LOCATION_LAT,NOTIFY_TIME_DATE,NOTIFY_ALL_DAY,
        IS_SYNCED,SERVER_ID,UID_LOCAL};
        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);
        int taskOverviewIndex = cursor.getColumnIndex(TASK_OVERVIEW);
        int localIDIndex = cursor.getColumnIndex(UID_LOCAL);
        int serverIDIndex = cursor.getColumnIndex(SERVER_ID);
        int subtasksIndex = cursor.getColumnIndex(SUBTASKS);
        int startDateTimeIndex = cursor.getColumnIndex(START_TIME_DATE);
        int endDateTimeIndex = cursor.getColumnIndex(END_TIME_DATE);
        int notifyDateTimeIndex = cursor.getColumnIndex(NOTIFY_TIME_DATE);
        int locationNameIndex = cursor.getColumnIndex(LOCATION_NAME);
        int locationLATIndex = cursor.getColumnIndex(LOCATION_LAT);
        int locationLANGIndex = cursor.getColumnIndex(LOCATION_LANG);
        int isSyncedIndex = cursor.getColumnIndex(IS_SYNCED);
        int notifyAllDayIndex = cursor.getColumnIndex(NOTIFY_ALL_DAY);

        int i = 0;

        List<ReminderElements> elementsList = new ArrayList<>();
        while (cursor.moveToNext()){
            ReminderElements element = new ReminderElements();
            element.taskOverview =  cursor.getString(taskOverviewIndex);
            element.subtasks =  cursor.getString(subtasksIndex);
            element.localID =  cursor.getString(localIDIndex);
            element.serverID =  cursor.getString(serverIDIndex);
            element.notifyAt =  cursor.getString(notifyDateTimeIndex);
            element.taskLocation =  cursor.getString(locationNameIndex);
            element.startAt =  cursor.getString(startDateTimeIndex);
            element.endAt =  cursor.getString(endDateTimeIndex);
            element.deleteTask =  "DELETE";
            element.isDone =  cursor.getString(notifyAllDayIndex);
            elementsList.add(i,element);
            i++;
        }
        return elementsList;

    }

    public int deleteReminder(int localID){
        SQLiteDatabase db = helper.getWritableDatabase();
        int result = db.delete(TABLE_NAME,UID_LOCAL+"=?",new String[]{String.valueOf(localID)});
    return result;
    }

    static class ReminderDatabaseHelper extends SQLiteOpenHelper {
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

        static final String CREATE_DB_QUERY = "CREATE TABLE REMINDER_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
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
                "is_synced VARCHAR(5));";

        static final int DATABASE_VERSION = 1;

        public ReminderDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_DB_QUERY);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}