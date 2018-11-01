package com.length6array.donationtracker2;

import 	android.media.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Donation {

    /*
    Categories of Donated goods: Clothing, Hat, Kitchen, Electronics, Household, Other

     */

    public static ArrayList<Donation> donations = new ArrayList<>();

    public static final Map<String, Donation> DONATION_MAP = new HashMap<>();

    public static List<Donation> getDonations() {
        return donations;
    }

    public static void setDonations(Donation donation) {
        DONATION_MAP.put(donation.name, donation);
        Donation.donations.add(donation);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String name;
    private Location location;
    private String dateAdded;
    private String value;
    private String type;  //probably needs to be changed to enum type
    private String description; //this is long description, need short one
    private Image image;

    public Donation(String name, Location location, String value, String date, String description) {
        this.name = name;
        this.location = location;
        this.value = value;
        dateAdded = date;
        this.description = description;

    }

    public Donation(String name, String location, String value, String date, String description) {
        for (int i = 0; i < Location.allDonations.size(); i++) {
            if (location.equals(Location.locations.get(i).getName())) {
                Donation d = new Donation(name, Location.locations.get(i), value, date, description);
            }
        }
    }

    public Donation() {
    }

    ;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location.getName();
    }

    public void setLocation(String location) {
        for (int i = 0; i < Location.locations.size(); i++){
            if (location.equals(Location.locations.get(i).getName())){
                this.location = Location.locations.get(i);
            }
        }
    }


    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
