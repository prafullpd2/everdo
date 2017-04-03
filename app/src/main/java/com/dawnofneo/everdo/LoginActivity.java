package com.dawnofneo.everdo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    LoginButton facebookLoginButton;
    CallbackManager callbackManager;
    AccessToken accessToken;
    LoginResult fbLoginResult;
    static SharedPreferences loginSharedPreff;
    static SharedPreferences normalLoginSharedPreff;
    Button signInButton,registerOptionButton;
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeContent();

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")||password.getText().equals(""))
                    Toast.makeText(LoginActivity.this, "Fill all options", Toast.LENGTH_SHORT).show();
                else
                attemptSignIn();
            }


        });

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                Log.d("FB Tag","Success");
                loginSharedPreff.edit().putBoolean(getString(R.string.facebookloginCheckString),true).commit();
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,Transition.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login canceled", Toast.LENGTH_SHORT).show();
                Log.d("FB Tag","cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "FB Exception", Toast.LENGTH_SHORT).show();
                Log.d("FB Tag","exception");
            }
        });
        registerOptionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

    }

    private void attemptSignIn() {

        final String emailString = email.getText().toString().trim();
        final String passwordString = password.getText().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://everdo-practicingbee.rhcloud.com/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                if(response.contains("success")){
                    normalLoginSharedPreff.edit().putBoolean(ApplicationConstants.NORMAL_LOGIN_CHECK_STRING,true).commit();
                    Intent intent = new Intent(LoginActivity.this,Transition.class);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response",error.toString());
                error.printStackTrace();
            }
        }){
            //params
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",emailString);
                params.put("password",passwordString);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void initializeContent() {
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        loginSharedPreff = getSharedPreferences(getString(R.string.loginSharedPreferenceString),MODE_PRIVATE);
        normalLoginSharedPreff = getSharedPreferences(ApplicationConstants.NORMAL_LOGIN_SHERED_PREFF,MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        facebookLoginButton.setText("Use Facebook Login");
        ArrayList<String> facebookPermissions = new ArrayList<>();
        facebookPermissions.add("user_status");
        facebookPermissions.add("email");
        facebookPermissions.add("user_birthday");
        facebookPermissions.add("user_events");
        facebookPermissions.add("public_profile");
        facebookLoginButton.setReadPermissions(facebookPermissions);
        registerOptionButton = (Button) findViewById(R.id.register_option_button);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        email = (EditText) findViewById(R.id.email_signIn_editText);
        password= (EditText) findViewById(R.id.password_signIn_editText);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}

