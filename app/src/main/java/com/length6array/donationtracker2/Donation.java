package com.length6array.donationtracker2;

import 	android.media.Image;

public class Donation {

    /*
    Categories of Donated goods: Clothing, Hat, Kitchen, Electronics, Household, Other

     */

    private String name;
    private Location location;
    private String dateAdded;
    private Float value;
    private String type;  //probably needs to be changed to enum type
    private String description; //this is long description, need short one
    private Image image;

    public Donation (String name, Location location, Float value, String date){
        this.name = name;
        this.location = location;
        this.value = value;
        dateAdded = date;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
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

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
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
