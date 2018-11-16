package com.length6array.donationtracker2;

import android.app.Activity;
//import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import org.w3c.dom.Text;

//import java.util.ArrayList;


/**
 * A fragment representing a single Donations detail screen.
 * This fragment is either contained in a {@link DonationsListActivity}
 * in two-pane mode (on tablets) or a {@link DonationsDetailActivity}
 * on handsets.
 */
public class DonationsDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Donation donation;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DonationsDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {


            donation = Donation.DONATION_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();


            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(donation.getName());
            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.donations_detail, container, false);

        // Show as text in a TextView.
        if (donation != null) {
            ((TextView) rootView.findViewById(R.id.location)).setText("Location: " + donation.getLocation());
            ((TextView) rootView.findViewById(R.id.type)).setText("Item Type: " + donation.getType());
            ((TextView) rootView.findViewById(R.id.value)).setText("Value: $" + donation.getValue());
            ((TextView) rootView.findViewById(R.id.date)).setText("Date Added: " + donation.getDateAdded());
            ((TextView) rootView.findViewById(R.id.description)).setText("Description: " + donation.getDescription());
        }

        return rootView;
    }


}
