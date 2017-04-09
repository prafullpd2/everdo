package com.dawnofneo.everdo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

import static com.dawnofneo.everdo.R.layout.activity_create_reminder;

public class CreateReminder extends AppCompatActivity implements TextView.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    PlaceAutocompleteFragment locaitonPickerFragment;
    TextView textView_start_date, textView_end_date, textView_start_time, textView_end_time, textView_notify_date, textView_notify_time;

    java.util.Calendar c;
    int hour;
    int min;
    int year;
    int month;
    int day;
    private String newDateInString;
    String startDate, endDate, notifyDate, startTime, endTime, notifyTime;
    long startDateTime, endDateTime, notifyDateTime;
    int id;
    private Time timeValue;


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
                Snackbar.make(findViewById(R.id.create_reminder_layout), "selected " + place.getName() + ", " + place.getLatLng(), Snackbar.LENGTH_LONG);
                Toast.makeText(CreateReminder.this, "" + place.getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Status status) {
                Log.i("TAG", "An error occurred: " + status);

            }
        });

        textView_start_date = (TextView) findViewById(R.id.textView_start_date);
        textView_end_date = (TextView) findViewById(R.id.textView_end_date);
        textView_start_time = (TextView) findViewById(R.id.textView_start_time);
        textView_end_time = (TextView) findViewById(R.id.textView_end_time);
        textView_notify_date = (TextView) findViewById(R.id.textView_notify_date);
        textView_notify_time = (TextView) findViewById(R.id.textView_notify_time);

        textView_start_date.setOnClickListener(this);
        textView_end_date.setOnClickListener(this);
        textView_start_time.setOnClickListener(this);
        textView_end_time.setOnClickListener(this);
        textView_notify_date.setOnClickListener(this);
        textView_notify_time.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        }

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

    private void checkForSuitableDateSelection() {
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

            EditText et = (EditText) findViewById(R.id.editText_task_overview);
            et.setText(String.valueOf(startDateTime));

            Toast.makeText(this, "start:" + startDateTime + ", end:" + endDateTime + ", notify:" + notifyDateTime, Toast.LENGTH_SHORT).show();

            if (notifyDateTime<startDateTime&&startDateTime<endDateTime){

            }
            else{
                Toast.makeText(this, "Date-Time is not set correctly", Toast.LENGTH_SHORT).show();
                textView_end_date.setText("Date");
                textView_end_time.setText("Time");
                textView_notify_date.setText("Date");
                textView_notify_time.setText("Time");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
