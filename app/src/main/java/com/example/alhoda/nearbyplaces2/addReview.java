package com.example.alhoda.nearbyplaces2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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


public class addReview extends AppCompatActivity {
    ImageView icon ;
     Button send;
     EditText reviewtext;
     RatingBar ratingbar;
     String username;
     String placename ;
    private static String URL_LOGIN = "http://192.168.1.2:8000/sendreview.php" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review_design);

        icon = (ImageView) findViewById(R.id.imageView);
        send = (Button)findViewById(R.id.send);
        reviewtext = (EditText)findViewById(R.id.reviewtext);
        ratingbar = (RatingBar) findViewById(R.id.ratingBar);

       // ratingbar.setRating(Float.parseFloat("4.5"));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
       username = prefs.getString("username", "no id");


        Intent intent = getIntent();
        placename=intent.getStringExtra("placename");





        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(addReview.this, username+" " +ratingbar.getRating(), Toast.LENGTH_LONG).show();

                sendReview(username,String.valueOf(ratingbar.getRating()),reviewtext.getText().toString(),placename);
            }
        });

    }







    private void sendReview(final String username, final String rating,final String review,final String placename) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        Toast.makeText(addReview.this, "Your review has been sent", Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(addReview.this, "Error 2" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("placename", placename);
                params.put("rating", rating);
                params.put("review", review);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


























}
