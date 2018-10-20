package com.length6array.donationtracker2;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A fragment representing a single Location detail screen.
 * This fragment is either contained in a {@link LocationListActivity}
 * in two-pane mode (on tablets) or a {@link LocationDetailActivity}
 * on handsets.
 */
public class LocationDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Location mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Location.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());


            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            //((TextView) rootView.findViewById(R.id.location_detail)).setText(mItem.getAddress());
            //TextView address = new TextView(this.getContext());
            TextView address = ((TextView) rootView.findViewById(R.id.location_detail));
            address.setText(mItem.getAddress());
            TextView city = ((TextView) rootView.findViewById(R.id.city_detail));
            city.setText(mItem.getCity());
            TextView state = ((TextView) rootView.findViewById(R.id.state_detail));
            state.setText(mItem.getState());
            TextView zipCode = ((TextView) rootView.findViewById(R.id.zipcode_detail));
            zipCode.setText(Integer.toString(mItem.getZipCode()));
            TextView latitude = ((TextView) rootView.findViewById(R.id.latitude_detail));
            latitude.setText(Float.toString(mItem.getLatitude()));
            TextView longitude = ((TextView) rootView.findViewById(R.id.longitude_detail));
            longitude.setText(Float.toString(mItem.getLongitude()));
            TextView phone = ((TextView) rootView.findViewById(R.id.phone_detail));
            phone.setText(mItem.getPhone());
            TextView type = ((TextView) rootView.findViewById(R.id.type_detail));
            type.setText(mItem.getType());
            TextView website = ((TextView) rootView.findViewById(R.id.website_detail));
            website.setText(mItem.getWebsite());
        }

        return rootView;
    }
}
