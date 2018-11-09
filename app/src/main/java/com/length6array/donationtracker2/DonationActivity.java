package com.length6array.donationtracker2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the activity that allows for a user to create a donation item
 */
public class DonationActivity extends AppCompatActivity {


    public static List<String> categories = Arrays.asList("Clothing", "Hat", "Kitchen", "Electronics", "Household", "Other");

    private EditText name;
    private EditText value;
    private EditText date;
    private EditText description;
    private Spinner type;
    private Spinner location;
    myDBHandler dbHandler;

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
                startActivity((new Intent(DonationActivity.this, LocationListActivity.class)));
            }
        });

        name = findViewById(R.id.name);
        value = findViewById(R.id.value);
        description = findViewById(R.id.description);
        type = findViewById(R.id.category);
        location = findViewById(R.id.locations);
        value = findViewById(R.id.value);
        date = findViewById(R.id.date);


        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        //initializes dbHandler
        dbHandler = new myDBHandler(this,  null, null, 2);

        readLocations();
        ArrayList<String> locationName = new ArrayList<>();
        for (int i = 0; i < Location.locations.size(); i++){
            locationName.add(Location.locations.get(i).getName());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, locationName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter1);


        FloatingActionButton add = findViewById(R.id.add);

        //adds a donation using database
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
                               newDonation.setDateAdded(date.getText().toString());
                               newDonation.setDescription(description.getText().toString());
                               newDonation.setValue(value.getText().toString());
                               newDonation.setLocation(Location.ITEM_MAP.get(location.getSelectedItem().toString()));


                               //if successful, will say "Donation Added"
                               if(dbHandler.addDonation(newDonation)){
                                   Toast.makeText(DonationActivity.this, "Donation Added", Toast.LENGTH_SHORT).show();

                               } else {
                                   Toast.makeText(DonationActivity.this, "Donation Not Added", Toast.LENGTH_SHORT).show();
                               }
                               //old way. this can prob be deleted
//                               Donation.setDonations(newDonation);
//                               Location.ITEM_MAP.get(newDonation.getLocation()).addDonation(newDonation);

                               Intent intent = new Intent(DonationActivity.this, DonationsListActivity.class);
                               intent.putExtra("Location", newDonation.getLocation());
                               startActivity(intent);
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

            }
        });


    }

    public void readLocations() {
        InputStream is = getResources().openRawResource(R.raw.locations);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            boolean duplicate = false;
            //this steps over the header!
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                //this splits the data
                String[] tokens = line.split(",");

                //checking for duplicate
                Log.i("LocationListActivity", "Location Items size: " + Location.locations.size());
                for (int i = 0; i < Location.locations.size(); i++) {
                    if ((Location.locations.get(i).getName().equals(tokens[1]))) {
                        Log.i("LocationListActivity", "Duplicate: " + tokens[1]);
                        duplicate = true;
                    }
                }

                //if made it this far, means new location
                if (!duplicate) {
                    Location newLocation = new Location();
                    newLocation.setKey(Integer.parseInt(tokens[0]));
                    newLocation.setName(tokens[1]);
                    newLocation.setLatitude(Float.parseFloat(tokens[2]));
                    newLocation.setLongitude(Float.parseFloat(tokens[3]));
                    newLocation.setAddress(tokens[4]);
                    newLocation.setCity(tokens[5]);
                    newLocation.setState(tokens[6]);
                    newLocation.setZipCode(Integer.parseInt(tokens[7]));
                    newLocation.setType(tokens[8]);
                    newLocation.setPhone(tokens[9]);
                    newLocation.setWebsite(tokens[10]);

                    Location.locations.add(newLocation);
                    Location.ITEM_MAP.put(newLocation.getName(), newLocation);
                    Log.i("LocationListActivity", "Just Added: " + newLocation.getName());
                }
            }
        } catch (IOException e) {
            Log.i("LocationListActivity", "Error reading Location Data");
            e.printStackTrace();
        }
    }

}
