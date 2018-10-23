package com.length6array.donationtracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DonationActivity extends AppCompatActivity {


    public static List<String> categories = Arrays.asList("Clothing", "Hat", "Kitchen", "Electronics", "Household", "Other");

    private EditText name;
    private EditText value;
    private EditText description;
    private Spinner type;
    private Spinner location;

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
        location = findViewById(R.id.locations);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);


        ArrayList<String> locationName = new ArrayList<>();
        for (int i = 0; i < Location.locations.size(); i++){
            locationName.add(Location.locations.get(i).getName());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, locationName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter1);


        FloatingActionButton add = findViewById(R.id.add);

        //adds a donation
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText() != null) {
                   if (value.getText() != null){
                       if (description != null){
                           if (type != null){

                               Donation newDonation = new Donation();
                               newDonation.setName(name.getText().toString());
                               newDonation.setType(type.getSelectedItem().toString());
                               newDonation.setDescription(description.getText().toString());
                               newDonation.setLocation(Location.ITEM_MAP.get(location.getSelectedItem().toString()));

                               //TODO date, value, image, short description
                               Donation.donations.add(newDonation);
                               Log.i("DonationActivity.class", "Added donation");

                           }else {
                               Snackbar.make(view, "Category cannot be null", Snackbar.LENGTH_SHORT)
                                       .setAction("Action", null).show();
                           }
                       } else {
                           Snackbar.make(view, "Description cannot be null", Snackbar.LENGTH_SHORT)
                                   .setAction("Action", null).show();
                       }
                   } else {
                       Snackbar.make(view, "Value cannot be null", Snackbar.LENGTH_SHORT)
                               .setAction("Action", null).show();
                   }
                } else {
                    Snackbar.make(view, "Name cannot be null", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                }

                startActivity((new Intent(DonationActivity.this, LocationListActivity.class)));
            }
        });


    }

}
