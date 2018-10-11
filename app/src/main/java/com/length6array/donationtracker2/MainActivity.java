package com.length6array.donationtracker2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //all locations
    private List<Location> locations = new ArrayList<Location>();

    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Welcome.class));
            }
        });
        readLocations();

        listView = findViewById(R.id.locations);
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1);
        for (int i = 0; i < locations.size(); i++){
            adapter.insert(locations.get(i).getName(), i);
        }
        listView.setAdapter(adapter);
    }

    /**
     * Reads in the locations from the csv file found in res.raw
     */
    private void readLocations(){
        InputStream is = getResources().openRawResource(R.raw.locations);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8") )
        );

        String line = "";
        try {
            //this steps over the header!
            reader.readLine();

            while ( (line = reader.readLine()) != null){
                //this splits the data
                String[] tokens = line.split(",");

                //actually parsing and setting
                //TODO error checking!!
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

                locations.add(newLocation);

                Log.i("MainActivity", "Just Added: " + newLocation.getName());
            }
        }catch (IOException e){
            Log.i("MainActivity", "Error reading Location Data");
            e.printStackTrace();
        }
    }





}
