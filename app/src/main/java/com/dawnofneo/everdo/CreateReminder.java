package com.dawnofneo.everdo;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.dawnofneo.everdo.R.layout.activity_create_reminder;

public class CreateReminder extends AppCompatActivity {

    PlaceAutocompleteFragment locaitonPickerFragment;

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
                Snackbar.make(findViewById(R.id.create_reminder_layout),"selected "+place.getName()+", "+place.getLatLng(),Snackbar.LENGTH_LONG);
                Toast.makeText(CreateReminder.this, ""+ place.getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Status status) {
                Log.i("TAG", "An error occurred: " + status);

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
