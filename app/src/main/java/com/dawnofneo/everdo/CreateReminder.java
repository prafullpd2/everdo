package com.dawnofneo.everdo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

import static com.dawnofneo.everdo.R.layout.activity_create_reminder;

public class CreateReminder extends AppCompatActivity implements TextView.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    PlaceAutocompleteFragment locaitonPickerFragment;
    TextView textView_start_date, textView_end_date, textView_start_time, textView_end_time, textView_notify_date, textView_notify_time;
    EditText taskOverView, subTasks;
    Button saveButton;
    ImageButton clearTimeDate, clearNotifyTimeDate;
    LatLng taskLatLang;

    String taskLocationName = null;
    java.util.Calendar c;
    int hour, min, year, month, day, id;
    private String newDateInString;
    String startDate, endDate, notifyDate, startTime, endTime, notifyTime;
    long startDateTime, endDateTime, notifyDateTime;
    private Time timeValue;
    CheckBox checkboxDateTime, checkboxNotifyDateTime, checkboxNotifyAt;
    int normalColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_create_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locaitonPickerFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.fragment_location_picker);
        locaitonPickerFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                Log.i("TAG", "Place: " + place.getName());
                taskLocationName = place.getName().toString();
                taskLatLang = place.getLatLng();
                Snackbar.make(findViewById(R.id.create_reminder_layout), "selected " + place.getName() + ", " + place.getLatLng(), Snackbar.LENGTH_LONG);
                Toast.makeText(CreateReminder.this, "" + place.getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Status status) {
                Log.i("TAG", "An error occurred: " + status);

            }
        });

        initContents();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void onCheckBoxClick(View view) {

        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {


            case R.id.set_time_checkBox:
                if (isChecked) {
                    textView_start_date.setClickable(true);
                    textView_start_time.setClickable(true);
                    textView_end_date.setClickable(true);
                    textView_end_time.setClickable(true);
                    textView_start_date.setTextColor(normalColor);
                    textView_start_time.setTextColor(normalColor);
                    textView_end_date.setTextColor(normalColor);
                    textView_end_time.setTextColor(normalColor);

                } else {

                    textView_start_date.setClickable(false);
                    textView_start_time.setClickable(false);
                    textView_end_date.setClickable(false);
                    textView_end_time.setClickable(false);

                    textView_start_date.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
                    textView_start_time.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
                    textView_end_date.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
                    textView_end_time.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
                }
                break;

            case R.id.notify_allDay_checkBox:
                if (isChecked) {
                    textView_notify_date.setClickable(false);
                    textView_notify_time.setClickable(false);
                    textView_notify_date.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
                    textView_notify_time.setTextColor(ContextCompat.getColor(this, R.color.disabledText));


                } else {
                    textView_notify_date.setClickable(true);
                    textView_notify_time.setClickable(true);
                    textView_notify_date.setTextColor(normalColor);
                    textView_notify_time.setTextColor(normalColor);

                }
                break;
            case R.id.checkbox_notifyAt:
                if (isChecked) {
                    textView_notify_date.setClickable(true);
                    textView_notify_time.setClickable(true);
                    checkboxNotifyDateTime.setClickable(true);

                    textView_notify_date.setTextColor(normalColor);
                    textView_notify_time.setTextColor(normalColor);
                    checkboxNotifyDateTime.setTextColor(Color.BLACK);

                } else {

                    textView_notify_date.setClickable(false);
                    textView_notify_time.setClickable(false);
                    checkboxNotifyDateTime.setClickable(false);

                    checkboxNotifyDateTime.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
                    textView_notify_date.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
                    textView_notify_time.setTextColor(ContextCompat.getColor(this, R.color.disabledText));


                }
                break;

        }

    }

    @Override
    public void onClick(View v) {
        id = v.getId();
        if (id == R.id.textView_start_date || id == R.id.textView_start_time
                || id == R.id.textView_end_date || id == R.id.textView_end_time
                || id == R.id.textView_notify_date || id == R.id.textView_notify_time) {
            c = java.util.Calendar.getInstance();
            hour = c.get(java.util.Calendar.HOUR_OF_DAY);
            min = c.get(java.util.Calendar.MINUTE);
            year = c.get(java.util.Calendar.YEAR);
            month = c.get(java.util.Calendar.MONTH);
            day = c.get(java.util.Calendar.DAY_OF_MONTH);
            makeDateTimeDialogue(id);
        } else if (id == R.id.imageButton_clearTimeDate) {

            textView_start_date.setText("Date");
            textView_end_date.setText("Date");
            textView_start_time.setText("Time");
            textView_end_time.setText("Time");


        } else if (id == R.id.imageButton_clearNotifyTimeDate) {

            textView_notify_date.setText("Date");
            textView_notify_time.setText("Time");

        } else if (id == R.id.saveButton) {

            if (checkDataForSaving()) {

                String taskOverview = taskOverView.getText().toString();
                String subtasks = subTasks.getText().toString();
                String notifyAllDay = "no";
                long startMilliSec = -1, endMilliSec = -1, notifyMilliSec = -1;
                String locationName = null;
                String locationLAT = null;
                String locationLANG = null;


                if (checkboxDateTime.isChecked() == true) {
                    startMilliSec = startDateTime;
                    endMilliSec = endDateTime;
                }
                if (checkboxNotifyAt.isChecked() == true) {
                    if (checkboxNotifyDateTime.isChecked())
                        notifyAllDay = "yes";
                    else
                        notifyMilliSec = notifyDateTime;
                }
                if (taskLocationName != null) {
                    locationName = taskLocationName;
                    locationLANG = String.valueOf(taskLatLang.longitude);
                    locationLAT = String.valueOf(taskLatLang.latitude);
                    Toast.makeText(this, "Location Perfect", Toast.LENGTH_SHORT).show();
                }

                ReminderDatabaseAdapter databaseAdapter = new ReminderDatabaseAdapter(getApplicationContext());
                long response = databaseAdapter.insertReminderData(taskOverview, subtasks,
                        String.valueOf(startMilliSec), String.valueOf(endMilliSec),
                        locationName, locationLAT, locationLANG, String.valueOf(notifyMilliSec), notifyAllDay);

                if (response <= 0)
                    Toast.makeText(this, "not Success", Toast.LENGTH_SHORT).show();
                else {

                    Intent moreInfoIntent = new Intent(getApplicationContext(), ReminderNotificationReceiver.class);
                    Toast.makeText(getApplicationContext(), "InComing Msg", Toast.LENGTH_SHORT).show();
                    moreInfoIntent.putExtra("CONTENT_TITLE", "New Task")
                            .putExtra("CONTENT_TEXT", taskOverview)
                            .putExtra("NOTIFICATION_ID", 1);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP,
                            notifyMilliSec,
                            PendingIntent.getBroadcast(getApplicationContext(), 0, moreInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT));

                    //instant notification
//                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                            .setContentTitle("New Task")
//                            .setContentText(taskOverview)
//                            .setTicker("Alert")
//                            .setSmallIcon(R.drawable.alert_icon);
//                    Intent notificationIntent = new Intent(this,Transition.class);
//                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
//                    taskStackBuilder.addParentStack(Transition.class);
//                    taskStackBuilder.addNextIntent(notificationIntent);
//
//                    PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
//
//                    notificationBuilder.setContentIntent(pendingIntent);
//                    notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
//                    notificationBuilder.setAutoCancel(true);
//
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.notify(1, notificationBuilder.build());

                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), Transition.class);
                    startActivity(intent);
                    finish();
                }


            } else
                Toast.makeText(this, "Something wrong is chosen!!!", Toast.LENGTH_SHORT).show();


        }

    }

    private boolean checkDataForSaving() {
        boolean valid = true;
        Date curDate = new Date();
        long curMillis = curDate.getTime();
        if (taskOverView.getText().toString().equals(""))
            valid = false;

//        else if ((checkboxNotifyAt.isChecked() == true && notifyDateTime <= curMillis))
//            valid = false;

        else if ((checkboxDateTime.isChecked() == true && startDateTime <= curMillis)
                || (checkboxDateTime.isChecked() == true && startDateTime >= endDateTime)
                || (checkboxDateTime.isChecked() == true && endDateTime <= curMillis)
                || (checkboxDateTime.isChecked() == true && endDateTime <= startDateTime))
            valid = false;

        else if (checkboxNotifyDateTime.isChecked() == true && checkboxDateTime.isChecked() == false)
            valid = false;
        else if ((checkboxDateTime.isChecked() == true && textView_start_date.getText().toString().equals("Date")) ||
                (checkboxDateTime.isChecked() == true && textView_end_date.getText().toString().equals("Date")) ||
                (checkboxNotifyAt.isChecked() == true && checkboxNotifyDateTime.isChecked() == false && textView_notify_date.getText().toString().equals("Date")))
            valid = false;

        return valid;
    }

    void makeDateTimeDialogue(int id) {

        //this.id = id;
        DatePickerDialog pickerDialog = new DatePickerDialog(CreateReminder.this, this, year, month, day);
        pickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        try {

            String dateInString = dayOfMonth + "/" + (month + 1) + "/" + year;
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            date = format.parse(dateInString);
            format = new java.text.SimpleDateFormat("dd/MMM/yyyy");
            newDateInString = format.format(date);
            if (id == R.id.textView_start_date || id == R.id.textView_start_time) {
                textView_start_date.setText(newDateInString);
                startDate = newDateInString;
            } else if (id == R.id.textView_end_date || id == R.id.textView_end_time) {
                textView_end_date.setText(newDateInString);
                endDate = newDateInString;
            } else if (id == R.id.textView_notify_date || id == R.id.textView_notify_time) {
                textView_notify_date.setText(newDateInString);
                notifyDate = newDateInString;
            }
            java.util.Calendar c = java.util.Calendar.getInstance();
            hour = c.get(java.util.Calendar.HOUR_OF_DAY);
            min = c.get(java.util.Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(CreateReminder.this, this, hour, min, android.text.format.DateFormat.is24HourFormat(this));
            timePickerDialog.show();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("error : ", e.toString());
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String dtStart = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm");

        try {
            timeValue = new Time(format.parse(dtStart).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (id == R.id.textView_start_date || id == R.id.textView_start_time) {
            textView_start_time.setText(String.valueOf(timeValue));
            startTime = timeValue.toString();
//            startDateTime = startDate+", "+startTime;
        } else if (id == R.id.textView_end_date || id == R.id.textView_end_time) {
            textView_end_time.setText(String.valueOf(timeValue));
            endTime = timeValue.toString();
//            endDateTime = endDate+", "+endTime;


        } else if (id == R.id.textView_notify_date || id == R.id.textView_notify_time) {
            textView_notify_time.setText(String.valueOf(timeValue));
            notifyTime = timeValue.toString();
//            notifyDateTime = notifyDate+", "+ notifyTime;

        }
        checkForSuitableDateSelection();


    }

    private boolean checkForSuitableDateSelection() {
        boolean validation = true;
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MMM/yyyy, HH:mm:ss");
        formatter.setLenient(false);
//        Date curDate = new Date();
//        long curMillis = curDate.getTime();
//        String curTime = formatter.format(curDate);
//        String oldTime = "05.01.2011, 12:45";
        Date date = null;
        try {
            date = formatter.parse(startDate + ", " + startTime);
            startDateTime = date.getTime();

            date = formatter.parse(endDate + ", " + endTime);
            endDateTime = date.getTime();

            date = formatter.parse(notifyDate + ", " + notifyTime);
            notifyDateTime = date.getTime();

            Toast.makeText(this, "start:" + startDateTime + ", end:" + endDateTime + ", notify:" + notifyDateTime, Toast.LENGTH_SHORT).show();

            if (notifyDateTime < startDateTime && startDateTime < endDateTime) {

                validation = true;
            } else {
                Toast.makeText(this, "Date-Time is not set correctly", Toast.LENGTH_SHORT).show();
                textView_end_date.setText("Date");
                textView_end_time.setText("Time");
                textView_notify_date.setText("Date");
                textView_notify_time.setText("Time");
                validation = false;
            }

            Date curDate = new Date();
            long curMillis = curDate.getTime();

            if (curMillis >= notifyDateTime) {
                Toast.makeText(this, "Notify time cant be less than Current Time!", Toast.LENGTH_SHORT).show();
                textView_notify_date.setText("Date");
                textView_notify_time.setText("Time");
                validation = false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return validation;
    }

    private void initContents() {
        textView_start_date = (TextView) findViewById(R.id.textView_start_date);
        textView_end_date = (TextView) findViewById(R.id.textView_end_date);
        textView_start_time = (TextView) findViewById(R.id.textView_start_time);
        textView_end_time = (TextView) findViewById(R.id.textView_end_time);
        textView_notify_date = (TextView) findViewById(R.id.textView_notify_date);
        textView_notify_time = (TextView) findViewById(R.id.textView_notify_time);
        taskOverView = (EditText) findViewById(R.id.editText_task_overview);
        subTasks = (EditText) findViewById(R.id.editText_subtasks);
        saveButton = (Button) findViewById(R.id.saveButton);

        checkboxDateTime = (CheckBox) findViewById(R.id.set_time_checkBox);
        checkboxNotifyDateTime = (CheckBox) findViewById(R.id.notify_allDay_checkBox);
        checkboxNotifyAt = (CheckBox) findViewById(R.id.checkbox_notifyAt);

        clearNotifyTimeDate = (ImageButton) findViewById(R.id.imageButton_clearNotifyTimeDate);
        clearTimeDate = (ImageButton) findViewById(R.id.imageButton_clearTimeDate);

        clearNotifyTimeDate.setOnClickListener(this);
        clearTimeDate.setOnClickListener(this);

        saveButton.setOnClickListener(this);

        textView_start_date.setOnClickListener(this);
        textView_end_date.setOnClickListener(this);
        textView_start_time.setOnClickListener(this);
        textView_end_time.setOnClickListener(this);
        textView_notify_date.setOnClickListener(this);
        textView_notify_time.setOnClickListener(this);
        normalColor = textView_notify_time.getCurrentTextColor();
        textView_start_date.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
        textView_start_time.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
        textView_end_date.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
        textView_end_time.setTextColor(ContextCompat.getColor(this, R.color.disabledText));
    }

}
