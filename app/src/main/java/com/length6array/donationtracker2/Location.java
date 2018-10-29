package com.length6array.donationtracker2;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is pretty self-explanatory.
 * To note however:
 *     List locations is typically used to propagate a Spinner
 *     Map ITEM_MAP is used mainly to check or get a location when we have the location's name
 */
public class Location {


    public static List<Donation> allDonations = new ArrayList<>();
    /**
     * An array of location objects.
     */
    public static final List<Location> locations = new ArrayList<Location>();

    /**
     * A map of locations, by name.
     */
    public static final Map<String, Location> ITEM_MAP = new HashMap<String, Location>();


    private static void addLocation(Location location) {
        locations.add(location);
        ITEM_MAP.put(location.getName(), location);
    }



    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    private int key;
    private String name;
    private float latitude;
    private float longitude;
    private String address;
    private String city;
    private String state;
    private int zipCode;
    private String type;
    private String phone;
    private String website;
    public List<Donation> donationItems = new ArrayList<>();

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void addDonation(Donation donation) {
        donationItems.add(donation);
        allDonations.add(donation);
    }


}
