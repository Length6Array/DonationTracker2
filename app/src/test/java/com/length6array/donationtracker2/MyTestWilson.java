package com.length6array.donationtracker2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyTestWilson {
	@Test
	public void testPerson(){
		String email1 = "wpu3325@gmail.com";
		String password1 = "12345678";
		String userType1 = "User";

		String email2 = "location@gmail.com";
		String password2 = "12345678";
		String userType2 = "Location Employee";

		String email3 = "falselocation@gmail.com";
		String password3 = "12345678";
		String userType3 = "Location Employee";

		String email4 = "userreallocation@gmail.com";
		String password4 = "12345678";
		String userType4 = "User";

		String location1name = "location1";
		String location2name = "location2";
		String location3name = "location3";
		
		int locationlen = Location.locations.size(); //Saves Location Array length

		Location location1 = new Location(location1name); //Creates new location1
		Location location2 = new Location(location2name); //Creates new location2

		int newlocationlen = Location.locations.size();

		Person testperson1 = new Person(email1, password1, userType1); //User with no location
		Person testperson2 = new Person(email2, password2, userType2, location1name); //Location Employee with real location
		Person testperson3 = new Person(email3, password3, userType3, location3name); //Location Employee with false location
		Person testperson4 = new Person(email4, password4, userType4, location2name); //User with real location


        assertEquals("Ensure location1's name is location1", location1.getName(), "location1");
        assertEquals("Ensure location2's name is location2", location2.getName(), "location2");
		assertEquals("Check the email of testperson1 is equal to wpu3325@gmail.com", testperson1.getEmail(), "wpu3325@gmail.com");
		assertEquals("Check the password of testperson1 is equal to 12345678", testperson1.getPassword(), "12345678");
		assertEquals("Check the userType of testperson1 is equal to User", testperson1.getUserType(), "User");
		assertEquals("Check the location of testperson1 is equal to null", testperson1.getLocation(), null);

		assertEquals("Check the email of testperson2 is equal to location@gmail.com", testperson2.getEmail(), "location@gmail.com");
		assertEquals("Check the password of testperson2 is equal to 12345678", testperson2.getPassword(), "12345678");
		assertEquals("Check the userType of testperson2 is equal to Location Employee", testperson2.getUserType(), "Location Employee");
		assertEquals("Check the location of testperson2 is equal to location1", testperson2.getLocation(), "location1");

		assertEquals("Check the email of testperson3 is equal to falselocation@gmail.com", testperson3.getEmail(), "falselocation@gmail.com");
		assertEquals("Check the password of testperson3 is equal to 12345678", testperson3.getPassword(), "12345678");
		assertEquals("Check the userType of testperson3 is equal to Location Employee", testperson3.getUserType(), "Location Employee");
		assertEquals("Check the location of testperson3 is equal to null", testperson3.getLocation(), null);

		assertEquals("Check the email of testperson4 is equal to userreallocation@gmail.com", testperson4.getEmail(), "userreallocation@gmail.com");
		assertEquals("Check the password of testperson4 is equal to 12345678", testperson4.getPassword(), "12345678");
		assertEquals("Check the userType of testperson4 is equal to User", testperson4.getUserType(), "User");
		assertEquals("Check the location of testperson4 is equal to null", testperson4.getLocation(), null);

	}

}