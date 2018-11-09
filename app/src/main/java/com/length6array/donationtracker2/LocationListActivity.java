package com.length6array.donationtracker2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

/**
 * OKAYYY this is the LocationListActivity!!! Get excited! We're now in the legit app!! WOaH
 * What's going on:
 *      A ~recyclerView~ (a layout that allows for scrolling thru a list in a cool and space efficient way-
 *                          it RECYCLES views! Wow! Wild! so basically it takes the view that went off
 *                          screen during scrolling and puts it at the bottom with NEW info! How cool!)
 *           takes all the locations and makes them into a scrolling list.
 *      When you click on a location, it will take you to the Detail View of that location
 *
 */
public class LocationListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    personDBHandler personDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton openMap = (FloatingActionButton) findViewById(R.id.openMap);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity((new Intent(LocationListActivity.this, GoogleMapsActivity.class)));
            }
        });

        //TODO this will be used to add new Locations.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        readLocations();

        if (findViewById(R.id.location_detail_container) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.location_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, Location.locations, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final LocationListActivity mParentActivity;
        private final List<Location> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location item = (Location) view.getTag();

                //this is for like iPads and stuff so you can ignore it
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(LocationDetailFragment.ARG_ITEM_ID, item.getName());
                    LocationDetailFragment fragment = new LocationDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.location_detail_container, fragment)
                            .commit();

                //this is the stuff actually used
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, LocationDetailActivity.class);
                    intent.putExtra(LocationDetailFragment.ARG_ITEM_ID, item.getName());
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(LocationListActivity parent,
                                      List<Location> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }


        //okay so I think this and the next few methods are used to help show all the Locations
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getName());
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
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
