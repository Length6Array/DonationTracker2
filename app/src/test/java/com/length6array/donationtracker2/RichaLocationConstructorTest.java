package com.length6array.donationtracker2;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RichaLocationConstructorTest {

    @Test
    public void testLocation() {

        int key1 = 42875;
        String name1 = "Richa";
        float latitude1 = 49.65f;
        float longitude1 = 63.11f;
        String address1 = "111 Main Street";
        String city1 = "NYC";
        String state1 = "NY";
        int zipCode1 = 11223;
        String type1 = "drop off";
        String phone1 = "888-123-4567";
        String website1 = "www.location1.com";

        int key2 = 33026;
        String name2 = "Wilson";
        float latitude2 = 10.92f;
        float longitude2 = 50.88f;
        String address2 = "222 Green Street";
        String city2 = "Chicago";
        String state2 = "Illinois";
        int zipCode2 = 33562;
        String type2 = "drop off";
        String phone2 = "888-666-4444";
        String website2 = "www.location2.com";

        int key3 = 42875;
        String name3 = "Morgan";
        float latitude3 = 93.20f;
        float longitude3 = 12.98f;
        String address3 = "333 14th Street";
        String city3 = "Boston";
        String state3 = "MA";
        int zipCode3 = 89675;
        String type3 = "drop off";
        String phone3 = "123-456-7890";
        String website3 = "www.location3.com";

        int key4 = 22222;
        String name4 = "Grace";
        float latitude4 = 74.74f;
        float longitude4 = 33.33f;
        String address4 = "444 10th Street";
        String city4 = "Orlando";
        String state4 = "FL";
        int zipCode4 = 34567;
        String type4 = "drop off";
        String phone4 = "555-666-5555";
        String website4 = "www.location4.com";

        int expectedLocationsLength = 3;

        Location l1 = new Location(key1, name1, latitude1, longitude1, address1, city1, state1,
                zipCode1, type1, phone1, website1);
        Location l2 = new Location(key2, name2, latitude2, longitude2, address2, city2, state2,
                zipCode2, type2, phone2, website2);
        Location l3 = new Location(key3, name3, latitude3, longitude3, address3, city3, state3,
                zipCode3, type3, phone3, website3);
        Location l4 = new Location(key4, name4, latitude4, longitude4, address4, city4, state4,
                zipCode4, type4, phone4, website4);
        Location l5 = new Location(key3, name3, latitude3, longitude3, address3, city3, state3,
                zipCode3, type3, phone3, website3);

        int updatedLocationsLength = Location.locations.size();

        assertEquals("ensure that l1's key equals th key",l1.getKey(), 42875);
        assertEquals("ensure that l2's key equals th key",l2.getKey(), 33026);
        assertEquals("ensure that l3's key equals th key",l3.getKey(), 42875);
        assertEquals("ensure that l4's key equals th key",l4.getKey(), 22222);

        // pass
        Location.locations.add(l1);
        Location.locations.add(l2);
        Location.locations.add(l3);
        Location.locations.add(l4);

        assertEquals("check that list length equals expected list length",
                expectedLocationsLength, updatedLocationsLength);

        // fail

        Location.locations.remove(l2);
        Location.locations.add(l5);

        assertEquals("check that list length equals expected list length",
                expectedLocationsLength, updatedLocationsLength);
    }


}