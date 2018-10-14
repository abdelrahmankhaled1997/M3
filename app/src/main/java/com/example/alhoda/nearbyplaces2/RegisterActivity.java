package com.example.alhoda.nearbyplaces2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText email, password,phone,age,nation;
    private EditText name_ ;
    private Button reg_btn;
    private static String URL_LOGIN = "http://192.168.1.5:8000/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        age = findViewById(R.id.age);
        phone = findViewById(R.id.phone);
        nation = findViewById(R.id.nation);
        name_ = findViewById(R.id.name);
        reg_btn = findViewById(R.id.reg_btn);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();
                String mPhone = phone.getText().toString().trim();
                String mAge = age.getText().toString().trim();
                String mName = name_.getText().toString().trim();
                String mNation = nation.getText().toString().trim();

                if (mEmail.isEmpty() || mPass.isEmpty()|| mPhone.isEmpty() || mAge.isEmpty() || mName.isEmpty() || mNation.isEmpty() ) {
                    email.setError("Please insert email");
                    password.setError("Please insert password");
                    age.setError("Please insert age");
                    phone.setError("Please insert phone");
                    nation.setError("Please insert nationality");
                    name_.setError("Please insert name");
                }
                else{
                    reg(mEmail, mPass,mPhone,mAge,mName,mNation); }

            }
        });

    }

    private void reg(final String email, final String password,final String phone, final String age,final String name, final String nation) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        Toast.makeText(RegisterActivity.this, "Your account has been created", Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                        myIntent.putExtra("useremail", email);
                        startActivity(myIntent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(RegisterActivity.this, "Error 2" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("name", name);
                params.put("age", age);
                params.put("nation", nation);
                params.put("phone", phone);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
