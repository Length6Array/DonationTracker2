package com.length6array.donationtracker2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


/**
 * Hey!
 * This is the Welcome screen! Literally the first thing you see when you open the app
 * It contains two buttons, login and register.
 */
public class Welcome extends AppCompatActivity {

    myDBHandler myDBHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        readLocations();

      //  myDBHandler.onUpgrade(myDBHandler.getWritableDatabase(), 1, 2);

        myDBHandler = new myDBHandler(this, null, null, 2);
        //myDBHandler.onUpgrade(myDBHandler.getWritableDatabase(), 1, 2);

        // clearDatabase();
        loadDonationsFromDatabase();



        for (int i = 0; i < Donation.donations.size(); i++){
            Log.i("Welcome", Donation.donations.get(i).getName());
        }


        //login button. This takes you to the LoginActivity.
        Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Welcome", "Login button clicked");
                startActivity(new Intent(Welcome.this, LoginActivity.class));
            }
        });


        //register button. This takes you to the Registration Activity.
        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Welcome", "Register button clicked");
                startActivity(new Intent(Welcome.this, Registration.class));
            }
        });
    }
    public void loadDonationsFromDatabase() {
        Cursor cursor = myDBHandler.getAllDonations();
        Log.i("Welcome", Donation.donations.size() + "");
        int count = myDBHandler.getAllDonations().getCount();
        Log.i("Welcome", count + "");
        if (cursor.moveToFirst()) { //if there's a line to be read
            do {

                String loc = cursor.getString(2);
                Donation d =  new Donation();
                d.setName(cursor.getString(1));
                d.setLocation(cursor.getString(2));
                d.setDateAdded( cursor.getString(3));
                d.setType(cursor.getString(4));
                d.setDescription( cursor.getString(5));
                d.setValue(cursor.getString(6));


                Log.i("Welcome", "In database: " + d.getName());
                Log.i("Welcome", d.getName() + " LOCATION = "  + d.getLocation());

                boolean matched = false;
                for (int i = 0; i < Donation.donations.size(); i++){
                    if (d.getName().equals(Donation.donations.get(i).getName())){
                        if (d.getType().equals(Donation.donations.get(i).getType())){
                            matched = true;
                            Log.i("Welcome", "Matched = True, " + d.getName());
                        }
                    }
                }
                if (!matched){
                    Donation.donations.add(d); //putting into an arraylist to be used now
                    Donation.DONATION_MAP.put(d.getName(), d); //into a map to be used for other activities
                }

                Log.i("Welcome", "Total Donations: " + Donation.donations.size());
            } while (cursor.moveToNext());//this line basically just says "do while there's lines to read
        }
    }

    public void clearDatabase(){
        Cursor cursor = myDBHandler.getAllDonations();
        if (cursor.moveToFirst()) { //if there's a line to be read
            do {
                myDBHandler.deleteDonation(cursor.getString(1));
            } while(cursor.moveToNext());
        }
        Donation.donations.clear();
        loadDonationsFromDatabase();
        Log.i("Welcome" ," Total DONATIONS: " + Donation.donations.size());
    }

    /**
     * This method reads in all the locations from the csv file found in res.raw
     */
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
