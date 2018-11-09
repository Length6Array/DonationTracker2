package com.length6array.donationtracker2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Location detail screen. This fragment is either contained in a
 * {@link LocationListActivity} in two-pane mode (on tablets) or a {@link LocationDetailActivity} on
 * handsets.
 *
 * <p>The only important info you need from this is at the bottom
 */
public class LocationDetailFragment extends Fragment {
    /** The fragment argument representing the item ID that this fragment represents. */
    public static final String ARG_ITEM_ID = "item_id";

    /** The dummy content this fragment is presenting. */
    private Location mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
     * screen orientation changes).
     */
    public LocationDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Location.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout =
                    (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    /**
     * This fills our detail view! It shows a buncha stuff, only thing worth noting is the thing at
     * the bottom, the textView object Donations: this is clickable, and will take you to
     * DonationListActivity PLEASE NOTE: it also passes an Intent (will explain in DonationsList)
     * that has the name of the location, and the key to grab that name (this is important so that
     * we know WHICH location's donations to show)
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_detail, container, false);

        // Show the content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.location_detail)).setText((mItem.getAddress()));
            ((TextView) rootView.findViewById(R.id.city_detail)).setText(mItem.getCity());
            ((TextView) rootView.findViewById(R.id.state_detail)).setText(mItem.getState());
            ((TextView) rootView.findViewById(R.id.zipcode_detail))
                    .setText(Integer.toString(mItem.getZipCode()));
            ((TextView) rootView.findViewById(R.id.latitude_detail))
                    .setText(Float.toString(mItem.getLatitude()));
            ((TextView) rootView.findViewById(R.id.longitude_detail))
                    .setText(Float.toString(mItem.getLongitude()));
            ((TextView) rootView.findViewById(R.id.phone_detail)).setText(mItem.getPhone());
            ((TextView) rootView.findViewById(R.id.type_detail)).setText(mItem.getType());
            ((TextView) rootView.findViewById(R.id.website_detail)).setText(mItem.getWebsite());
            TextView donations = rootView.findViewById(R.id.donation);
            String donationItems = "Donations: " + mItem.donationItems.size();
            donations.setText(donationItems);
            donations.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), DonationsListActivity.class);
                            intent.putExtra("Location", mItem.getName());
                            startActivity(intent);
                        }
                    });
        }

        return rootView;
    }
}
