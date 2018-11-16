package com.length6array.donationtracker2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the Person class.
 * it has basic details about a person
 * I don't think I need to explain what's going on here.
 */
public class Person {

    public static Map<String, String> credentials = new HashMap<>();

    public static List<Person> allUsers = new ArrayList<>();

    public static List<String> userTypes = Arrays.asList("User", "Admin", "Guest", "Location Employee");

    //private String name;
    private String email;
    private String password;
    private String userType;
    private String location;

    /**
     *
     * @param email string representing email
     * @param password string representing password
     * @param userType string representing userType
     * @param location string representing location
     */
    public Person(String email, String password, String userType, String location){
        this.email = email;
        this.password = password;
        this.userType = userType;
        if (userType.equals("Location Employee")) {
            boolean isLocation = false;
            for (int i = 0; i < Location.locations.size(); i++) {
                if (location.equals(Location.locations.get(i).getName())){
                    isLocation = true;
                }
            }
            if (isLocation) {
                this.location = location;
            } else {
                this.location = null;
            }
        } else {
            this.location = null;
        }
    }

    /**
     *
     * @param email string representing email
     * @param password string representing password
     * @param userType string representing userType
     */
    public Person(String email, String password, String userType){
        this(email, password, userType, null);
    }

    /**
     * Empty constructor
     */
    public Person(){}

    /**
     *
     * @param email String of email that resets the email of a Person
     */
    public void setEmail(String email){
        this.email = email;

    }

    /**
     *
     * @param password String of password that resets the password of a Person
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param userType String of a userType that resets the userType of a Person
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     *
     * @return String representing the current email of the Person
     */
    public String getEmail(){
        return email;
    }

    /**
     *
     * @return String representing the current userType of the Person
     */
    public String getUserType(){
        return userType;
    }

    /**
     *
     * @return String representing the current password of the Person
     */
    public String getPassword(){
        return password;
    }

    /**
     *
     * @return String representing the current location of the user
     */
    public String getLocation(){
        return this.location;
    }


}
