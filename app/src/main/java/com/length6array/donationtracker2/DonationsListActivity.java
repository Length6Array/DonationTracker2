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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
    String category;

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

        //setup of spinner for filtering donations
        filter = findViewById(R.id.spinnerFilter);
        List<String> categories = Arrays.asList("All", "Clothing", "Hat", "Kitchen", "Electronics", "Household", "Other");
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(adapter);

        category = filter.getSelectedItem().toString();
        //TODO refresh page to get selected filter



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

        /**
         * What's going on:
         *        using the intent's data (The location name as a string), we go find that location
         *        Then, depending on how it's filtered it shows certain donations
         *        "All" being all donations, otherwise get that specific category of donations
         */

        Location thisLocation = Location.ITEM_MAP.get(location);
        if (category.equals("All")){
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, thisLocation.donationItems, mTwoPane));
        } else {
            ArrayList<Donation> sortedDonations = new ArrayList<>();
            for (int i = 0; i < thisLocation.donationItems.size(); i++) {
                if (thisLocation.donationItems.get(i).getType().equals(category)) {
                    sortedDonations.add(thisLocation.donationItems.get(i));
                }
            }
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, sortedDonations, mTwoPane));
        }

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
           // holder.mContentView.setText(mValues.get(position).content);

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
}
