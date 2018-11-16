package com.length6array.donationtracker2;

import 	android.media.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing Donation objects
 */
public class Donation {

    /*
    Categories of Donated goods: Clothing, Hat, Kitchen, Electronics, Household, Other

     */

    public static ArrayList<Donation> donations = new ArrayList<>();

    public static final Map<String, Donation> DONATION_MAP = new HashMap<>();

    /**
     * getter method to return list of donated items
     * @return arraylist of donations containing type Donation
     */
    public static List<Donation> getDonations() {
        return donations;
    }

    /**
     * setter to add a donation item to the map and arraylist
     * @param donation is type Donation
     */
    public static void setDonations(Donation donation) {
        DONATION_MAP.put(donation.name, donation);
        Donation.donations.add(donation);
    }

    /**
     * getter to return variable Id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * setter to set variable id
     * @param id int
     */
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


    /**
     * getter for variable name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter to set variable name of donation
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter to return location of donation
     * @return name of location
     */
    public String getLocation() {
        return location.getName();
    }


    /**
     * setter to set name of location to local variable location
     * @param location String
     */
    public void setLocation(String location) {
        for (int i = 0; i < Location.locations.size(); i++){
            if (location.equals(Location.locations.get(i).getName())){
                this.location = Location.locations.get(i);
            }
        }
    }

    /**
     * setter to set location of type Location to local variable location
     * @param location Location item
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * getter for the date a donation was added
     * @return String of date added
     */
    public String getDateAdded() {
        return dateAdded;
    }

    /**
     * setter to set date of item donation
     *
     * @param dateAdded String
     */
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * getter for local variable value
     * @return value of donation item
     */
    public String getValue() {
        return value;
    }

    /**
     * setter to set value of donation item
     * @param value String
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * getter for type of donation item
     * @return type of item donated
     */
    public String getType() {
        return type;
    }

    /**
     * setter to set type of donation item
     * @param type String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter for description of donation item
     * @return description of donation item
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter to set desription
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for image of donation item
     * @return image of donated item
     */
    public Image getImage() {
        return image;
    }

    /**
     * setter to set image associated with donated item
     * @param image of type Image
     */
    public void setImage(Image image) {
        this.image = image;
    }
}
