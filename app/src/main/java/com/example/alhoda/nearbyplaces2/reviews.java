package com.example.alhoda.nearbyplaces2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class reviews extends AppCompatActivity {
    ListView lView;
    ArrayList<String> reviews ;
    ArrayList<String> ratings ;
   reviewsAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews_list);

        lView = (ListView) findViewById(R.id.androidList2);
        reviews = new ArrayList<String>();
        ratings = new ArrayList<String>();

        Intent intent = getIntent();


        reviews = intent.getStringArrayListExtra("reviews");
        ratings = intent.getStringArrayListExtra("ratings");

        Log.d("reviews222",reviews.get(0));

        lAdapter = new reviewsAdapter(reviews.this,ratings,reviews);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                Toast.makeText(reviews.this, "hello", Toast.LENGTH_LONG).show();


            }
        });

    }
}
