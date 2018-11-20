package com.example.alhoda.nearbyplaces2;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class places extends AppCompatActivity {
    private static String URL_LOGIN = "http://192.168.1.2:8000/getreviews.php";

    ImageView placeimage ;
    ImageView logoutimg ;
    ImageView showonmapimg ;
    ImageView showdistanceimg ;
    ImageView callusimg ;
    ImageView addreviewimg ;
    ImageView showreviewsimg ;

    String landmarkimage;
    String  placerating ;
    String latitude;
    String longitude;
    String landmarkname ;
    String distance ;
    String phone;

    Bitmap decodedByte ;
    TextView placename ;


    Button callusbtn;
    Button reviewbtn;
    Button showdistancebtn;
    Button showreviewsbtn;
    Button showonmapbtn;
    Button logoutbtn;

    RatingBar ratingbar;

    TextView welcome ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        callusbtn =findViewById(R.id.callus);
        reviewbtn =findViewById(R.id.addreview);
        showdistancebtn =findViewById(R.id.distance);
        showreviewsbtn =findViewById(R.id.showreviews);
        logoutbtn =(Button)findViewById(R.id.logout);
        showonmapbtn =findViewById(R.id.showonmap);

        placeimage =findViewById(R.id.placeimage);
        logoutimg =findViewById(R.id.logoutimg);
        showdistanceimg =findViewById(R.id.distanceimg);
        showonmapimg =findViewById(R.id.mapimg);
        callusimg =findViewById(R.id.callusimg);
        addreviewimg =findViewById(R.id.reviewimg);
        showreviewsimg =findViewById(R.id.showimg);

        ratingbar =findViewById(R.id.ratingbar);

        placename =findViewById(R.id.welcome);
       // placerating =findViewById(R.id.placerating);


       Bundle extras = getIntent().getExtras();
        if (extras != null) {
            landmarkimage = extras.getString("landmarkimage");
            landmarkname = extras.getString("landmarkname");
            placerating =(extras.getString("landmarkrating"));
            placename.setText(extras.getString("landmarkname"));
            byte[] decodedString = Base64.decode(landmarkimage, Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            placeimage.setImageBitmap(decodedByte);
            ratingbar.setRating(Float.parseFloat(placerating));
            distance  = extras.getString("distance");
            Log.d("distance",distance);
        }


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
         latitude  = prefs.getString("latitude", "no id");
         longitude  = prefs.getString("longitude", "no id");
         phone  = prefs.getString("phone", "no id");


        Log.d("latitude",latitude);
        Log.d("latitude",longitude);
        Log.d("phone",phone);
        showreviewsbtn.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                getReviews(landmarkname);

            }
        });

        reviewbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendReview();
            }
        });


        showonmapbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+Double.parseDouble(latitude)+","+Double.parseDouble(longitude));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        callusbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phone1 = "0"+phone;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone1, null));
                startActivity(intent);
            }
        });



        showdistancebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(places.this, distance+" M", Toast.LENGTH_LONG).show();
            }
        });


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(places.this,LoginActivity.class);
                startActivity(intent);
            }
        });




    }

//--------------------------------------------------------------------------------------------









    private void getReviews(final String placename2) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        try {

                            int i ;
                            Log.d("Mytrace",response);
                            ArrayList<String> reviews = new ArrayList<String>();
                            ArrayList<String> ratings = new ArrayList<String>();
                            JSONArray jsonArray = new JSONArray(response);

                            Log.d("responsesize",response);

                            for (i = 0; i < jsonArray.length(); i++) {
                                JSONObject s = jsonArray.getJSONObject(i);
                                String review = s.getString("review").trim();
                                double rating = s.getDouble("rating");
                                ratings.add(String.valueOf(rating));
                                reviews.add(review);

                                Log.d("reviews",review);
                                Log.d("ratings",String.valueOf(rating));
                            }


                            Intent intent = new Intent(getApplicationContext(), reviews.class);
                            intent.putExtra("reviews", reviews);
                            intent.putExtra("ratings", ratings);

                            startActivity(intent);


                        }

                        catch (JSONException e) {
                            e.printStackTrace();


                            Toast.makeText(places.this, "Error " +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(places.this, "Error 2" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {

            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("placename", placename2);
                params.put("whwhwh", "jshj");
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






    private void sendReview() {

        Intent intent = new Intent(getApplicationContext(), addReview.class);
        intent.putExtra("placename",placename.getText().toString()) ;
        startActivity(intent);

    }



}