package com.napier.sem;

import com.napier.sem.DatabaseObjects.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SemIntTests {

    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("db:33060");
    }

    @Test
    void TestSampleQuery()
    {
        ArrayList<Country> x = app.sampleQuery();
        assertNotNull(x);
        assertEquals(239, x.size());
        for(Country z : x){
            assertNotNull(z);
        }
    }

    @Test
    void TestRunningAFakeQuery() {
        String qr = "SELECT * From Countri";
        ArrayList<Country> al = app.RunListQuery(Country.class, qr);
        assertNull(al);
    }

    @Test
    void Test_countries_largest_to_smallest(){
        ArrayList<Country> al = app.getCountriesLargestToSmallest();
        assertNotNull(al);
        assertEquals(al.size(), 239);
    }

    @Test
    void Test_countries_largest_to_smallest_group_continent(){
        ArrayList<Country> al = app.getCountriesLargestToSmallestGroupByContinent();
        assertNotNull(al);
        assertEquals(al.size(), 239);
    }

    @Test
    void Test_countries_largest_to_smallest_group_region(){
        ArrayList<Country> al = app.getCountriesLargestToSmallestGroupByRegion();
        assertNotNull(al);
        assertEquals(al.size(), 239);
    }

    @Test
    void TestRunningAQuery(){
        String qr = "SELECT Code, Name, Continent, Region, Population, Capital "
                + "FROM country "
                + "ORDER BY Population DESC";

        ArrayList<Country> al = app.RunListQuery(Country.class, qr);
        assertNotNull(al);
            for (Country country : al) {
                assertNotNull(country);
                app.PrintCountry(country);
            }
    }
}
