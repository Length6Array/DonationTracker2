package com.length6array.donationtracker2;

public class Person {

    //private String name;
    private String email;
    private String password;
    private String userType;

    public Person(String email, String password, String userType){
        this.email = email;
        this.password = password;
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
