package com.dawnofneo.everdo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    EditText first_name,last_name,email,password;
    String first_name_string,last_name_string,email_string,password_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize_components();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
        });
    }

    private void requestRegister() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://everdo-practicingbee.rhcloud.com/register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                if(response.contains("successfully registered")){


                    Intent intent = new Intent(RegisterActivity.this,Transition.class);
                    RegisterActivity.this.startActivity(intent);
                    RegisterActivity.this.finish();
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
                params.put("first_name",first_name_string);
                params.put("last_name",last_name_string);
                params.put("email",email_string);
                params.put("password",password_string);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    private void attemptRegistration() {
        first_name_string = first_name.getText().toString().trim();
        last_name_string = last_name.getText().toString().trim();
        email_string = email.getText().toString().trim();
        password_string = password.getText().toString().trim();
        if(first_name_string.equals("")||last_name_string.equals("")||email_string.equals("")||password_string.equals("")){
            Toast.makeText(this, "Fill all options", Toast.LENGTH_SHORT).show();
        }

        else
           requestRegister();

    }

    void initialize_components(){
        registerButton = (Button) findViewById(R.id.registration_button);
        first_name = (EditText) findViewById(R.id.first_name_editText);
        last_name = (EditText) findViewById(R.id.last_name_editText);
        email = (EditText) findViewById(R.id.register_email_editText);
        password = (EditText) findViewById(R.id.register_passwort_editText);
    }
}
