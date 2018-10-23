package com.length6array.donationtracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class DonationActivity extends AppCompatActivity {


    public static List<String> categories = Arrays.asList("Clothing", "Hat", "Kitchen", "Electronics", "Household", "Other");

    private EditText name;
    private EditText value;
    private EditText description;
    private Spinner type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity((new Intent(DonationActivity.this, LocationListActivity.class)));
            }
        });

        name = findViewById(R.id.name);
        value = findViewById(R.id.value);
        description = findViewById(R.id.description);
        type = findViewById(R.id.category);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        FloatingActionButton add = findViewById(R.id.add);

            //TODO error handling
//        add.setOnClickListener(
//                Donation newDonation = Donation(name.getText(), )
//
//        );



    }

}
