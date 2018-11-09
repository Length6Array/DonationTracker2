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
    personDBHandler personDBHandler;
    locationDBHandler locationDBHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        personDBHandler = new personDBHandler(this, null, null, 1);
        myDBHandler = new myDBHandler(this, null, null, 2);
        locationDBHandler = new locationDBHandler(this, null, null, 1);

        //uncomment only to clear out all donations from database
        // clearDatabase();
        clearLocationDatabase();
        loadDonationsFromDatabase();
        loadUsers();
        loadLocations();



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

    private void loadUsers() {
        Cursor cursor = personDBHandler.getAllUsers();
        Log.i("Welcome", "User count " + personDBHandler.getAllUsers().getCount() + "");
        if (cursor.moveToFirst()){
            do {
                Person person = new Person();
                person.setEmail(cursor.getString(0));
                person.setPassword(cursor.getString(1));
                person.setUserType(cursor.getString(2));

                Log.i("Welcome", cursor.getString(0));

                boolean matched = false;
                for (int i = 0; i < Person.allUsers.size(); i++){
                    if (person.getEmail().equals(Person.allUsers.get(i).getEmail())){
                            matched = true;
                            Log.i("Welcome", "Matched = True, " + person.getEmail());
                        }
                }
                if (!matched){
                    Person.allUsers.add(person); //putting into an arraylist to be used now
                    Person.credentials.put(person.getEmail(), person.getPassword()); //into a map to be used for other activities
                }

            } while (cursor.moveToNext());
        }
    }

    public void loadDonationsFromDatabase() {
        Cursor cursor = myDBHandler.getAllDonations();
        int count = myDBHandler.getAllDonations().getCount();
        if (cursor.moveToFirst()) { //if there's a line to be read
            do {

                Donation d =  new Donation();
                d.setName(cursor.getString(1));
                d.setLocation(cursor.getString(2));
                d.setDateAdded( cursor.getString(3));
                d.setType(cursor.getString(4));
                d.setDescription( cursor.getString(5));
                d.setValue(cursor.getString(6));


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

    public void clearLocationDatabase(){
        Cursor cursor = locationDBHandler.getAllLocations();
        if (cursor.moveToFirst()) { //if there's a line to be read
            do {
                locationDBHandler.deleteLocation(cursor.getString(1));
            } while(cursor.moveToNext());
        }
        Location.locations.clear();
        Location.ITEM_MAP.clear();
        //loadDonationsFromDatabase();
        Log.i("Welcome" ," Total Locations: " + Location.locations.size());
    }

    void loadLocations(){
        if (locationDBHandler.getAllLocations().getCount() == 0){
            readLocations();
        }
        Cursor cursor = locationDBHandler.getAllLocations();
        int count = locationDBHandler.getAllLocations().getCount();
        if (cursor.moveToFirst()) { //if there's a line to be read
            do {
                Location l = new Location();
                l.setName(cursor.getString(0));
                l.setLatitude(cursor.getFloat(1));
                l.setLongitude(cursor.getFloat(2));
                l.setAddress(cursor.getString(3));
                l.setCity(cursor.getString(4));
                l.setState(cursor.getString(5));
                l.setZipCode(cursor.getInt(6));
                l.setType(cursor.getString(7));
                l.setPhone(cursor.getString(8));
                l.setWebsite(cursor.getString(9));



                boolean matched = false;
                for (int i = 0; i < Location.locations.size(); i++){
                    if (l.getName().equals(Location.locations.get(i).getName())){
                        if (l.getType().equals(Location.locations.get(i).getType())){
                            matched = true;
                            Log.i("Welcome", "Matched = True, " + l.getName());
                        }
                    }
                }

                if (!matched){
                    Location.locations.add(l); //putting into an arraylist to be used now
                    Location.ITEM_MAP.put(l.getName(), l); //into a map to be used for other activities
                }

                Log.i("Welcome", "Total Locations: " + Location.locations.size());
            } while (cursor.moveToNext());//this line basically just says "do while there's lines to read
        }



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
                for (int i = 0; i < Location.locations.size(); i++) {
                    if ((Location.locations.get(i).getName().equals(tokens[1]))) {
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

                    locationDBHandler.addLocation(newLocation);
                    Location.locations.add(newLocation);
                    Location.ITEM_MAP.put(newLocation.getName(), newLocation);
                }
            }
        } catch (IOException e) {
            Log.i("LocationListActivity", "Error reading Location Data");
            e.printStackTrace();
        }
    }
}
