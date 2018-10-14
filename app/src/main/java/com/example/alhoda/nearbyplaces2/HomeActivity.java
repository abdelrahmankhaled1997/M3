package com.example.alhoda.nearbyplaces2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private Button getlocation , logout ;
    private TextView welcome,namefield ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getlocation = findViewById(R.id.getlocation);
        logout = findViewById(R.id.logout);
        welcome = findViewById(R.id.welcome);
        namefield = findViewById(R.id.name_field);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(myIntent);

            }
        });



        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(myIntent);

            }
        });


        Bundle extras = getIntent().getExtras();
        String useremail = extras.getString("useremail");
        namefield.setText(useremail);
    }
}
