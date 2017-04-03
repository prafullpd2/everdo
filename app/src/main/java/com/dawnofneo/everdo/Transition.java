package com.dawnofneo.everdo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;

public class Transition extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ScheduleFragment.OnFragmentInteractionListener,ReminderFragment.OnFragmentInteractionListener {

    SharedPreferences loginSharedPreff,normalLoginSharedPreff;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ReminderFragment reminderFragment= new ReminderFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_transition_fragment_holder, reminderFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
         if (id == R.id.nav_reminder) {

             ReminderFragment reminderFragment= new ReminderFragment();
             fragmentTransaction = fragmentManager.beginTransaction();
             fragmentTransaction.replace(R.id.content_transition_fragment_holder, reminderFragment);
             fragmentTransaction.commit();
        } else if (id == R.id.nav_schedule) {
             ScheduleFragment scheduleFragment= new ScheduleFragment();
             fragmentTransaction = fragmentManager.beginTransaction();
             fragmentTransaction.replace(R.id.content_transition_fragment_holder, scheduleFragment);
             fragmentTransaction.commit();
        } else if (id == R.id.logout) {

            LoginManager.getInstance().logOut();
            loginSharedPreff = getSharedPreferences(getString(R.string.loginSharedPreferenceString), Context.MODE_PRIVATE);
            loginSharedPreff.edit().putBoolean(getString(R.string.facebookloginCheckString),false).commit();
            normalLoginSharedPreff = getSharedPreferences(ApplicationConstants.NORMAL_LOGIN_SHERED_PREFF,Context.MODE_PRIVATE);
            normalLoginSharedPreff.edit().putBoolean(ApplicationConstants.NORMAL_LOGIN_CHECK_STRING,false).commit();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            Transition.this.startActivity(intent);
            Transition.this.finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
