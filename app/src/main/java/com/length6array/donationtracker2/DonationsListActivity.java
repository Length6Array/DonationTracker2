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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An activity representing a list of Donations. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DonationsDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 *
 * Very similar setup to the LocationListActivity, so I won't really explain what's
 * actually going on.
 * What IS really important to know are these crazy things called Intents!
 *      An intent is a way to pass information from one Activity another.
 *      In this case, we pass the name of a location from LocationDetailActivity (or elsewhere)
 *      so that then we can grab that location's donations and fill the RecyclerView.
 *
 */
public class DonationsListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    //this will be used to grab an intent
    String location;
    Spinner filter;
    Spinner selectLocation;
    TextView noItems;
    String category= "All";
    String[] options = {"All", "Clothing", "Hat", "Kitchen", "Electronics", "Household", "Other"};
    ArrayList<String> categories = new ArrayList<>();
    SimpleItemRecyclerViewAdapter adapter1;
    int position1 = 0; //spinner position
    int locationSelection = 0;
    ArrayList<String> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        /**
         *   THIS IS WHERE I GRAB THE LOCATION name of the . I basically tell the program, "hey
         *   yo lemme get that thing i sent ya earlier" then I go and grab it via a key
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            location = extras.getString("Location");
        }

        FloatingActionButton back = findViewById(R.id.DonationGoBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DonationsListActivity.this, LocationListActivity.class));
            }
        });

        FloatingActionButton addDonation = (FloatingActionButton) findViewById(R.id.addDonation);
        addDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DonationsListActivity.this, DonationActivity.class));
            }
        });

        for (int i = 0; i < Location.locations.size(); i++){
            if (i == 0){
                locations.add(location); //make the default being the current location
            }else {
                if (i == 1){
                    locations.add("All"); //option to view donations from all locations
                }
                if (!Location.locations.get(i - 1).getName().equals(location)){
                    locations.add(Location.locations.get(i - 1).getName());
                }
            }
        }

        setupLocationSpinner();

        setupFilterSpinner();

        noItems = findViewById(R.id.noResultsFoundView);

        if (findViewById(R.id.donations_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.donations_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(getSelectedCategory(position1, locationSelection));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final DonationsListActivity mParentActivity;
        private final List<Donation> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Donation donation = (Donation) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(DonationsDetailFragment.ARG_ITEM_ID, donation.getName());
                    DonationsDetailFragment fragment = new DonationsDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.donations_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DonationsDetailActivity.class);
                    intent.putExtra(DonationsDetailFragment.ARG_ITEM_ID, donation.getName());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(DonationsListActivity parent,
                                      List<Donation> donations,
                                      boolean twoPane) {
            mValues = donations;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.donations_list_content, parent, false);
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



    private SimpleItemRecyclerViewAdapter getSelectedCategory(int categoryID, int locationID){
        String selection = categories.get(categoryID);
        String selectedLocation = locations.get(locationID);
        List<Donation> donations;
        if (locationID == 1){ //"All"
            donations = Location.allDonations;
        } else {
            donations =  Location.ITEM_MAP.get(selectedLocation).donationItems;
        }
        if (donations.size() == 0){
            noItems.setVisibility(TextView.VISIBLE);
        } else {
            noItems.setVisibility(TextView.INVISIBLE);
        }
        if (categoryID == 0){
            adapter1 = new SimpleItemRecyclerViewAdapter(this, donations, mTwoPane);
        } else {
            ArrayList<Donation> sortedDonations = new ArrayList<>();
            for (int i = 0; i < donations.size(); i++) {
                if (donations.get(i).getType().equals(selection)) {
                    sortedDonations.add(donations.get(i));
                }
                donations = sortedDonations;
            }
            if (sortedDonations.size() == 0){
                noItems.setVisibility(TextView.VISIBLE);
            } else {
                noItems.setVisibility(TextView.INVISIBLE);
            }
            adapter1 = new SimpleItemRecyclerViewAdapter(this, sortedDonations, mTwoPane);
        }
        return adapter1;
    }


    private void setupFilterSpinner(){
        categories.add("All");
        categories.add("Clothing");
        categories.add("Hat");
        categories.add("Kitchen");
        categories.add("Electronics");
        categories.add("Household");
        categories.add("Other");
        filter = findViewById(R.id.spinnerFilter);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(adapter);
        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                if (position >= 0 && position < options.length) {
                    position1 = position;
                    View recyclerView = findViewById(R.id.donations_list);
                    assert recyclerView != null;
                    setupRecyclerView((RecyclerView) recyclerView);
                }else {
                    Toast.makeText(DonationsListActivity.this, "Selected Category DNE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
    }

    private void setupLocationSpinner(){
        selectLocation = findViewById(R.id.spinnerLocation);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectLocation.setAdapter(adapter);
        selectLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                if (position >= 0 && position < options.length) {
                    locationSelection = position;
                    View recyclerView = findViewById(R.id.donations_list);
                    assert recyclerView != null;
                    setupRecyclerView((RecyclerView) recyclerView);
                }else {
                    Toast.makeText(DonationsListActivity.this, "Selected Category DNE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

    }

}
