package com.length6array.donationtracker2;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *  Tests the Donation constructor method, which creates a donation given non-null parameters
 *  AND the location given exists in the location list.
 */
public class MorganDonationTest {

    Donation d;

    /**
     * This tests that given a null location, will not create a donation object.
     */
    @Test (expected = java.lang.NullPointerException.class)
    public void NullLocationCase(){
        String test = null;
        d = new Donation("Test", test, "1.00", "12/12/12", "test" );
    }

    /**
     * This tests that given a null name but valid location, will not create a donation object.
     */
    @Test (expected = java.lang.NullPointerException.class)
    public void NullNameCase(){
        Location test = new Location();
        test.setName("Name");
        Location.locations.add(test);
        Location.ITEM_MAP.put("Name", test);

        String fakeName = null;
        d = new Donation(fakeName, "Name", "1.00", "12/12/12", "test" );
    }

    /**
     * This tests that given a null value but valid location, will not create a donation object.
     */
    @Test (expected = java.lang.NullPointerException.class)
    public void NullValueCase(){
        Location test = new Location();
        test.setName("Name");
        Location.locations.add(test);
        Location.ITEM_MAP.put("Name", test);

        String fakeValue = null;
        d = new Donation("Name", "Name", fakeValue, "12/12/12", "test" );
    }

    /**
     * This tests that given a null date but valid location, will not create a donation object.
     */
    @Test (expected = java.lang.NullPointerException.class)
    public void NullDateCase(){
        Location test = new Location();
        test.setName("Name");
        Location.locations.add(test);
        Location.ITEM_MAP.put("Name", test);

        String fakeDate = null;
        d = new Donation("Name", "Name", "1.00", fakeDate, "test" );
    }

    /**
     * This tests that given a null date but valid location, will not create a donation object.
     */
    @Test (expected = java.lang.NullPointerException.class)
    public void NullDescriptionCase(){
        Location test = new Location();
        test.setName("Name");
        Location.locations.add(test);
        Location.ITEM_MAP.put("Name", test);

        String fakeDescription = null;
        d = new Donation("Name", "Name", "1.00", "12/12/12", fakeDescription);
    }


    /**
     * This tests that given an invalid location, it will not create a donation object
     */
    @Test (expected = IllegalArgumentException.class)
    public void InvalidLocationCase(){
        Location test = new Location();
        test.setName("Name");
        Location.locations.add(test);
        Location.ITEM_MAP.put("Name", test);

        d = new Donation("Test", "Fake Location", "1.00", "12/12/12", "test" );

    }

    /**
     * Given a misspelled location, will not create a donation item
     */
    @Test (expected = IllegalArgumentException.class)
    public void MisspelledLocationCase(){
        Location test = new Location();
        test.setName("Name");
        Location.locations.add(test);
        Location.ITEM_MAP.put("Name", test);

        d = new Donation("Test", "name", "1.00", "12/12/12", "test" );

    }

    /**
     * This test is a success case. Given a valid location, will create a donation object.
     */
    @Test
    public void successCase(){
        Location test = new Location();
        test.setName("Name");
        Location.locations.add(test);
        Location.ITEM_MAP.put("Name", test);

        //success case
        assertNotNull(  d = new Donation("Test", "Name", "1.00", "12/12/12", "test" ));

    }

    /**
     * tests that given multiple locations, will create a donation given that the location param is
     * one of the valid location names.
     */

    @Test
    public void multipleLocationsSuccess(){
        Location test = new Location();
        test.setName("Name");
        Location.locations.add(test);
        Location.ITEM_MAP.put("Name", test);

        Location test1 = new Location();
        test.setName("Name1");
        Location.locations.add(test1);
        Location.ITEM_MAP.put("Name1", test1);

        assertNotNull(d = new Donation("Test", "Name1", "1.00", "12/12/12", "test" ));
    }


    /**
     * multiple donation items can be created to the same location.
     */
    @Test
    public void multipleDonationsToLocation(){
        Location test = new Location();
        test.setName("Name");
        Location.locations.add(test);
        Location.ITEM_MAP.put("Name", test);

        assertNotNull(d = new Donation("Test", "Name", "2.00", "1/1/12", "test" ));
        assertNotNull(d = new Donation("Test2", "Name", "1.00", "12/12/12", "test" ));
    }

}
