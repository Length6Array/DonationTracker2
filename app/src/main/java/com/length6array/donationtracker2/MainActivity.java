package com.length6array.donationtracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        //logout essentially
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO log out the person
                startActivity(new Intent(MainActivity.this, Welcome.class));

            }
        });

        Button location =  findViewById(R.id.goToLocations);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LocationListActivity.class));
            }

        });

        Button donation = findViewById(R.id.goToDonations);
        donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DonationsListActivity.class));
            }

        });
    }

}
