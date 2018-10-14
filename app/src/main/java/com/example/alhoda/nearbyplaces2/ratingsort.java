package com.example.alhoda.nearbyplaces2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ratingsort extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratingsort);

        lView = (ListView) findViewById(R.id.androidList);
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

        currLat = intent.getStringExtra("currlat");
        currLng = intent.getStringExtra("currlng");

        //  version  = new String[]{"Android Alpha", "Android Beta", "Android Cupcake", "Android Donut", "Android Eclair", "Android Froyo", "Android Gingerbread", "Android Honeycomb", "Android Ice Cream Sandwich", "Android JellyBean", "Android Kitkat", "Android Lollipop", "Android Marshmallow", "Android Nougat"};

        //   versionNumber = new String[]{"1.0", "1.1", "1.5", "1.6", "2.0", "2.2", "2.3", "3.0", "4.0", "4.1", "4.4", "5.0", "6.0", "7.0"};


        lAdapter = new ListAdapter(ratingsort.this, names, ratings, images,phones,distances);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent i2 = new Intent(ratingsort.this, MapsActivity.class);
                i2.putExtra("newlat",Double.parseDouble(latitudes.get(i)));
                i2.putExtra("newlng",Double.parseDouble(longitudes.get(i)));
                startActivity(i2);

                //Toast.makeText(MainActivity.this, names.get(i)+"\n"+ratings.get(i), Toast.LENGTH_LONG).show();


            }
        });

    }
}