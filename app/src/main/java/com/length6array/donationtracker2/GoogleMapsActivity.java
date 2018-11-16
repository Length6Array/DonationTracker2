package com.length6array.donationtracker2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import android.widget.LinearLayout;

import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng afdStation4 = new LatLng(33.75416, -84.37742);
        mMap.addMarker(new MarkerOptions().position(afdStation4).title("AFD Station 4").
                snippet("Address: 309 Edgewood Ave SE, Atlanta, GA 30332" + "\n" + "Phone Number: (404)555-3456"
                        + "\n" + "Website: www.afd04.atl.ga"));
        LatLng boysGirlsClub = new LatLng(33.73182, -84.43971);
        mMap.addMarker(new MarkerOptions().position(boysGirlsClub).title("Boys & Girls Club W.W. Woolfolk").
                snippet("Address: 1642 Richland Rd, Atlanta, GA 30332" + "\n" + "Phone Number: (404)555-1234"
                        + "\n" + "Website: www.bgc.wool.ga"));
        LatLng pathwayUpperRoom = new LatLng(33.70866, -84.41853);
        mMap.addMarker(new MarkerOptions().position(pathwayUpperRoom).title("Pathway Upper Room Christian Ministries").
                snippet("Address: 1683 Sylvan Rd, Atlanta, GA 30332" + "\n" + "Phone Number: (404)555-5432"
                        + "\n" + "Website: www.pathways.org"));
        LatLng pavilionOfHope = new LatLng(33.80129, -84.25537);
        mMap.addMarker(new MarkerOptions().position(pavilionOfHope).title("Pavilion of Hope Inc.").
                snippet("Address: 3558 East Ponce de Leon Ave, Scottdale, GA 30079" + "\n"
                        + "Phone Number: (404)555-8765" + "\n" + "Website: www.pavhope.org"));
        LatLng ddConvenienceStore = new LatLng(33.71747, -84.2521);
        mMap.addMarker(new MarkerOptions().position(ddConvenienceStore).title("D&D Convenience Store").
                snippet("Address: 2426 Columbia Drive, Decatur, GA 30034" + "\n" + "Phone Number: (404)555-9876"
                        + "\n" + "Website: www.ddconv.com"));
        LatLng keepNFBeautiful = new LatLng(33.96921, -84.3688);
        mMap.addMarker(new MarkerOptions().position(keepNFBeautiful).title("Keep North Fulton Beautiful").
                snippet("Address: 470 Morgan Falls Rd, Sandy Springs, GA 30302" + "\n" + "Phone Number: (770)555-7321"
                        + "\n" + "Website: www.knfb.org"));

        LatLngBounds bounds = new LatLngBounds(new LatLng(33.70, -84.442), new LatLng(33.97, -84.25));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }
            @Override
            public View getInfoContents(Marker marker) {
                Context context = getApplicationContext();
                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);
                TextView title = new TextView(context);
                title.setText(marker.getTitle());
                TextView snippet = new TextView(context);
                snippet.setText(marker.getSnippet());
                info.addView(title);
                info.addView(snippet);
                return info;
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 10.2f));
    }
}
