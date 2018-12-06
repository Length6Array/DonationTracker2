package com.length6array.donationtracker2;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * JUnit tests for testing the setLocation() method in the Donation class.
 *
 * @author Grace Mrosek
 */

public class SetLocationTest {

    @Test
    public void testSetLocation() {
        Location one = new Location();
        Location.locations.add(one);
        one.setName("one");
        Location two = new Location();
        Location.locations.add(two);
        two.setName("two");
        Location three = new Location();
        Location.locations.add(three);
        three.setName("three");

        Donation toy = new Donation();
        toy.setLocation("one");
        Donation shoes = new Donation();
        shoes.setLocation("two");
        Donation clothes = new Donation();
        clothes.setLocation("three");

        assertEquals("one", toy.getLocation());
        assertEquals("two", shoes.getLocation());
        assertEquals("three", clothes.getLocation());
    }

    @Test
    public void setLocationExceptions() {
        Donation donation = new Donation();
        Location one = new Location();
        Location.locations.add(one);
        one.setName("one");

        try {
            donation.setLocation("four");
            fail("Failed to throw an exception when the location argument did not exist.");
        } catch (NoSuchElementException e) {}

        Location nullLocation = null;
        Location.locations.add(nullLocation);
        Location nullNameLocation = new Location();
        nullNameLocation.setName(null);
        Location.locations.add(nullNameLocation);

        try {
            donation.setLocation("location");
            fail("Failed to throw an exception when no locations existed.");
        } catch (Exception e) {}
        try {
            donation.setLocation("nullLocation");
            fail("Failed to throw an exception when an archived location was null.");
        } catch (NullPointerException e) {}
        try {
            donation.setLocation("nullNameLocation");
            fail("Failed to throw an exception when the name of an archived location was null.");
        } catch (NullPointerException e) {}
    }
}