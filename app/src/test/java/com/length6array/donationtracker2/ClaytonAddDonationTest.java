package com.length6array.donationtracker2;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClaytonAddDonationTest {
    @Test
    public void testPerson(){
        Location loc = new Location("TestLocation");
        Donation don = new Donation();
        don.setName("TestDonation");
        loc.addDonation(don);
        assertTrue(loc.donationItems.contains(don));
        assertTrue(loc.allDonations.contains(don));
    }
}