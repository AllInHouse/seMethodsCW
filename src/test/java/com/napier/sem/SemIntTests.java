package com.napier.sem;

import com.napier.sem.DatabaseObjects.AditionalQueries;
import com.napier.sem.DatabaseObjects.CapitalCity;
import com.napier.sem.DatabaseObjects.City;
import com.napier.sem.DatabaseObjects.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SemIntTests {

    static App app;

    private static final int NumberOfCities = 4079;
    private static final int NumberOfCountries = 239;
    private static final int NumberOfCapitalCities = 232;

    private static final Long WorldPop = 6078749450L;
    private static final int NumberOfContinents = 7;
    private static final int NumberOfRegions = 25;
    private static final int NumberOfDistricts = 1367;

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
        assertEquals(NumberOfCountries, al.size());
    }

    @Test //Requirement 2
    void Test_countries_largest_to_smallest_group_continent(){
        ArrayList<Country> al = app.getCountriesLargestToSmallestGroupByContinent();
        assertNotNull(al);
        assertEquals(NumberOfCountries, al.size());
    }

    @Test //Requirement 3
    void Test_countries_largest_to_smallest_group_region(){
        ArrayList<Country> al = app.getCountriesLargestToSmallestGroupByRegion();
        assertNotNull(al);
        assertEquals(NumberOfCountries, al.size());
    }

    @Test //Requirement 4
    void Test_get_countries_largest_to_smallest_limited(){
        ArrayList<Country> al = app.getCountriesLargestToSmallestLimited("7");
        assertNotNull(al);
        assertEquals(7, al.size());

        al = app.getCountriesLargestToSmallestLimited("2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getCountriesLargestToSmallestLimited("pear");
        assertNull(al);
    }

    @Test //Requirement 5
    void Test_top_populated_countries_continent(){
        ArrayList<Country> al = app.getTopPopulatedCountriesContinent("Asia", "4");
        assertNotNull(al);
        assertEquals(4, al.size());

        al = app.getTopPopulatedCountriesContinent("Asia","2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getTopPopulatedCountriesContinent("Terra","3");
        assertEquals(0, al.size());

        al = app.getTopPopulatedCountriesContinent("Asia", "pear");
        assertNull(al);
    }

    @Test //Requirement 6
    void Test_top_populated_countries_region(){
        ArrayList<Country> al = app.getTopPopulatedCountriesRegion("Western Europe", "4");
        assertNotNull(al);
        assertEquals(4, al.size());

        al = app.getTopPopulatedCountriesRegion("Western Europe","2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getTopPopulatedCountriesRegion("Terra","3");
        assertEquals(0, al.size());

        al = app.getTopPopulatedCountriesRegion("Western Europe", "pear");
        assertNull(al);
    }

    @Test //Requirement 7
    void Test_cities_largest_to_smallest(){
        ArrayList<City> al = app.getCitiesLargestToSmallest();
        assertNotNull(al);
        assertEquals(NumberOfCities, al.size());
    }

    @Test //Requirement 8
    void Test_cities_largest_to_smallest_group_continent(){
        ArrayList<City> al = app.getCitiesLargestToSmallestGroupByContinent();
        assertNotNull(al);
        assertEquals(NumberOfCities, al.size());
    }

    @Test //Requirement 9
    void Test_cities_largest_to_smallest_group_region(){
        ArrayList<City> al = app.getCitiesLargestToSmallestGroupByRegion();
        assertNotNull(al);
        assertEquals(NumberOfCities, al.size());
    }

    @Test //Requirement 10
    void Test_cities_largest_to_smallest_group_country(){
        ArrayList<City> al = app.getCitiesLargestToSmallestGroupByCountry();
        assertNotNull(al);
        assertEquals(NumberOfCities, al.size());
    }

    @Test //Requirement 11
    void Test_cities_largest_to_smallest_group_district(){
        ArrayList<City> al = app.getCitiesLargestToSmallestGroupByDistrict();
        assertNotNull(al);
        assertEquals(NumberOfCities, al.size());
    }

    @Test //Requirement 12
    void Test_cities_largest_to_smallest_limited(){
        ArrayList<City> al = app.getCitiesLargestToSmallestLimited("7");
        assertNotNull(al);
        assertEquals(7, al.size());

        al = app.getCitiesLargestToSmallestLimited("2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getCitiesLargestToSmallestLimited("pear");
        assertNull(al);
    }

    @Test //Requirement 13
    void Test_top_populated_cities_continent(){
        ArrayList<City> al = app.getTopPopulatedCitiesContinent("Asia", "1");
        assertNotNull(al);
        assertEquals(1, al.size());

        al = app.getTopPopulatedCitiesContinent("Asia","2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getTopPopulatedCitiesContinent("Terra","3");
        assertEquals(0, al.size());

        al = app.getTopPopulatedCitiesContinent("Asia", "pear");
        assertNull(al);
    }

    @Test //Requirement 14
    void Test_top_populated_cities_region(){
        ArrayList<City> al = app.getTopPopulatedCitiesRegion("Western Europe", "1");
        assertNotNull(al);
        assertEquals(1, al.size());

        al = app.getTopPopulatedCitiesRegion("Western Europe","2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getTopPopulatedCitiesRegion("Terra","3");
        assertEquals(0, al.size());

        al = app.getTopPopulatedCitiesRegion("Western Europe", "pear");
        assertNull(al);
    }

    @Test //Requirement 15
    void Test_top_populated_cities_country(){
        ArrayList<City> al = app.getTopPopulatedCitiesCountry("United Kingdom", "1");
        assertNotNull(al);
        assertEquals(1, al.size());

        al = app.getTopPopulatedCitiesCountry("United Kingdom","2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getTopPopulatedCitiesCountry("Terra","3");
        assertEquals(0, al.size());

        al = app.getTopPopulatedCitiesCountry("United Kingdom", "pear");
        assertNull(al);
    }

    @Test //Requirement 16
    void Test_top_populated_cities_district(){
        ArrayList<City> al = app.getTopPopulatedCitiesDistrict("Nagasaki", "1");
        assertNotNull(al);
        assertEquals(1, al.size());

        al = app.getTopPopulatedCitiesDistrict("Nagasaki","2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getTopPopulatedCitiesDistrict("Terra","3");
        assertEquals(0, al.size());

        al = app.getTopPopulatedCitiesDistrict("Nagasaki", "pear");
        assertNull(al);
    }

    @Test //Requirement 17
    void Test_capital_cities_largest_to_smallest(){
        ArrayList<CapitalCity> al = app.getCapitalCitiesLargestToSmallest();
        assertNotNull(al);
        assertEquals(NumberOfCapitalCities, al.size());
    }

    @Test //Requirement 18
    void Test_capital_cities_lagest_to_smallest_group_continent(){
        ArrayList<CapitalCity> al = app.getCapitalCitiesLargestToSmallestGroupByContinent();
        assertNotNull(al);
        assertEquals(NumberOfCapitalCities, al.size());
    }

    @Test //Requirement 19
    void Test_capital_cities_largest_to_smallest_group_region(){
        ArrayList<CapitalCity> al = app.getCapitalCitiesLargestToSmallestGroupByRegion();
        assertNotNull(al);
        assertEquals(NumberOfCapitalCities, al.size());
    }

    @Test //Requirement 20
    void Test_capital_cities_largest_to_smallest_limited(){
        ArrayList<CapitalCity> al = app.getCapitalCitiesLargestToSmallestLimited("7");
        assertNotNull(al);
        assertEquals(7, al.size());

        al = app.getCapitalCitiesLargestToSmallestLimited("2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getCapitalCitiesLargestToSmallestLimited("pear");
        assertNull(al);
    }

    @Test //Requirement 21
    void Test_top_populated_capital_cities_continent(){
        ArrayList<City> al = app.getTopPopulatedCapitalCitiesContinent("Asia", "1");
        assertNotNull(al);
        assertEquals(1, al.size());

        al = app.getTopPopulatedCapitalCitiesContinent("Asia","2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getTopPopulatedCapitalCitiesContinent("Terra","3");
        assertEquals(0, al.size());

        al = app.getTopPopulatedCapitalCitiesContinent("Asia", "pear");
        assertNull(al);
    }

    @Test //Requirement 22
    void Test_top_populated_capital_cities_region(){
        ArrayList<City> al = app.getTopPopulatedCapitalCitiesRegion("Western Europe", "1");
        assertNotNull(al);
        assertEquals(1, al.size());

        al = app.getTopPopulatedCapitalCitiesRegion("Western Europe","2");
        assertNotNull(al);
        assertEquals(2, al.size());

        al = app.getTopPopulatedCapitalCitiesRegion("Terra","3");
        assertEquals(0, al.size());

        al = app.getTopPopulatedCapitalCitiesRegion("Western Europe", "pear");
        assertNull(al);
    }

    @Test //Requirement 26?
    void Test_world_population(){
        ArrayList<AditionalQueries> al = app.getWorldPopulation();
        assertNotNull(al);

        assertEquals(1, al.size());
        assertEquals(WorldPop, al.get(0).Population);
    }

    @Test //Requirement 27?
    void Test_continent_population(){
        ArrayList<AditionalQueries> al = app.getContinentPopulation("Asia");
        assertNotNull(al);

        assertEquals(1, al.size());
    }

    @Test //Requirement 28?
    void Test_region_population(){
        ArrayList<AditionalQueries> al = app.getRegionPopulation("South America");
        assertNotNull(al);

        assertEquals(1, al.size());
        assertEquals(345780000, al.get(0).Population);
    }

    @Test //Requirement 29?
    void Test_country_population(){
        ArrayList<AditionalQueries> al = app.getCountryPopulation("United Kingdom");
        assertNotNull(al);

        assertEquals(1, al.size());
        assertEquals(59623400, al.get(0).Population);
    }

    @Test //Requirement 30?
    void Test_district_population(){
        ArrayList<AditionalQueries> al = app.getDistrictPopulation("Nagasaki");
        assertNotNull(al);

        assertEquals(1, al.size());
        assertEquals(770057, al.get(0).Population);
    }

    @Test //Requirement 31?
    void Test_city_population(){
        ArrayList<AditionalQueries> al = app.getCityPopulation("Edinburgh");
        assertNotNull(al);

        assertEquals(1, al.size());
        assertEquals(450180, al.get(0).Population);
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
