package com.length6array.donationtracker2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This is the Person class.
 * it has basic details about a person
 * I don't think I need to explain what's going on here.
 */
public class Person {

    public static HashMap<String, String> credentials = new HashMap<>();

    public static ArrayList<Person> allUsers = new ArrayList<>();

    public static List<String> userTypes = Arrays.asList("User", "Admin", "Guest", "Location Employee");

    //private String name;
    private String email;
    private String password;
    private String userType;

    public Person(String email, String password, String userType){
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public Person() {

    }

    public Person(){}

    public void setEmail(String email){
        this.email = email;

    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public String getEmail(){
        return email;
    }
    public String getUserType(){
        return userType;
    }
    public String getPassword(){
        return password;
    }






}
