package com.dawnofneo.everdo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences mySharedPreferences = getSharedPreferences("mySharedPreferences",MODE_PRIVATE);
        final SharedPreferences loginSharedPreff = getSharedPreferences(getString(R.string.loginSharedPreferenceString),MODE_PRIVATE);
        final SharedPreferences normalLoginSharedPreff = getSharedPreferences(ApplicationConstants.NORMAL_LOGIN_SHERED_PREFF,MODE_PRIVATE);

        if(mySharedPreferences.getBoolean("first_time_launched",true)){
            Log.d("Tag","First Time launched."+String.valueOf(mySharedPreferences.getBoolean("first_time_launched",true)));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(SplashScreen.this,LoginActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            },3000);

            mySharedPreferences.edit().putBoolean("first_time_launched",false).commit();
        }
        else{
            Log.d("Tag"," NOT First Time launched."+String.valueOf(mySharedPreferences.getBoolean("first_time_launched",true)));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(!loginSharedPreff.getBoolean(getString(R.string.facebookloginCheckString),false)&&!normalLoginSharedPreff.getBoolean(ApplicationConstants.NORMAL_LOGIN_CHECK_STRING,false)){
                        Log.d("IN SPLASH IF", String.valueOf(loginSharedPreff.getBoolean(getString(R.string.facebookloginCheckString),true)));
                        Intent mainIntent;
                        mainIntent = new Intent(SplashScreen.this,LoginActivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
                    else {
                        Log.d("IN SPLASH ELSE fb", String.valueOf(loginSharedPreff.getBoolean(getString(R.string.facebookloginCheckString),true)));
                        Log.d("IN SPLASH ELSE normal", String.valueOf(normalLoginSharedPreff.getBoolean(ApplicationConstants.NORMAL_LOGIN_CHECK_STRING,true)));
                        Intent mainIntent;
                        mainIntent = new Intent(SplashScreen.this,Transition.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }

                }
            },1000);

        }
    }
}
