package com.example.alhoda.nearbyplaces2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login;
    private Button register;
    private static String URL_LOGIN = "http://192.168.1.2:8000/login1.php" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        register = findViewById(R.id.register);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                Log.d("onlogin1","beforestartact");
                startActivity(myIntent);
                Log.d("onlogin","loginsuccess");

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                    Login(mEmail, mPass);
                } else {
                    email.setError("Please insert email");
                    password.setError("Please insert password");
                }
            }
        });


    }

    private void Login(final String email, final String password) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        try {
                            // JSONArray jsonArray = new JSONArray(response);
                            //JSONObject obj = jsonArray.getJSONObject(0);
                            //Toast.makeText(LoginActivity.this, String.valueOf(obj), Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean success = jsonObject.getBoolean("success");
                            // JSONArray jsonArray = jsonObject.getJSONArray("");

                            if (success) {

                                Toast.makeText(LoginActivity.this, "succesful login", Toast.LENGTH_LONG).show();
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("username", email); //InputString: from the EditText
                                editor.commit();
                                Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                myIntent.putExtra("useremail", email);
                                startActivity(myIntent);


                            } else {
                                Toast.makeText(LoginActivity.this, "unsuccesful login", Toast.LENGTH_LONG).show();
                            }
                        }

                        catch (JSONException e) {
                            e.printStackTrace();


                            Toast.makeText(LoginActivity.this, "Error " +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(LoginActivity.this, "Error 2" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        Log.d("MyLoginrequest",  stringRequest.toString());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



}