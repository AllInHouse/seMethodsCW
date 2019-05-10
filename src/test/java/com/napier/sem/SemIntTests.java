package com.napier.sem;

import com.napier.sem.DatabaseObjects.City;
import com.napier.sem.DatabaseObjects.Country;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SemIntTests {

    static App app;

    private static final int NumberOfCities = 4079;
    private static final int NumberOfCountries = 239;

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
        assertEquals(NumberOfCountries, x.size());
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

    @Test //Requirement 1
    void Test_countries_largest_to_smallest(){
        ArrayList<Country> al = app.getCountriesLargestToSmallest();
        assertNotNull(al);
        assertEquals(al.size(), NumberOfCountries);
    }

    @Test //Requirement 2
    void Test_countries_largest_to_smallest_group_continent(){
        ArrayList<Country> al = app.getCountriesLargestToSmallestGroupByContinent();
        assertNotNull(al);
        assertEquals(al.size(), NumberOfCountries);
    }

    @Test //Requirement 3
    void Test_countries_largest_to_smallest_group_region(){
        ArrayList<Country> al = app.getCountriesLargestToSmallestGroupByRegion();
        assertNotNull(al);
        assertEquals(al.size(), NumberOfCountries);
    }


    @Test //Requirement 7
    void Test_cities_largest_to_smallest(){
        ArrayList<City> al = app.getCitiesLargestToSmallest();
        assertNotNull(al);
        assertEquals(al.size(), NumberOfCities);
    }

    @Test //Requirement 8
    void Test_cities_largest_to_smallest_group_continent(){
        ArrayList<City> al = app.getCitiesLargestToSmallestGroupByContinent();
        assertNotNull(al);
        assertEquals(al.size(), NumberOfCities);
    }

    @Test //Requirement 9
    void Test_cities_largest_to_smallest_group_region(){
        ArrayList<City> al = app.getCitiesLargestToSmallestGroupByRegion();
        assertNotNull(al);
        assertEquals(al.size(), NumberOfCities);
    }

    @Test //Requirement 10
    void Test_cities_largest_to_smallest_group_country(){
        ArrayList<City> al = app.getCitiesLargestToSmallestGroupByCountry();
        assertNotNull(al);
        assertEquals(al.size(), NumberOfCities);
    }

    @Test //Requirement 11
    void Test_cities_largest_to_smallest_group_district(){
        ArrayList<City> al = app.getCitiesLargestToSmallestGroupByDistrict();
        assertNotNull(al);
        assertEquals(al.size(), NumberOfCities);
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
