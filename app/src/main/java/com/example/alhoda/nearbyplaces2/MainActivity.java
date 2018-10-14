package com.example.alhoda.nearbyplaces2;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.Strings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView lView;
    ArrayList<String> names ;
    ArrayList<String> images ;
    ArrayList<String> ratings ;
    ArrayList<String> latitudes ;
    ArrayList<String> longitudes ;
    ArrayList<String> phones ;
    ArrayList<String> distances ;
    String currLat ;
    String currLng ;
    ListAdapter lAdapter;
    Button sort;
    private static String URL_LOGIN = "http://192.168.1.5:8000/sortbyrating.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lView = (ListView) findViewById(R.id.androidList);
        sort = (Button) findViewById(R.id.ratingsort);
        Intent intent = getIntent();

        names = new ArrayList<String>();
        images = new ArrayList<String>();
        ratings = new ArrayList<String>();
        latitudes = new ArrayList<String>();
        longitudes = new ArrayList<String>();
        distances = new ArrayList<String>();
        phones = new ArrayList<String>();






        images = intent.getStringArrayListExtra("urls");
        names = intent.getStringArrayListExtra("names");
        ratings = intent.getStringArrayListExtra("ratings");
        phones = intent.getStringArrayListExtra("phones");
        latitudes = intent.getStringArrayListExtra("newlats");
        longitudes = intent.getStringArrayListExtra("newlongs");
        distances = intent.getStringArrayListExtra("distances");

        currLat = (intent.getStringExtra("currlat"));
        currLng = (intent.getStringExtra("currlng"));






        sort.setOnClickListener(new View.OnClickListener() {
            //String Restaurant = "restaurant";

            public void onClick(View v) {
                getData(currLat , currLng);

            }
        });


        //  version  = new String[]{"Android Alpha", "Android Beta", "Android Cupcake", "Android Donut", "Android Eclair", "Android Froyo", "Android Gingerbread", "Android Honeycomb", "Android Ice Cream Sandwich", "Android JellyBean", "Android Kitkat", "Android Lollipop", "Android Marshmallow", "Android Nougat"};

      //   versionNumber = new String[]{"1.0", "1.1", "1.5", "1.6", "2.0", "2.2", "2.3", "3.0", "4.0", "4.1", "4.4", "5.0", "6.0", "7.0"};


        lAdapter = new ListAdapter(MainActivity.this, names, ratings, images,phones,distances);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent i2 = new Intent(MainActivity.this, MapsActivity.class);
                i2.putExtra("newlat",Double.parseDouble(latitudes.get(i)));
                i2.putExtra("newlng",Double.parseDouble(longitudes.get(i)));
                startActivity(i2);

                //Toast.makeText(MainActivity.this, names.get(i)+"\n"+ratings.get(i), Toast.LENGTH_LONG).show();


            }
        });

    }








    private void getData(final String currlatitude2 , final String currlongitude2 ) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new com.android.volley.Response.Listener<String>() {

                    public void onResponse(String response) {
                        try {
                            int i=0;
                            ArrayList<String> urls =new ArrayList<String>();
                            ArrayList<String> names =new ArrayList<String>();
                            ArrayList<String> ratings =new ArrayList<String>();
                            ArrayList<String> phones =new ArrayList<String>();
                            ArrayList<String> distances =new ArrayList<String>();
                            ArrayList<String> latitudes =new ArrayList<String>();
                            ArrayList<String> longitudes =new ArrayList<String>();
                            JSONArray jsonArray = new JSONArray(response);

                            for(i=0;i<jsonArray.length();i++) {

                                JSONObject s = jsonArray.getJSONObject(i);
                                String url = s.getString("image").trim();
                                String name = s.getString("name").trim();
                                double rating = s.getDouble("rating");
                                String phone = s.getString("phone");
                                double distance = s.getDouble("distance");
                                double latitude = s.getDouble("latitude");
                                double longitude = s.getDouble("longitude");
                                urls.add(url);
                                names.add(name);
                                ratings.add(String.valueOf(rating));
                                phones.add(String.valueOf(phone));
                                distances.add(String.valueOf(distance));
                                latitudes.add(String.valueOf(latitude));
                                longitudes.add(String.valueOf(longitude));
                            }

                            Intent i2 = new Intent(MainActivity.this, ratingsort.class);
                            i2.putExtra("names", names);
                            i2.putExtra("urls", urls);
                            i2.putExtra("ratings", ratings);
                            i2.putExtra("distances", distances);
                            i2.putExtra("phones", phones);
                            i2.putExtra("newlongs", longitudes);
                            i2.putExtra("newlats", latitudes);
                            i2.putExtra("currlat", String.valueOf(currLat));
                            i2.putExtra("currlng", String.valueOf(currLng));
                            startActivity(i2);


                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Error 1" + e.toString(), Toast.LENGTH_SHORT).show();


                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(MainActivity.this, "Error 2" + error.toString(), Toast.LENGTH_SHORT).show();

                    }

                })
        {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("currlatitude2", currlatitude2);
                params.put("currlongitude2", currlongitude2);
                return params;

            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                600000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }














}