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
    public static final ArrayList<Location> locations = new ArrayList<Location>();

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
    public ArrayList<Donation> donationItems = new ArrayList<>();

    public Location(){
        this("");
    }

    public Location(String name){
        this(0,"",0,0,"","","",
                0,"","","");
    }

    public Location(int key, String name, float latitude, float longitude, String address,
                    String city, String state, int zipCode, String type, String phone, String website) {
        this.key = key;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.type = type;
        this.phone = phone;
        this.website = website;

        //boolean isKey = true;
        for (int i = 0; i < locations.size() - 1; i++) {
            int counter = 0;
            if (locations.get(i).getKey() != key) {
                //isKey = false;
                counter = i;
                if (counter != 0) {
                    locations.add(this);
                }
            }

        }
        //locations.add(this);

    }

    /**
     * getter for local variable key
     * @return key of location
     */
    public int getKey() {
        return key;
    }

    /**
     * setter to set passed in key to location
     * @param key int
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * getter for local variable name
     * @return name of location
     */
    public String getName() {
        return name;
    }

    /**
     * setter to set passed in name to location
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for local variable latitude
     * @return latitude of location
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * setter to set passed in latitude to location
     * @param latitude float
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * getter for local variable longitude
     * @return longitude of location
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * setter to set passed in longitude to location
     * @param longitude float
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * getter for local variable address
     * @return address of location
     */
    public String getAddress() {
        return address;
    }

    /**
     * setter to set passed in address to location
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * getter for local variable city
     * @return city location is in
     */
    public String getCity() {
        return city;
    }

    /**
     * setter to set passed in city to location
     * @param city String
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * getter for local variable state
     * @return state location is in
     */
    public String getState() {
        return state;
    }

    /**
     * setter to set passed in state to location
     * @param state String
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * getter for local variable zip code
     * @return zip code of location
     */
    public int getZipCode() {
        return zipCode;
    }

    /**
     * setter to set passed in zip code to location
     * @param zipCode int
     */
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * getter for local variable type
     * @return type of location
     */
    public String getType() {
        return type;
    }

    /**
     * setter to set passed in type to location
     * @param type String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter for local variable phone
     * @return phone of location
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setter to set passed in phone number to location
     * @param phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * getter for local variable website
     * @return website of location
     */
    public String getWebsite() {
        return website;
    }

    /**
     * setter to set passed in website to location
     * @param website String
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * method to add Donation item donation to arraylists
     * @param donation donated item of type Donation
     */
    public void addDonation(Donation donation) {
        donationItems.add(donation);
        allDonations.add(donation);
    }


}
